package server.handlers;

import common.dto.DTO;

public class BadTestHandler extends Handler<Object, DTO> {
    public BadTestHandler() {
        super(null);
    }

    @Override
    public Object handle(DTO dto) {
        return null;
    }
}
