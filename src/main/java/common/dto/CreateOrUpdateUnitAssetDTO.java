package common.dto;

public class CreateOrUpdateUnitAssetDTO extends DTO {
    private final String unitId;
    private final String assetId;
    private final int quantity;

    public CreateOrUpdateUnitAssetDTO(String unitId, String assetId, int quantity) {
        this.unitId = unitId;
        this.assetId = assetId;
        this.quantity = quantity;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getAssetId() {
        return assetId;
    }

    public int getQuantity() {
        return quantity;
    }
}
