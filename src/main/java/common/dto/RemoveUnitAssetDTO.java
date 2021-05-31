package common.dto;

public class RemoveUnitAssetDTO extends DTO {
    private final String unitId;
    private final String assetId;

    public RemoveUnitAssetDTO(String unitId, String assetId) {
        this.unitId = unitId;
        this.assetId = assetId;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getAssetId() {
        return assetId;
    }
}
