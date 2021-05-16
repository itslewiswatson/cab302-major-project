package common.domain;

public class UnitAsset extends Entity {
    private final String unitId;
    private final String assetId;
    private int quantity;

    public UnitAsset(String unitId, String assetId, int quantity) {
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
