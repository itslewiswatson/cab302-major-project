package common;

/**
 * This class represents a login username.
 */
public class Username implements java.io.Serializable {

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
    public Username(String username) throws Exception{
        int minLength = 1;
        int maxLength = 255;

        if (username == null || username.length() >= minLength && username.length() <= maxLength)
        {
            this.username = username;
        }
        else {
            throw new Exception();
        }
    }

    /**
     * Gets the username field.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }
}
