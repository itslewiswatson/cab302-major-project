package server;

import common.dto.DTO;
import server.handlers.Handler;

import java.util.HashMap;

public interface RoutesMapStrategy {
    HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> getMap();
}
