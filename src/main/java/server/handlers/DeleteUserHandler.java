package server.handlers;

import common.domain.User;
import common.dto.DeleteUserDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

import java.sql.SQLException;

/**
 * This class represents the delete user logic
 */
public class DeleteUserHandler extends Handler<Boolean, DeleteUserDTO> {
    public DeleteUserHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Boolean indicating success
     */
    @Override
    public Boolean handle(DeleteUserDTO dto) {
        try {
            User user = resolveUser(dto.getUserId());
            dbStatements.deleteUser(user);
        } catch (NullResultException | SQLException e) {
            return false;
        }
        return true;
    }
}
