package server.handlers;

import common.domain.User;
import common.dto.GetUnitUsersDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class GetUnitUsersTest {
    private GetUnitUsersHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new GetUnitUsersHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        ArrayList<User> users = handler.handle(new GetUnitUsersDTO("ID12"));
        Assert.assertNotNull(users);

        for (User user : users) {
            Assert.assertTrue(user.getUnits().contains("ID12"));
        }
    }

    @Test
    public void testNullUnit() {
        ArrayList<User> users = handler.handle(new GetUnitUsersDTO("INVALID"));
        Assert.assertNull(users);
    }
}
