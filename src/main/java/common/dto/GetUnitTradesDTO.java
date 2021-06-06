package common.dto;

/**
 * This class represents the get unit trades DTO
 */
public class GetUnitTradesDTO extends DTO {
    private final String unitId;

    /**
     * Create get unit trades DTO
     *
     * @param unitId
     */
    public GetUnitTradesDTO(String unitId) {
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
