package server.handlers;

import common.domain.User;
import common.dto.GetUsersDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUsersHandler extends Handler<ArrayList<User>, GetUsersDTO> {
    public GetUsersHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return List of all users
     */
    @Override
    public ArrayList<User> handle(GetUsersDTO dto) {
        return dbStatements.fetchUsers();
    }
}
