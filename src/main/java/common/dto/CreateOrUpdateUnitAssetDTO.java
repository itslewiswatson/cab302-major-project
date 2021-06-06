package common.dto;

/**
 * This class represents the crate or update unit asset DTO
 */
public class CreateOrUpdateUnitAssetDTO extends DTO {
    private final String unitId;
    private final String assetId;
    private Integer quantity;

    /**
     * Create instance of create or update unit asset DTO
     *
     * @param unitId String of unit ID
     * @param assetId String of asset ID
     * @param quantity Int of quantity
     */
    public CreateOrUpdateUnitAssetDTO(String unitId, String assetId, Integer quantity) {
        this.unitId = unitId;
        this.assetId = assetId;
        this.quantity = quantity;
    }

    /**
     * Get unit ID
     *
     * @return The unit id string
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Get asset ID
     *
     * @return The asset id string
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * Get quantity
     *
     * @return The quantity integer
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Set the DTO quantity
     *
     * @param quantity Integer of quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
