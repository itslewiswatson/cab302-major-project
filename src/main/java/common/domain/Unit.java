package common.domain;

import java.util.List;

public class Unit extends Entity {
    private final String unitId;
    private final String unitName;
    private final int credits;
    private final List<UnitAsset> unitAssets;

    public Unit(String unitId, String unitName, int credits, List<UnitAsset> unitAssets) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.credits = credits;
        this.unitAssets = unitAssets;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getCredits() {
        return credits;
    }

    public List<UnitAsset> getUnitAssets() {
        return unitAssets;
    }

    public UnitAsset addUnitAsset(String assetId, int quantity) {
        UnitAsset newAsset = new UnitAsset(this.getUnitId(), assetId, quantity);
        this.unitAssets.add(newAsset);
        return newAsset;
    }

    public UnitAsset findAssetById(String assetId) {
        for (UnitAsset unitAsset : this.unitAssets) {
            if (unitAsset.getAssetId().equals(assetId)) {
                return unitAsset;
            }
        }
        return null;
    }
}
