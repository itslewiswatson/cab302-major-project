package server.handlers;

import common.domain.User;
import common.dto.CreateAccountDTO;
import common.services.UuidGenerator;
import server.DBStatements;

public class CreateAccountHandler extends Handler<User, CreateAccountDTO> {

    public CreateAccountHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto DTO of create account information
     * @return Newly registered user
     */
    @Override
    public User handle(CreateAccountDTO dto) {
        String[] units = {};
        User newUser = new User(UuidGenerator.generateUuid(), dto.getUsername(), dto.getPassword(), dto.isAdmin(), units);

        dbStatements.addNewUser(newUser);

        return newUser;
    }
}