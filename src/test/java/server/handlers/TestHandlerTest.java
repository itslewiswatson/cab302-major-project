package server.handlers;

import common.dto.TestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;
import server.db.DBStrategy;

public class TestHandlerTest {
    private TestDTO testDTO;
    private DBStrategy mockStrategy;
    private TestHandler handler;

    @Before
    public void setUp() {
        this.testDTO = new TestDTO("TEST");
        this.mockStrategy = new MockDBStatements();
        this.handler = new TestHandler(mockStrategy);
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
    public void testDBStrategy() {
        Assert.assertSame(handler.dbStatements, mockStrategy);
    }
}
