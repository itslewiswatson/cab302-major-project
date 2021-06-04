package server.handlers;

import common.domain.User;
import common.dto.UpdateUserPermissionsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class UpdateUserPermissionsHandlerTest {
    private UpdateUserPermissionsHandler handler;

    @Before
    public void setUp() {
        handler = new UpdateUserPermissionsHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        User user = handler.handle(new UpdateUserPermissionsDTO("ID1", true));
        Assert.assertNotNull(user);
        Assert.assertTrue(user.isAdmin());
    }

    @Test
    public void testBadUser() {
        User user = handler.handle(new UpdateUserPermissionsDTO("INVALID", true));
        Assert.assertNull(user);
    }
}
