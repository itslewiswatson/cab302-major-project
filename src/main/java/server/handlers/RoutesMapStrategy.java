package server.handlers;

import common.dto.DTO;

import java.util.HashMap;

public interface RoutesMapStrategy {
    HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> getMap();
}
