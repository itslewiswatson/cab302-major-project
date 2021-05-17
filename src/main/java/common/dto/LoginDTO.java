package common.dto;

public class LoginDTO extends DTO {
    private final String username;

    public LoginDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
