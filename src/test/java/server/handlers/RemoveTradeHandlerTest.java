package server.handlers;

import common.dto.RemoveTradeDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class RemoveTradeHandlerTest {
    private RemoveTradeHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new RemoveTradeHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        boolean success = handler.handle(new RemoveTradeDTO("ID13"));
        Assert.assertTrue(success);
    }

    @Test
    public void testNullTrade() {
        boolean success = handler.handle(new RemoveTradeDTO("INVALID"));
        Assert.assertFalse(success);
    }
}
