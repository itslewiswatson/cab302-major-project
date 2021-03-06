package server.handlers;

import common.domain.Unit;
import common.domain.User;
import common.dto.RemoveUserFromUnitDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.sql.SQLException;

/**
 * This class represents the remove user from unit logic
 */
public class RemoveUserFromUnitHandler extends Handler<Boolean, RemoveUserFromUnitDTO> {
    public RemoveUserFromUnitHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Boolean indicating success
     */
    @Override
    public Boolean handle(RemoveUserFromUnitDTO dto) {
        try {
            User user = resolveUser(dto.getUserId());
            Unit unit = resolveUnit(dto.getUnitId());

            dbStatements.removeUserFromUnit(user, unit);
        } catch (NullResultException | SQLException e) {
            return false;
        }

        return true;
    }
}
