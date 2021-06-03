package server.handlers;

import common.dto.TestDTO;
import server.db.DBStrategy;

public class TestHandler extends Handler<Object[], TestDTO> {
    public TestHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Object[] handle(TestDTO dto) {
        return new String[]{dto.getTestId(), dto.getTestId(), dto.getTestId()};
    }
}
