package server.handlers;

import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitUsersDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitUsersHandler extends Handler<ArrayList<User>, GetUnitUsersDTO> {
    public GetUnitUsersHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<User> handle(GetUnitUsersDTO dto) {
        try {
            Unit unit = resolveUnit(dto.getUnitId());
            return dbStatements.fetchUnitUsers(unit.getUnitId());
        } catch (NullResultException e) {
            return null;
        }
    }
}
