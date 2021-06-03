package server.handlers.strategy;

import common.dto.DTO;
import server.RoutesMap;
import server.db.DBStrategy;
import server.handlers.Handler;

import java.lang.reflect.InvocationTargetException;

public class HandlerResolver {
    private final RoutesMap routesMap;
    private final DBStrategy dbStatements;

    public HandlerResolver(RoutesMap routesMap, DBStrategy dbStatements) {
        this.routesMap = routesMap;
        this.dbStatements = dbStatements;
    }

    public Handler<Object, DTO> resolveHandler(DTO object) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<Handler<Object, DTO>> handlerClass = getHandlerClass(object);
        return createHandlerFromClass(handlerClass);
    }

    private Handler<Object, DTO> createHandlerFromClass(Class<Handler<Object, DTO>> handlerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return handlerClass.getDeclaredConstructor(DBStrategy.class).newInstance(dbStatements);
    }

    @SuppressWarnings("unchecked")
    private Class<Handler<Object, DTO>> getHandlerClass(Object object) {
        return (Class<Handler<Object, DTO>>) routesMap.getMap().get(object.getClass());
    }
}
