package server.handlers;

import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitsDTO;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetUnitsHandler extends Handler<ArrayList<Unit>, GetUnitsDTO> {
    public GetUnitsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    public ArrayList<Unit> handle(GetUnitsDTO dto) {
        @Nullable String userId = dto.getUserId();

        // If the optional userId is present, we can fetch for units that user belongs to
        if (userId != null) {
            User user = dbStatements.findUserById(userId);
            return dbStatements.findUserUnits(user);
        }

        // No userId present, fetch all
        return dbStatements.findUnits();
    }
}
