package common.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    private final String username;

    public LoginDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
