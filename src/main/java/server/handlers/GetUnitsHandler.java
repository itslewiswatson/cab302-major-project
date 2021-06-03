package server.handlers;

import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitsDTO;
import server.db.DBStatements;

import java.util.ArrayList;

public class GetUnitsHandler extends Handler<ArrayList<Unit>, GetUnitsDTO> {
    public GetUnitsHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    public ArrayList<Unit> handle(GetUnitsDTO dto) {
        String userId = dto.getUserId();

        if (userId != null) {
            User user = dbStatements.findUserById(userId);
            return dbStatements.findUserUnits(user);
        }

        return dbStatements.findUnits();
    }
}
