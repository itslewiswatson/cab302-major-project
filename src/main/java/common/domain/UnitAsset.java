package common.domain;

public class UnitAsset extends Entity {
    private final String unitId;
    private final Asset asset;
    private int quantity;

    public UnitAsset(String unitId, Asset asset, int quantity) {
        this.unitId = unitId;
        this.asset = asset;
        this.quantity = quantity;
    }

    public String getUnitId() {
        return unitId;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
