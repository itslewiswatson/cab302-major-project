package server.handlers;

import common.domain.User;
import common.dto.UpdateUserPermissionsDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

public class UpdateUserPermissionsHandler extends Handler<User, UpdateUserPermissionsDTO> {
    public UpdateUserPermissionsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return Updated user or null
     */
    @Override
    public User handle(UpdateUserPermissionsDTO dto) {
        try {
            User user = resolveUser(dto.getUserId());
            user.setAdmin(dto.isAdmin());
            dbStatements.updateUserPermissions(user);
            return user;
        } catch (NullResultException e) {
            return null;
        }
    }
}
