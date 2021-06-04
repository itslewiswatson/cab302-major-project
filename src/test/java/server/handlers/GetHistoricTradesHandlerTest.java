package server.handlers;

import common.domain.Trade;
import common.dto.GetHistoricTradesDTO;
import org.junit.Assert;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetHistoricTradesHandlerTest {
    @Test
    public void testHandle() {
        GetHistoricTradesHandler handler = new GetHistoricTradesHandler(new MockDBStatements());
        ArrayList<Trade> historicTrades = handler.handle(new GetHistoricTradesDTO());
        Assert.assertNull(historicTrades);
    }

    @Test
    public void testNullOnNoTrades() throws SQLException {
        DBStrategy dbStrategy = new MockDBStatements();
        for (Trade trade : dbStrategy.fetchHistoricTrades()) {
            dbStrategy.removeTrade(trade);
        }

        GetHistoricTradesHandler handler = new GetHistoricTradesHandler(dbStrategy);
        ArrayList<Trade> historicTrades = handler.handle(new GetHistoricTradesDTO());
        Assert.assertNull(historicTrades);
    }
}
