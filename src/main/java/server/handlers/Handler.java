package server.handlers;

import common.dto.DTO;
import server.db.DBStrategy;

abstract public class Handler<T, U extends DTO> {
    protected final DBStrategy dbStatements;

    public Handler(DBStrategy dbStatements) {
        this.dbStatements = dbStatements;
    }

    abstract public T handle(U dto);
}
