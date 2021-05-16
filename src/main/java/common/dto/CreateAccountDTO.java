package common.dto;

import java.io.Serializable;

public class CreateAccountDTO implements Serializable {

    private final String username;
    private final String password;
    private final boolean admin;

    public CreateAccountDTO(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }
}
