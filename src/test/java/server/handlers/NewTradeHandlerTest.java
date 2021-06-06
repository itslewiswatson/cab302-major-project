package server.handlers;

import common.dataTypes.TradeType;
import common.domain.Trade;
import common.dto.NewTradeDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class NewTradeHandlerTest {
    private NewTradeHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new NewTradeHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        NewTradeDTO dto = new NewTradeDTO("ID9", "ID10", "ID1", TradeType.BUY, 1, 1);
        Trade trade = handler.handle(dto);
        Assert.assertNotNull(trade);
    }

    @Test
    public void testBadAsset() {
        NewTradeDTO dto = new NewTradeDTO("INVALID", "ID10", "ID1", TradeType.BUY, 1, 1);
        Trade trade = handler.handle(dto);
        Assert.assertNull(trade);
    }

    @Test
    public void testBadUnit() {
        NewTradeDTO dto = new NewTradeDTO("ID9", "INVALID", "ID1", TradeType.BUY, 1, 1);
        Trade trade = handler.handle(dto);
        Assert.assertNull(trade);
    }

    @Test
    public void testBadUser() {
        NewTradeDTO dto = new NewTradeDTO("ID9", "ID10", "INVALID", TradeType.BUY, 1, 1);
        Trade trade = handler.handle(dto);
        Assert.assertNull(trade);
    }

    @Test
    public void testBadQuantity() {
        NewTradeDTO dto = new NewTradeDTO("ID9", "ID10", "ID1", TradeType.BUY, -100, 1);
        Trade trade = handler.handle(dto);
        Assert.assertNull(trade);
    }

    @Test
    public void testBadPrice() {
        NewTradeDTO dto = new NewTradeDTO("ID9", "ID10", "ID1", TradeType.BUY, 1, -100);
        Trade trade = handler.handle(dto);
        Assert.assertNull(trade);
    }
}
