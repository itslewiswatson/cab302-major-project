package common;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class implements a user account.
 */
public class User implements java.io.Serializable {
    /**
     * Class version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * An indication of whether the user is logged in or not.
     */
    private boolean loggedIn;

    /**
     * A user's username.
     */
    private String username;

    /**
     * A user's hashed password.
     */
    private String password;

    /**
     *
     * @param username A user's username.
     * @param password A user's plaintext password.
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Indicate that user is logged in.
     */
    public void login()
    {
        loggedIn = true;
    }

    /**
     * Indicate that user is not logged in.
     */
    public void logout()
    {
        loggedIn = false;
    }
}
