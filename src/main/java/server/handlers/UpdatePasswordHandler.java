package server.handlers;

import common.domain.User;
import common.dto.UpdatePasswordDTO;
import server.DBStatements;

public class UpdatePasswordHandler extends Handler<User, UpdatePasswordDTO> {
    public UpdatePasswordHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public User handle(UpdatePasswordDTO dto) {
        User user = dbStatements.findUserById(dto.getUserId());
        user.setPassword(dto.getNewPassword());
        dbStatements.updatePassword(user);

        return user;
    }
}
