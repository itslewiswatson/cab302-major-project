package common.dto;

/**
 * This class represents the remove unit asset DTO
 */
public class RemoveUnitAssetDTO extends DTO {
    private final String unitId;
    private final String assetId;

    /**
     * Create remove unit asset DTO
     *
     * @param unitId String of unit ID
     * @param assetId String of asset ID
     */
    public RemoveUnitAssetDTO(String unitId, String assetId) {
        this.unitId = unitId;
        this.assetId = assetId;
    }

    /**
     * Get unit ID
     *
     * @return String of unit ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Get asset ID
     *
     * @return String of asset ID
     */
    public String getAssetId() {
        return assetId;
    }
}
