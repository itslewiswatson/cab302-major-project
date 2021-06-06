package common.dto;

/**
 * This class represents the login DTO
 */
public class LoginDTO extends DTO {
    private final String username;

    /**
     * Create login DTO
     *
     * @param username String of username
     */
    public LoginDTO(String username) {
        this.username = username;
    }

    /**
     * Get username
     *
     * @return String of user name
     */
    public String getUsername() {
        return username;
    }
}
