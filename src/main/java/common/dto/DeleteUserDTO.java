package common.dto;

/**
 * This class represents the delete user DTO
 */
public class DeleteUserDTO extends DTO {
    private final String userId;

    /**
     * Create delete user DTO
     *
     * @param userId String of user ID
     */
    public DeleteUserDTO(String userId) {
        this.userId = userId;
    }

    /**
     * Get user ID
     *
     * @return String of user ID
     */
    public String getUserId() {
        return userId;
    }
}
