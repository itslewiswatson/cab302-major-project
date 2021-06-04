package server.handlers.strategy;

import server.MockRoutesMap;
import server.db.MockDBStatements;

public class MockHandlerResolver extends HandlerResolver {
    public MockHandlerResolver() {
        super(new MockRoutesMap(), new MockDBStatements());
    }
}
