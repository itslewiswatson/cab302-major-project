package server;

import common.dto.DTO;
import server.handlers.Handler;
import server.handlers.strategy.HandlerResolver;

import java.lang.reflect.InvocationTargetException;

public class RequestInterpreter {
    private final HandlerResolver handlerResolver;

    public RequestInterpreter(HandlerResolver handlerResolver) {
        this.handlerResolver = handlerResolver;
    }

    public Object interpret(DTO object) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RequestExecutor executor = new RequestExecutor();
        Handler<Object, DTO> handler = handlerResolver.resolveHandler(object);
        return executor.execute(handler, object);
    }
}
