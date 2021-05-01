package common;

/**
 * This class represents an existing user account.
 */
public class ExistingUser extends NewUser implements java.io.Serializable {

    /**
     * The Class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The existing user's organisational unit names.
     */
    private String[] units;

    /**
     * Creates an ExistingUser object when a user account is retrieved from the database.
     *
     * @param username A user's username.
     * @param password A user's hashed password.
     * @param admin An indication of whether the user has administrative privileges.
     * @param units The existing user's organisational unit names.
     */
    public ExistingUser(String username, String password, boolean admin, String[] units) {
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

    /**
     * Sets the existing user's units field.
     *
     * @param units The existing user's organisational unit names.
     */
    public void setUnits(String[] units) {
        this.units = units;
    }
}
