package common.dto;

public class CreateOrUpdateUnitAssetDTO extends DTO {
    private final String unitId;
    private final String assetId;
    private Integer quantity;

    public CreateOrUpdateUnitAssetDTO(String unitId, String assetId, Integer quantity) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
