package server.handlers;

import common.domain.User;
import common.dto.UpdateUserPermissionsDTO;
import server.db.DBStrategy;

public class UpdateUserPermissionsHandler extends Handler<User, UpdateUserPermissionsDTO> {
    public UpdateUserPermissionsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public User handle(UpdateUserPermissionsDTO dto) {
        User user = resolveUser(dto.getUserId());
        if (user == null) {
            return null;
        }

        user.setAdmin(dto.isAdmin());
        dbStatements.updateUserPermissions(user);
        return user;
    }

    private User resolveUser(String userId) {
        return dbStatements.findUserById(userId);
    }
}
