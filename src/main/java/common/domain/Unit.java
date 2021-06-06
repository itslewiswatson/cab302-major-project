package common.domain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * This class represents a unit
 */
public class Unit extends Entity implements Comparable<Unit> {
    /**
     * A constant for the minimum length a unit's name can be
     */
    public final static int NAME_MIN_LENGTH = 4;

    private final String unitId;
    private final String unitName;
    private int credits;
    private final ArrayList<UnitAsset> unitAssets;
    private final ArrayList<User> users;

    /**
     * Creates an instance of a unit
     *
     * @param unitId   A string representing the unit's ID
     * @param unitName A string representing the unit's name
     * @param credits  An integer representing the unit's credit balance
     */
    public Unit(String unitId, String unitName, int credits) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.credits = credits;
        this.unitAssets = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * Gets the unit's ID
     *
     * @return A string representing the unit's ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Gets the unit's name
     *
     * @return A string representing the unit's name
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * Gets the unit's credit balance
     *
     * @return An integer representing the unit's credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the unit's credit balance
     *
     * @param credits An integer representing the unit's new credit balance
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Adds to the unit's credit balance
     *
     * @param credits An integer representing the how much credit to add to the balance
     */
    public void addCredits(int credits) {
        this.credits += credits;
    }

    /**
     * Subtracts from the unit's credit balance
     *
     * @param credits An integer representing the how much credit to remove from the balance
     */
    public void subtractCredits(int credits) {
        this.credits -= credits;
    }

    /**
     * Gets the unit's assets
     *
     * @return An arraylist of the unit's assets
     */
    public ArrayList<UnitAsset> getUnitAssets() {
        return unitAssets;
    }

    /**
     * Gets the unit's users
     *
     * @return An arraylist of the unit's users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Adds a user to the unit
     *
     * @param user The user to add to the unit.
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Compares two unit names
     *
     * @param otherUnit The other unit to have a comparison with
     * @return A boolean showing whether the comparison holds true or not
     */
    @Override
    public int compareTo(@NotNull Unit otherUnit) {
        return this.getUnitName().toLowerCase().compareTo(otherUnit.getUnitName().toLowerCase());
    }
}
