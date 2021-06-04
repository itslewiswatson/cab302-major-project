package server.handlers;

import common.dto.TestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class TestHandlerTest {
    private TestDTO testDTO;
    private TestHandler handler;

    @Before
    public void setUp() {
        this.testDTO = new TestDTO("TEST");
        this.handler = new TestHandler();
    }

    @Test
    public void testHandle() {
        Object[] result = handler.handle(testDTO);

        Assert.assertTrue(result instanceof String[]);
        Assert.assertArrayEquals(
                new String[]{"TEST", "TEST", "TEST"},
                result
        );
    }

    @Test
    public void testDbStrategy() {
        Assert.assertTrue(handler.dbStatements instanceof MockDBStatements);
    }
}
