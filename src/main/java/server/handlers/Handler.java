package server.handlers;

import server.DBStatements;

abstract public class Handler<T, U> {
    protected DBStatements dbStatements;

    public Handler(DBStatements dbStatements) {
        this.dbStatements = dbStatements;
    }

    abstract public T handle(U dto);
}
