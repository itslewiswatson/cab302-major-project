package common.dto;

/**
 * This class represents the new user DTO
 */
public class NewUserDTO extends DTO {
    private final String username;
    private final String password;
    private final Boolean admin;

    /**
     * Create a new user DTO
     *
     * @param username Username string
     * @param password Hashed password string
     * @param admin Is admin boolean
     */
    public NewUserDTO(String username, String password, Boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Get username
     *
     * @return String of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get hashed password
     *
     * @return String of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get is admin
     *
     * @return Boolean showing whether is admin or not
     */
    public Boolean isAdmin() {
        return admin;
    }

}
