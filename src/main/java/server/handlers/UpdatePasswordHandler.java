package server.handlers;

import common.domain.User;
import common.dto.UpdatePasswordDTO;
import server.db.DBStrategy;

public class UpdatePasswordHandler extends Handler<User, UpdatePasswordDTO> {
    public UpdatePasswordHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public User handle(UpdatePasswordDTO dto) {
        User user = resolveUser(dto.getUserId());
        if (user == null) {
            return null;
        }

        user.setPassword(dto.getNewPassword());
        dbStatements.updatePassword(user);

        return user;
    }

    private User resolveUser(String userId) {
        return dbStatements.findUserById(userId);
    }
}
