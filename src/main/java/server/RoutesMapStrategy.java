package server;

import common.dto.DTO;
import server.handlers.Handler;

import java.util.HashMap;

public interface RoutesMapStrategy {
    /**
     * Public accessor for the internally-stored routes hash map
     *
     * @return The class-stored routes hash map
     */
    HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> getMap();
}
