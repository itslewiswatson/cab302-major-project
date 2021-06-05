package server.handlers;

import common.domain.User;
import common.dto.NewUserDTO;
import common.services.UuidGenerator;
import server.db.DBStrategy;

import java.sql.SQLException;

public class NewUserHandler extends Handler<User, NewUserDTO> {
    public NewUserHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public User handle(NewUserDTO dto) {
        try {
            User user = new User(
                    UuidGenerator.generateUuid(),
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.isAdmin()
            );

            User existingUser = dbStatements.findUserByUsername(dto.getUsername());
            if (existingUser != null) {
                return null;
            }

            dbStatements.addUser(user);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
