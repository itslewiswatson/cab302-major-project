package common.domain;

import java.io.Serializable;

/**
 * This class represents a user account.
 */
public class User extends Entity implements Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

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
    private final boolean admin;

    /**
     * The user's organisational unit names.
     */
    private final String[] units;

    /**
     * Creates a User instance
     *
     * @param username An user account's username.
     * @param password An user account's hashed password.
     * @param admin    An indication of whether the user account has administrative privileges.
     * @param units    The user's organisational unit names.
     */
    public User(String userId, String username, String password, boolean admin, String[] units) {
        this.userId = userId;
        this.username = username;
        this.password = password; // Hash by using (new PasswordHasher()).hashPassword(password)
        this.admin = admin;
        this.units = units;
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
    public String[] getUnits() {
        return units;
    }
}