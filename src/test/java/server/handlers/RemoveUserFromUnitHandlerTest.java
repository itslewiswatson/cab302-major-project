package server.handlers;

import common.dto.RemoveUserFromUnitDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class RemoveUserFromUnitHandlerTest {
    private RemoveUserFromUnitHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new RemoveUserFromUnitHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        Boolean success = handler.handle(new RemoveUserFromUnitDTO("ID1", "ID11"));
        Assert.assertTrue(success);
    }

    @Test
    public void testBadUser() {
        Boolean success = handler.handle(new RemoveUserFromUnitDTO("INVALID", "ID11"));
        Assert.assertFalse(success);
    }

    @Test
    public void testBadUnit() {
        Boolean success = handler.handle(new RemoveUserFromUnitDTO("ID1", "INVALID"));
        Assert.assertFalse(success);
    }
}
