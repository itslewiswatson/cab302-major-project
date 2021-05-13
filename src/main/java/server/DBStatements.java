package server;

import common.Credentials;
import common.ExistingUser;
import common.NewUser;
import java.sql.*;

/**
 * This class controls database access and contains its required statements.
 */
public class DBStatements {
    /**
     * SQL statement to retrieve a given user.
     */
    private static final String GET_USER = "SELECT * FROM user WHERE username = ?";

    /**
     * SQL statement to insert a user.
     */
    private static final String INSERT_USER = "INSERT INTO user (username, password, admin) VALUES (?, ?, ?);";

    /**
     * SQL statement to update a given user's password.
     */
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE username = ?";

    /**
     * A database connection.
     */
    private final Connection connection;

    /**
     * A precompiled SQL statement to insert a user.
     */
    private PreparedStatement insertUser;

    /**
     * A precompiled SQL statement to retrieve a given user.
     */
    private PreparedStatement getUser;

    /**
     * A precompiled SQL statement to update a given user's password.
     */
    private PreparedStatement updatePassword;

    /**
     * Creates a DBStatements object.
     */
    public DBStatements() {
        connection = DBConnection.getConnection();

        try {
            insertUser = connection.prepareStatement(INSERT_USER);
            getUser = connection.prepareStatement(GET_USER);
            updatePassword = connection.prepareStatement(UPDATE_PASSWORD);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Adds the provided new user to the database.
     *
     * @param newUser A new user account.
     */
    public void addNewUser(NewUser newUser) {
        try {
            insertUser.setString(1, newUser.getUsername());
            insertUser.setString(2, newUser.getPassword());
            insertUser.setString(3, newUser.getAdmin());

            insertUser.execute();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Retrieves the existing user's details from the database that correspond to the provided credential's username.
     *
     * @param credentials A set of login credentials.
     * @return An existing user account.
     */
    public ExistingUser getExistingUser(Credentials credentials) {
        ExistingUser existingUser = new ExistingUser();
        ResultSet resultSet;

        try {
            getUser.setString(1, credentials.getUsername());

            resultSet = getUser.executeQuery();
            resultSet.next();

            existingUser.setUsername(resultSet.getString("username"));
            existingUser.setPassword(resultSet.getString("password"));
            existingUser.setAdmin(resultSet.getBoolean("admin"));
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return existingUser;
    }

    /**
     * Changes the provided existing user's password in the database.
     *
     * @param existingUser An existing user account.
     */
    public void changePassword(ExistingUser existingUser) {
        try {
            updatePassword.setString(1, existingUser.getPassword());
            updatePassword.setString(2, existingUser.getUsername());
            updatePassword.execute();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
