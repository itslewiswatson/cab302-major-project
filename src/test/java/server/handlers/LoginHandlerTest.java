package server.handlers;

import common.domain.User;
import common.dto.LoginDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class LoginHandlerTest {
    private LoginHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new LoginHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        User user = handler.handle(new LoginDTO("Harry"));
        Assert.assertNotNull(user);
    }

    @Test
    public void testBadUser() {
        User user = handler.handle(new LoginDTO("INVALID"));
        Assert.assertNull(user);
    }

    @Test
    public void testNullUser() {
        User user = handler.handle(new LoginDTO(null));
        Assert.assertNull(user);
    }
}
