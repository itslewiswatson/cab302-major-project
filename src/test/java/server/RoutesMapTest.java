package server;

import common.dto.DTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.handlers.Handler;

import java.util.HashMap;

public class RoutesMapTest {
    private RoutesMap routesMap;

    @Before
    public void setUp() {
        this.routesMap = new RoutesMap();
    }

    @Test
    public void testGetMap() {
        HashMap<?, ?> routes = routesMap.getMap();
        Assert.assertNotNull(routes);
    }

    @Test
    public void testMapKeys() {
        HashMap<?, ?> routes = routesMap.getMap();

        for (Object routeDTO : routes.keySet()) {
            Assert.assertTrue(routeDTO instanceof Class<?>);
            Class<?> baseDTOClass = ((Class<?>) routeDTO).getSuperclass();
            Assert.assertSame(DTO.class, baseDTOClass);
        }
    }

    @Test
    public void testMapValues() {
        HashMap<?, ?> routes = routesMap.getMap();

        for (Object routeHandler : routes.values()) {
            Assert.assertTrue(routeHandler instanceof Class<?>);
            Class<?> baseHandlerClass = ((Class<?>) routeHandler).getSuperclass();
            Assert.assertSame(Handler.class, baseHandlerClass);
        }
    }
}
