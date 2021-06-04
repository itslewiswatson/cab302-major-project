package server.handlers;

import common.domain.Trade;
import common.dto.GetTradesDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class GetTradesHandlerTest {
    private DBStrategy dbStrategy;

    @Before
    public void setUp() throws Exception {
        dbStrategy = new MockDBStatements();
        for (Trade trade : dbStrategy.getActiveTrades()) {
            dbStrategy.removeTrade(trade);
        }
    }

    @Test
    public void testHandle() {
        GetTradesHandler handler = new GetTradesHandler(new MockDBStatements());
        ArrayList<Trade> trades = handler.handle(new GetTradesDTO());
        Assert.assertNotNull(trades);
    }

    @Test
    public void testNullOnNoTrades() {
        GetTradesHandler handler = new GetTradesHandler(dbStrategy);
        ArrayList<Trade> trades = handler.handle(new GetTradesDTO());
        Assert.assertNull(trades);
    }
}
