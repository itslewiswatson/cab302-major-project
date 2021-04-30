package common;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class implements a user account.
 */
public class User implements java.io.Serializable {

    /* Fields */

    /**
     * Class version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * An indication of whether the user has administrative privileges.
     */
    private boolean admin;

    /**
     * An indication of whether the user is logged in.
     */
    private boolean loggedIn;

    /**
     * An indication of whether the user is wanting to update their password.
     */
    private boolean updatePassword;

    /**
     * A user's hashed password.
     */
    private String password;

    /**
     * A user's username.
     */
    private String username;

    /**
     * The units that a user belongs to.
     */
    private String[] units;


    /* Constructors */

    /**
     * Constructor to create a user object when a login is attempted.
     *
     * @param username A user's username.
     * @param password A user's plaintext password.
     */
    public User(String password, String username) {
        admin = false;
        loggedIn = false;
        updatePassword = false;
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }


    /* Methods */

    /**
     * Set user's admin field.
     *
     * @param admin An indication of whether the user has administrative privileges.
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Set user's loggedIn field.
     *
     * @param loggedIn An indication of whether the user is logged.
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Set user's updatePassword field.
     *
     * @param updatePassword An indication of whether the user is wanting to update their password.
     */
    public void setUpdatePassword(boolean updatePassword) {
        this.updatePassword = updatePassword;
    }

    /**
     * Hash and set user's password field.
     *
     * @param password A user's plaintext password.
     */
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Set user's units field.
     *
     * @param units The units that a user belongs to.
     */
    public void setUnits(String[] units) {
        this.units = units;
    }
}
