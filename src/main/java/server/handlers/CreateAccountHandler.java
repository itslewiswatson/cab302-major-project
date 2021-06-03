package server.handlers;

import common.domain.User;
import common.dto.CreateAccountDTO;
import common.services.UuidGenerator;
import server.db.DBStrategy;

public class CreateAccountHandler extends Handler<User, CreateAccountDTO> {

    public CreateAccountHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto DTO of create account information
     * @return Newly registered user
     */
    @Override
    public User handle(CreateAccountDTO dto) {
        User newUser = new User(UuidGenerator.generateUuid(), dto.getUsername(), dto.getPassword(), dto.isAdmin());

        dbStatements.addNewUser(newUser);

        return newUser;
    }
}
