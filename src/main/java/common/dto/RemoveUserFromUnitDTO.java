package common.dto;

/**
 * This class represents the remove user from unit DTO
 */
public class RemoveUserFromUnitDTO extends DTO {
    private final String userId;
    private final String unitId;

    /**
     * Create remove user from unit DTO
     *
     * @param userId String of user ID
     * @param unitId String of unit ID
     */
    public RemoveUserFromUnitDTO(String userId, String unitId) {
        this.userId = userId;
        this.unitId = unitId;
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
     * Get unit ID
     *
     * @return String of unit ID
     */
    public String getUnitId() {
        return unitId;
    }
}
