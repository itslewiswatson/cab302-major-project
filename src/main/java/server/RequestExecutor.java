package server;

import common.dto.DTO;
import server.handlers.Handler;

public class RequestExecutor {
    public Object execute(Handler<Object, DTO> handler, DTO dto) {
        return handler.handle(dto);
    }
}
