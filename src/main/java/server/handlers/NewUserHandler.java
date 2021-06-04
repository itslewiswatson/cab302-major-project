package server.handlers;

import common.domain.User;
import common.dto.NewUserDTO;
import common.services.UuidGenerator;
import server.db.DBStrategy;

public class NewUserHandler extends Handler<User, NewUserDTO> {
    public NewUserHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public User handle(NewUserDTO dto) {
        User user = new User(
                UuidGenerator.generateUuid(),
                dto.getUsername(),
                dto.getPassword(),
                dto.isAdmin()
        );

        dbStatements.addNewUser(user);
        return user;
    }
}
