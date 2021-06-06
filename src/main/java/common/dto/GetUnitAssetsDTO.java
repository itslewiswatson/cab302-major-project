package common.dto;

/**
 * This class represents the get unit assets DTO
 */
public class GetUnitAssetsDTO extends DTO {
    private final String unitId;

    /**
     * Create get unit asset DTO
     *
     * @param unitId String of unit Id
     */
    public GetUnitAssetsDTO(String unitId) {
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
