package common.dto;

/**
 * This class represents the update password DTO
 */
public class UpdatePasswordDTO extends DTO {
    private final String userId;
    private final String newPassword;

    /**
     * Create update password DTO
     *
     * @param userId String of user ID
     * @param newPassword String of hashed password
     */
    public UpdatePasswordDTO(String userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    /**
     * Get user ID
     *
     * @return String of user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get new hashed password
     *
     * @return String of new hashed password
     */
    public String getNewPassword() {
        return newPassword;
    }
}
