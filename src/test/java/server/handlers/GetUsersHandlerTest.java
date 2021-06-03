package server.handlers;

import common.domain.User;
import common.dto.GetUsersDTO;
import org.junit.Assert;
import org.junit.Test;
import server.db.MockDBStatements;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUsersHandlerTest {
    private final GetUsersHandler handler;
    private final DBStrategy dbStrategy;
    private final GetUsersDTO mockDTO;

    public GetUsersHandlerTest() {
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
