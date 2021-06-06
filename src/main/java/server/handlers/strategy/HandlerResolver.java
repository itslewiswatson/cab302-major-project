package server.handlers.strategy;

import common.dto.DTO;
import server.RoutesMapStrategy;
import server.db.DBStrategy;
import server.handlers.Handler;

import java.lang.reflect.InvocationTargetException;

/**
 * This class represents the runtime instantiation of specific route handlers
 */
public class HandlerResolver {
    private final RoutesMapStrategy routesMap;
    private final DBStrategy dbStatements;

    /**
     * Creates an instance of a handler resolver
     *
     * @param routesMap    Routes map to use
     * @param dbStatements DB strategy to use
     */
    public HandlerResolver(RoutesMapStrategy routesMap, DBStrategy dbStatements) {
        this.routesMap = routesMap;
        this.dbStatements = dbStatements;
    }

    /**
     * @param object DTO to resolve for from the request
     * @return A new handler object corresponding to the given DTO as seen in the RoutesMap
     */
    public Handler<Object, DTO> resolveHandler(DTO object) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<Handler<Object, DTO>> handlerClass = getHandlerClass(object);
        return createHandlerFromClass(handlerClass);
    }

    /**
     * @param handlerClass Class representation of the handler to be created.
     * @return The instance of the handler as given by the class name parameter.
     */
    private Handler<Object, DTO> createHandlerFromClass(Class<Handler<Object, DTO>> handlerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return handlerClass.getDeclaredConstructor(DBStrategy.class).newInstance(dbStatements);
    }

    /**
     * Locate the correct handler class to instantiate by using the DTO from the original request.
     *
     * @param object Unparsed DTO from request.
     * @return Class object of handler class to be instantiated.
     */
    @SuppressWarnings("unchecked")
    private Class<Handler<Object, DTO>> getHandlerClass(Object object) {
        return (Class<Handler<Object, DTO>>) routesMap.getMap().get(object.getClass());
    }
}
