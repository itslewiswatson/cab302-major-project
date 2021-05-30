package common.domain;

import java.util.ArrayList;

public class Unit extends Entity {
    private final String unitId;
    private final String unitName;
    private int credits;
    private final ArrayList<UnitAsset> unitAssets;
    private final ArrayList<User> users;

    public Unit(String unitId, String unitName, int credits) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.credits = credits;
        this.unitAssets = new ArrayList<>();
        this.users = new ArrayList<>();
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

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public ArrayList<UnitAsset> getUnitAssets() {
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
