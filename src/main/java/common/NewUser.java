package common;

/**
 * This class represents a new user account.
 *
 * @author Mitchell Egan
 */
public class NewUser extends Credentials implements java.io.Serializable {

    /**
     * The Class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * An indication of whether the new user account has administrative privileges.
     */
    private final boolean admin;

    /**
     * Creates a NewUser object when a new user account is created.
     *
     * @param username A user account's username.
     * @param password A user account's plaintext password.
     * @param admin An indication of whether the user account has administrative privileges.
     */
    public NewUser(String username, String password, boolean admin) {
        super(username, password);
        this.admin = admin;
    }

    /**
     * Gets the new user's admin field.
     *
     * @return An indication of whether the user account has administrative privileges.
     */
    public boolean isAdmin() {
        return admin;
    }
}
