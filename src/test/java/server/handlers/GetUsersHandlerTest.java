package server.handlers;

import common.domain.User;
import common.dto.GetUsersDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUsersHandlerTest {
    private GetUsersHandler handler;
    private DBStrategy dbStrategy;
    private GetUsersDTO mockDTO;

    @Before
    public void setUp() {
        dbStrategy = new MockDBStatements();
        mockDTO = new GetUsersDTO();
        handler = new GetUsersHandler(dbStrategy);
    }

    @Test
    public void testHandle() {
        ArrayList<User> expected = dbStrategy.fetchUsers();
        ArrayList<User> actual = handler.handle(mockDTO);
        Assert.assertNotNull(actual);
        Assert.assertSame(expected, actual);
    }
}
