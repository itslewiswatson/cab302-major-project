package common.domain;

/**
 * This class represents a unit's asset holding
 */
public class UnitAsset extends Entity {
    private final String unitId;
    private final Asset asset;
    private int quantity;

    /**
     * Creates an instance of a unit asset
     *
     * @param unitId The unit which holds the asset
     * @param asset The asset held by the unit
     * @param quantity The quantity of asset held by the unit
     */
    public UnitAsset(String unitId, Asset asset, int quantity) {
        this.unitId = unitId;
        this.asset = asset;
        this.quantity = quantity;
    }

    /**
     * Gets the unit ID who holds the asset
     *
     * @return A string representing the unit ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Gets the asset held
     *
     * @return The asset instance held by the unit
     */
    public Asset getAsset() {
        return asset;
    }

    /**
     * Gets the quantity held
     *
     * @return Integer representing how many of the asset are held
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the unit asset
     *
     * @param quantity The integer quantity to set to
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Adds the quantity to the current unit asset balance
     *
     * @param quantity The quantity to add to the current balance
     */
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    /**
     * Subtracts the quantity from the unit asset balance
     *
     * @param quantity The quantity to remove from the current balance
     */
    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
