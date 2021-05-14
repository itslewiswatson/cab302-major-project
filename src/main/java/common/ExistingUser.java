package common;

/**
 * This class represents an existing user account.
 */
public class ExistingUser extends NewUser implements java.io.Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The existing user's organisational unit names.
     */
    private final String[] units;

    /**
     * Creates an ExistingUser object when a user account is retrieved from the database.
     *
     * @param username An existing user account's username.
     * @param password An existing user account's plaintext password.
     * @param admin An indication of whether the existing user account has administrative privileges.
     * @param units The existing user's organisational unit names.
     * @throws Exception Incorrect username or password length.
     */
    public ExistingUser(String username, String password, boolean admin, String[] units) throws Exception {
        super(username, password, admin);
        this.setPassword(password);
        this.units = units;
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
