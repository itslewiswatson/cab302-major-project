package server.handlers;

import common.domain.User;
import common.dto.GetUsersDTO;
import server.DBStatements;

import java.util.ArrayList;

public class GetUsersHandler extends Handler<ArrayList<User>, GetUsersDTO> {
    public GetUsersHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<User> handle(GetUsersDTO dto) {
        return dbStatements.fetchUsers();
    }
}
