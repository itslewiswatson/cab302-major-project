package server.handlers;

import common.domain.Trade;
import common.dto.GetUnitTradesDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitTradesHandler extends Handler<ArrayList<Trade>, GetUnitTradesDTO> {
    public GetUnitTradesHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<Trade> handle(GetUnitTradesDTO dto) {
        String unitId = dto.getUnitId();
        return dbStatements.findUnitTrades(unitId);
    }
}
