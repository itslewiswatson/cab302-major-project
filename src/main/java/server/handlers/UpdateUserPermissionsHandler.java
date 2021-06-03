package server.handlers;

import common.domain.User;
import common.dto.UpdateUserPermissionsDTO;
import server.DBStatements;

public class UpdateUserPermissionsHandler extends Handler<User, UpdateUserPermissionsDTO> {
    public UpdateUserPermissionsHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public User handle(UpdateUserPermissionsDTO dto) {
        User user = dbStatements.findUserById(dto.getUserId());
        user.setAdmin(dto.isAdmin());
        dbStatements.updateUserPermissions(user);
        return user;
    }
}
