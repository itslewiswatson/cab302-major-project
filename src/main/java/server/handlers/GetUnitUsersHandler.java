package server.handlers;

import common.domain.User;
import common.dto.GetUnitUsersDTO;
import server.db.DBStatements;

import java.util.ArrayList;

public class GetUnitUsersHandler extends Handler<ArrayList<User>, GetUnitUsersDTO> {
    public GetUnitUsersHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<User> handle(GetUnitUsersDTO dto) {
        return dbStatements.fetchUnitUsers(dto.getUnitId());
    }
}
