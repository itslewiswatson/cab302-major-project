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
     * An indication that the user is to be added to the database.
     */
    private boolean add;

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
     * Client-side constructor to create a user object when a login is attempted.
     *
     * @param username A user's username.
     * @param password A user's plaintext password.
     */
    public User(String password, String username) {
        add = false;
        admin = false;
        loggedIn = false;
        updatePassword = false;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.username = username;
        units = new String[0];
    }

    /**
     * Client-side constructor to create a user object when a new user account is created.
     *
     * @param admin An indication of whether the user has administrative privileges.
     * @param username A user's username.
     * @param password A user's plaintext password.
     */
    public User(boolean admin, String username, String password) {
        add = true;
        this.admin = admin;
        loggedIn = false;
        updatePassword = false;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.username = username;
        units = new String[0];
    }

    /* Methods */

    /**
     * Set user's add field.
     *
     * @param add An indication that the user is to be added to the database.
     */
    public void setAdd(boolean add) {
        this.add = add;
    }

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

    /**
     * Set user's updatePassword field.
     *
     * @param updatePassword An indication of whether the user is wanting to update their password.
     */
    public void setUpdatePassword(boolean updatePassword) {
        this.updatePassword = updatePassword;
    }
}
