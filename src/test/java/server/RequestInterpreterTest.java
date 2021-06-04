package server;

import common.dto.BadTestDTO;
import common.dto.TestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.handlers.strategy.MockHandlerResolver;

import java.lang.reflect.InvocationTargetException;

public class RequestInterpreterTest {
    private RequestInterpreter requestInterpreter;

    @Before
    public void setUp() throws Exception {
        requestInterpreter = new RequestInterpreter(new MockHandlerResolver());
    }

    @Test
    public void testInterpret() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object object = requestInterpreter.interpret(new TestDTO("TEST"));
        Assert.assertNotNull(object);
    }

    @Test
    public void testBadInterpret() {
        Assert.assertThrows(NullPointerException.class, () -> requestInterpreter.interpret(new BadTestDTO()));
    }
}
