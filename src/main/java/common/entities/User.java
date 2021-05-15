package common.entities;

import common.services.PasswordHasher;

/**
 * This class represents an existing user account.
 */
public class User implements java.io.Serializable {

    public static final int USERNAME_MIN_LENGTH = 1;
    public static final int USERNAME_MAX_LENGTH = 255;

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

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
     * The existing user's organisational unit names.
     */
    private final String[] units;

    /**
     * Creates an ExistingUser object when a user account is retrieved from the database.
     *
     * @param username An existing user account's username.
     * @param password An existing user account's plaintext password.
     * @param admin    An indication of whether the existing user account has administrative privileges.
     * @param units    The existing user's organisational unit names.
     * @throws Exception Incorrect username or password length.
     */
    public User(String username, String password, boolean admin, String[] units) throws Exception {
        this.username = username;
        this.password = (new PasswordHasher()).hashPassword(password);
        this.admin = admin;
        this.units = units;
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
     * Set the existing user's password field.
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
     * Gets the existing user's units field.
     *
     * @return The existing user's organisational unit names.
     */
    public String[] getUnits() {
        return units;
    }
}
