package common;

/**
 * This class represents a login username.
 */
public class Username implements java.io.Serializable {

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
     * Creates a Username object when a login is attempted.
     *
     * @param username A username.
     * @throws Exception Incorrect username length.
     */
    public Username(String username) throws Exception {
        this.assertUsernameCorrect(username);
        this.username = username;
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
     * Asserts a username is correct when constructing an instance
     *
     * @param username A username.
     * @throws Exception Incorrect username length.
     */
    private void assertUsernameCorrect(String username) throws Exception {
        if (username.length() < USERNAME_MIN_LENGTH || username.length() > USERNAME_MAX_LENGTH) {
            throw new Exception();
        }
    }
}
