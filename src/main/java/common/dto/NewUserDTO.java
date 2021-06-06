package common.dto;

public class NewUserDTO extends DTO {
    private final String username;
    private final String password;
    private final Boolean admin;

    public NewUserDTO(String username, String password, Boolean admin) {
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

    public Boolean isAdmin() {
        return admin;
    }

}
