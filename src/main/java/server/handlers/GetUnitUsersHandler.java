package server.handlers;

import common.domain.User;
import common.dto.GetUnitUsersDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitUsersHandler extends Handler<ArrayList<User>, GetUnitUsersDTO> {
    public GetUnitUsersHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<User> handle(GetUnitUsersDTO dto) {
        return dbStatements.fetchUnitUsers(dto.getUnitId());
    }
}
