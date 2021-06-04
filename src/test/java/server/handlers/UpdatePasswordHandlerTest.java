package server.handlers;

import common.domain.User;
import common.dto.UpdatePasswordDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class UpdatePasswordHandlerTest {
    private UpdatePasswordHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new UpdatePasswordHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        User user = handler.handle(new UpdatePasswordDTO("ID1", "PASS"));
        Assert.assertNotNull(user);
        Assert.assertEquals("PASS", user.getPassword());
    }

    @Test
    public void testInvalidUser() {
        User user = handler.handle(new UpdatePasswordDTO("INVALID", "INVALID"));
        Assert.assertNull(user);
    }
}
