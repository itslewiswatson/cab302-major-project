package server.handlers;

import common.domain.User;
import common.dto.NewUserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class NewUserHandlerTest {
    private NewUserHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new NewUserHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        User newUser = handler.handle(new NewUserDTO("Jemima", "", false));
        Assert.assertNotNull(newUser);
    }

    @Test
    public void testExistingUser() {
        User newUser = handler.handle(new NewUserDTO("Harry", "", false));
        Assert.assertNull(newUser);
    }
}
