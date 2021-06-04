package server;

import common.dto.DTO;
import common.dto.TestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.handlers.Handler;
import server.handlers.TestHandler;

public class RequestExecutorTest {
    private RequestExecutor requestExecutor;

    @Before
    public void setUp() {
        this.requestExecutor = new RequestExecutor();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExecute() {
        TestDTO dto = new TestDTO("STRING");
        Handler<?, ?> handler = new TestHandler();
        Handler<Object, DTO> castedHandler = (Handler<Object, DTO>) handler;

        Object result = requestExecutor.execute(castedHandler, dto);
        Assert.assertNotNull(result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNullExecute() {
        DTO dto = new TestDTO(null);
        Handler<?, ?> handler = new TestHandler();
        Handler<Object, DTO> castedHandler = (Handler<Object, DTO>) handler;

        Object result = requestExecutor.execute(castedHandler, dto);
        Assert.assertNull(result);
    }
}
