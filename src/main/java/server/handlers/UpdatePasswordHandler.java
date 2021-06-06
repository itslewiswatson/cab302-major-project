package server.handlers;

import common.domain.User;
import common.dto.UpdatePasswordDTO;
import common.exceptions.NullResultException;
import server.db.DBStrategy;

public class UpdatePasswordHandler extends Handler<User, UpdatePasswordDTO> {
    public UpdatePasswordHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return The updated user or null
     */
    @Override
    public User handle(UpdatePasswordDTO dto) {
        try {
            User user = resolveUser(dto.getUserId());
            user.setPassword(dto.getNewPassword());
            dbStatements.updatePassword(user);
            return user;
        } catch (NullResultException e) {
            return null;
        }
    }
}
