package common;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This class represents a set of login credentials.
 *
 * @author Mitchell Egan
 */
public class Credentials implements java.io.Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The credentials' username.
     */
    private String username;

    /**
     * The credentials' plaintext password.
     */
    private String password;

    /**
     * Creates a Credentials object when a login is attempted.
     *
     * @param username A username.
     * @param password A plaintext password.
     */
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the credentials' username field.
     *
     * @return The credentials username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the credentials' password field.
     *
     * @return The credentials' plaintext password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the existing user's username field.
     *
     * @param username A username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the existing user's password field.
     *
     * @param password A plaintext password.
     */
    public void setPassword(String password) {
        this.password = password ;
    }

    /**
     * Changes the existing user's password.
     *
     * @param password A plaintext password.
     */
    public void changePassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
