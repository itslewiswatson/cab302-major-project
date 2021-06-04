package server.handlers.strategy;

import common.dto.BadTestDTO;
import common.dto.NonHandlerTestDTO;
import common.dto.TestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.MockRoutesMap;
import server.db.DBStrategy;
import server.db.MockDBStatements;
import server.handlers.Handler;
import server.RoutesMapStrategy;
import server.handlers.TestHandler;

import java.lang.reflect.InvocationTargetException;

public class HandlerResolverTest {
    private HandlerResolver handlerResolver;

    @Before
    public void setUp() {
        RoutesMapStrategy routesMap = new MockRoutesMap();
        DBStrategy dbStrategy = new MockDBStatements();
        this.handlerResolver = new HandlerResolver(routesMap, dbStrategy);
    }

    @Test
    public void testResolve() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestDTO testDTO = new TestDTO("A");
        Handler<?, ?> handler = handlerResolver.resolveHandler(testDTO);

        Assert.assertTrue(handler instanceof TestHandler);
    }

    @Test
    public void testBadDTO() {
        BadTestDTO testDTO = new BadTestDTO();

        Assert.assertThrows(NullPointerException.class, () -> handlerResolver.resolveHandler(testDTO));
    }

    @Test
    public void testNonHandlerDTO() {
        NonHandlerTestDTO testDTO = new NonHandlerTestDTO();

        Assert.assertThrows(NoSuchMethodException.class, () -> handlerResolver.resolveHandler(testDTO));
    }
}
