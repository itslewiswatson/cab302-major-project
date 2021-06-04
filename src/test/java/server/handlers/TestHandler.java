package server.handlers;

import common.dto.TestDTO;
import server.db.MockDBStatements;

public class TestHandler extends Handler<Object[], TestDTO> {
    public TestHandler() {
        super(new MockDBStatements());
    }

    @Override
    public Object[] handle(TestDTO dto) {
        if (dto.getTestId() == null) {
            return null;
        }

        return new String[]{dto.getTestId(), dto.getTestId(), dto.getTestId()};
    }
}
