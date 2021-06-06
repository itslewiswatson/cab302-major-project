package common.domain;

/**
 * This class represents a unit's asset holding
 */
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

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
