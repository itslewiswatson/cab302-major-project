package common.dto;

/**
 * This class represents the update user permissions DTO
 */
public class UpdateUserPermissionsDTO extends DTO {
    private final String userId;
    private final boolean isAdmin;

    /**
     * Create update user permissions DTO
     *
     * @param userId String of user ID
     * @param isAdmin Boolean indicating if is admin
     */
    public UpdateUserPermissionsDTO(String userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
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
     * Check if is admin
     *
     * @return Boolean showing whether admin or not
     */
    public boolean isAdmin() {
        return isAdmin;
    }
}
