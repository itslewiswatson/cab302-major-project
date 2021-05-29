package server.handlers;

import common.domain.Trade;
import common.dto.GetUnitTradesDTO;
import server.DBStatements;

import java.util.ArrayList;

public class GetUnitTradesHandler extends Handler<ArrayList<Trade>, GetUnitTradesDTO> {
    public GetUnitTradesHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<Trade> handle(GetUnitTradesDTO dto) {
        String unitId = dto.getUnitId();
        return dbStatements.findUnitTrades(unitId);
    }
}
