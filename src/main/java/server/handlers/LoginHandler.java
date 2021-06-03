package server.handlers;

import common.domain.User;
import common.dto.LoginDTO;
import server.db.DBStrategy;

public class LoginHandler extends Handler<User, LoginDTO> {

    public LoginHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    public User handle(LoginDTO dto) {
        return dbStatements.findUserByUsername(dto.getUsername());
    }
}
