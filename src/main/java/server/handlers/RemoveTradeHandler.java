package server.handlers;

import common.domain.Trade;
import common.dto.RemoveTradeDTO;
import server.db.DBStrategy;

public class RemoveTradeHandler extends Handler<Boolean, RemoveTradeDTO> {
    public RemoveTradeHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Boolean handle(RemoveTradeDTO dto) {
        Trade trade = dbStatements.findTradeById(dto.getTradeId());

        try {
            dbStatements.removeTrade(trade);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
