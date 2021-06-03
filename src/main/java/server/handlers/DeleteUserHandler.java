package server.handlers;

import common.domain.User;
import common.dto.DeleteUserDTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.sql.SQLException;

public class DeleteUserHandler extends Handler<Boolean, DeleteUserDTO> {
    public DeleteUserHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

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

    private User resolveUser(String userId) throws NullResultException {
        @Nullable User user = dbStatements.findUserById(userId);
        if (user == null) {
            throw new NullResultException();
        }
        return user;
    }
}