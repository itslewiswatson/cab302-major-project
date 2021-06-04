package server.handlers;

import common.domain.Trade;
import common.dto.GetTradesDTO;
import org.junit.Assert;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetTradesHandlerTest {
    @Test
    public void testHandle() {
        GetTradesHandler handler = new GetTradesHandler(new MockDBStatements());
        ArrayList<Trade> trades = handler.handle(new GetTradesDTO());
        Assert.assertNotNull(trades);
    }

    @Test
    public void testNullOnNoTrades() throws SQLException {
        DBStrategy dbStrategy = new MockDBStatements();
        for (Trade trade : dbStrategy.getActiveTrades()) {
            dbStrategy.removeTrade(trade);
        }

        GetTradesHandler handler = new GetTradesHandler(dbStrategy);
        ArrayList<Trade> trades = handler.handle(new GetTradesDTO());
        Assert.assertNull(trades);
    }
}
