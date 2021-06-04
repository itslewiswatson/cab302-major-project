package server.handlers;

import common.domain.Trade;
import common.dto.GetUnitTradesDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class GetUnitTradesHandlerTest {
    private GetUnitTradesHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new GetUnitTradesHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        ArrayList<Trade> trades = handler.handle(new GetUnitTradesDTO("ID12"));
        Assert.assertNotNull(trades);

        for (Trade trade : trades) {
            Assert.assertEquals("ID12", trade.getUnit().getUnitId());
        }
    }

    @Test
    public void testNullUnit() {
        ArrayList<Trade> trades = handler.handle(new GetUnitTradesDTO("invalid"));
        Assert.assertNull(trades);
    }
}
