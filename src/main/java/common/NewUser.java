package common;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This class represents a new user account.
 */
public class NewUser extends Username implements java.io.Serializable {

    /**
     * The class' version number.
     */
    private static final long serialVersionUID = 0;

    /**
     * The user account's plaintext password.
     */
    private String password;

    /**
     * An indication of whether the new user account has administrative privileges.
     */
    private final boolean admin;

    /**
     * The minimum length a password can be.
     */
    private static final int minLength = 1;

    /**
     * The maximum length a password can be.
     */
    private static final int maxLength = 255;

    /**
     * Creates a NewUser object when a new user account is created.
     *
     * @param username A new user account's username.
     * @param password A new user account's plaintext password.
     * @param admin    An indication of whether the new user account has administrative privileges.
     * @throws Exception Incorrect username or password length.
     */
    public NewUser(String username, String password, boolean admin) throws Exception {
        super(username);

        if (password == null || password.length() >= minLength && password.length() <= maxLength) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        } else {
            throw new Exception();
        }

        this.admin = admin;
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
}
