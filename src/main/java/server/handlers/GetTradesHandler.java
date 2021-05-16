package server.handlers;

import common.domain.Trade;
import common.dto.GetTradesDTO;
import server.DBStatements;

import java.util.List;

public class GetTradesHandler extends Handler<List<Trade>, GetTradesDTO> {
    public GetTradesHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public List<Trade> handle(GetTradesDTO dto) {
        return null;
    }
}
