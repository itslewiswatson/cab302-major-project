package server.handlers;

import common.domain.Trade;
import common.domain.Unit;
import common.dto.GetUnitTradesDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitTradesHandler extends Handler<ArrayList<Trade>, GetUnitTradesDTO> {
    public GetUnitTradesHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return List of all trades from a unit
     */
    @Override
    public ArrayList<Trade> handle(GetUnitTradesDTO dto) {
        try {
            Unit unit = resolveUnit(dto.getUnitId());
            return dbStatements.findUnitTrades(unit.getUnitId());
        } catch (NullResultException e) {
            return null;
        }
    }
}
