package server.handlers;

import common.domain.Unit;
import common.domain.User;
import common.dto.AddUserToUnitDTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.sql.SQLException;

public class AddUserToUnitHandler extends Handler<Boolean, AddUserToUnitDTO> {
    public AddUserToUnitHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public Boolean handle(AddUserToUnitDTO dto) {
        try {
            User user = resolveUser(dto.getUserId());
            Unit unit = resolveUnit(dto.getUnitId());

            boolean isInUnit = user.getUnits().contains(unit.getUnitId());
            if (isInUnit) return false;

            dbStatements.addUserToUnit(user, unit);
        } catch (NullResultException | SQLException e) {
            return false;
        }

        return true;
    }

    private User resolveUser(String userId) throws NullResultException {
        @Nullable User user = dbStatements.findUserById(userId);
        if (user == null) {
            throw new NullResultException();
        }
        return user;
    }

    private Unit resolveUnit(String unitId) throws NullResultException {
        @Nullable Unit unit = dbStatements.findUnitById(unitId);
        if (unit == null) {
            throw new NullResultException();
        }
        return unit;
    }
}