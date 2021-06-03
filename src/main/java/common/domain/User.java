package common.domain;

import java.util.ArrayList;

/**
 * This class represents a user account.
 */
public class User extends Entity {

    private final String userId;

    /**
     * The username.
     */
    private final String username;

    /**
     * The user account's plaintext password.
     */
    private String password;

    /**
     * An indication of whether the new user account has administrative privileges.
     */
    private boolean admin;

    /**
     * The user's organisational unit names.
     */
    private final ArrayList<String> units;

    /**
     * Creates a User instance
     *
     * @param username An user account's username.
     * @param password An user account's hashed password.
     * @param admin    An indication of whether the user account has administrative privileges.
     */
    public User(String userId, String username, String password, boolean admin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.units = new ArrayList<>();
    }

    /**
     * Gets the user's UUID
     *
     * @return The UUID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the username field.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user account's password field.
     *
     * @return The user account's plaintext password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user's password field.
     *
     * @param password A hashed password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user account's admin field.
     *
     * @return An indication of whether the user account has administrative privileges.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets the user account's admin field.
     *
     * @param admin Boolean representing whether the user should be an admin or not
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Gets the new user account's admin field as a string.
     *
     * @return A string indication of whether the user account has administrative privileges.
     */
    public String getAdmin() {
        return admin ? "1" : "0";
    }

    /**
     * Gets the user's units field.
     *
     * @return The user's organisational unit names.
     */
    public ArrayList<String> getUnits() {
        return units;
    }

    /**
     * Add the user to a unit.
     *
     * @param unitId The unit id to add the user to.
     */
    public void addUnit(String unitId) {
        this.units.add(unitId);
    }
}
