package server;

import common.dto.DTO;
import common.dto.NonHandlerTestDTO;
import common.dto.TestDTO;
import server.handlers.BadTestHandler;
import server.handlers.Handler;
import server.handlers.TestHandler;

import java.util.HashMap;

public class MockRoutesMap implements RoutesMapStrategy {
    private final HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> routesMap = new HashMap<>();

    public MockRoutesMap() {
        routesMap.put(TestDTO.class, TestHandler.class);
        routesMap.put(NonHandlerTestDTO.class, BadTestHandler.class);
    }

    @Override
    public HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> getMap() {
        return routesMap;
    }
}
