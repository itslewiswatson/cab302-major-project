package server.handlers;

import common.domain.Trade;
import common.dto.RemoveTradeDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.sql.SQLException;

public class RemoveTradeHandler extends Handler<Boolean, RemoveTradeDTO> {
    public RemoveTradeHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Boolean handle(RemoveTradeDTO dto) {
        try {
            Trade trade = resolveTrade(dto.getTradeId());
            dbStatements.removeTrade(trade);
        } catch (NullResultException | SQLException e) {
            return false;
        }
        return true;
    }

    private Trade resolveTrade(String tradeId) throws NullResultException {
        Trade trade = dbStatements.findTradeById(tradeId);
        if (trade == null) {
            throw new NullResultException();
        }
        return trade;
    }
}
