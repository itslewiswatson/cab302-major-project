package server;

import common.dto.DTO;
import server.handlers.Handler;

/**
 * Executes a request handler and its associated DTO
 */
class RequestExecutor {
    /**
     * @param handler The handler instance to use.
     * @param dto     The DTO to pass into the handler.
     * @return An object representing the handler's result.
     */
    public Object execute(Handler<Object, DTO> handler, DTO dto) {
        return handler.handle(dto);
    }
}
