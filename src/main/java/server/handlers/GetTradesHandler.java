package server.handlers;

import common.domain.Trade;
import common.dto.GetTradesDTO;
import server.DBStatements;

import java.util.ArrayList;

public class GetTradesHandler extends Handler<ArrayList<Trade>, GetTradesDTO> {
    public GetTradesHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<Trade> handle(GetTradesDTO dto) {
        return dbStatements.getActiveTrades();
    }
}
