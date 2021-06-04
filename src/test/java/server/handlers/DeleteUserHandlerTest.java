package server.handlers;

import common.dto.DeleteUserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeleteUserHandlerTest {
    private DeleteUserHandler handler;
    private DBStrategy dbStrategy;

    @Before
    public void setUp() throws Exception {
        dbStrategy = new MockDBStatements();
        handler = new DeleteUserHandler(dbStrategy);
    }

    @Test
    public void testHandle() {
        Assert.assertEquals(
                1,
                dbStrategy.fetchUsers().stream()
                        .filter(user -> user.getUserId().equals("ID1"))
                        .collect(Collectors.toCollection(ArrayList::new)).size()
        );

        boolean success = handler.handle(new DeleteUserDTO("ID1"));
        Assert.assertTrue(success);

        Assert.assertEquals(
                0,
                dbStrategy.fetchUsers().stream()
                        .filter(user -> user.getUserId().equals("ID1"))
                        .collect(Collectors.toCollection(ArrayList::new)).size()
        );
    }

    @Test
    public void testNullUser() {
        boolean success = handler.handle(new DeleteUserDTO("INVALID"));
        Assert.assertFalse(success);
    }
}
