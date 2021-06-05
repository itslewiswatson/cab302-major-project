package common.domain;

import java.util.ArrayList;

public class Unit extends Entity {
    public final static int NAME_MIN_LENGTH = 4;

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

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public void subtractCredits(int credits) {
        this.credits -= credits;
    }

    public ArrayList<UnitAsset> getUnitAssets() {
        return unitAssets;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
