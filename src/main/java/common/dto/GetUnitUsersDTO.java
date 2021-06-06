package common.dto;

/**
 * This class represents the get unit users DTO
 */
public class GetUnitUsersDTO extends DTO {
    private final String unitId;

    /**
     * Create unit users DTO
     *
     * @param unitId String of unit ID
     */
    public GetUnitUsersDTO(String unitId) {
        this.unitId = unitId;
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
