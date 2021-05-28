package server.handlers;

import common.dto.DTO;
import server.DBStatements;

abstract public class Handler<T, U extends DTO> {
    protected DBStatements dbStatements;

    public Handler(DBStatements dbStatements) {
        this.dbStatements = dbStatements;
    }

    abstract public T handle(U dto);
}
