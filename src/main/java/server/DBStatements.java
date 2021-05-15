package server;

import common.Username;
import common.ExistingUser;
import common.NewUser;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class controls database access and contains its required statements.
 */
public class DBStatements {
    /**
     * SQL statement to retrieve a given user.
     */
    private static final String GET_USER = "SELECT * FROM user WHERE username = ?";

    /**
     * SQL statement to retrieve the organisation units of a given user.
     */
    private static final String GET_USER_UNITS = "SELECT unitName FROM unitusers WHERE username = ?";

    /**
     * SQL statement to insert a user.
     */
    private static final String INSERT_USER = "INSERT INTO user (username, password, admin) VALUES (?, ?, ?);";

    /**
     * SQL statement to update a given user's password.
     */
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE username = ?";

    /**
     * A precompiled SQL statement to insert a user.
     */
    private PreparedStatement insertUser;

    /**
     * A precompiled SQL statement to retrieve a given user.
     */
    private PreparedStatement getUser;

    /**
     * A precompiled SQL statement to retrieve the organisation units of a given user.
     */
    private PreparedStatement getUserUnits;

    /**
     * A precompiled SQL statement to update a given user's password.
     */
    private PreparedStatement updatePassword;

    /**
     * Creates a DBStatements object.
     */
    public DBStatements() {
        Connection connection = DBConnection.getConnection();

        try {
            insertUser = connection.prepareStatement(INSERT_USER);
            getUser = connection.prepareStatement(GET_USER);
            getUserUnits = connection.prepareStatement(GET_USER_UNITS);
            updatePassword = connection.prepareStatement(UPDATE_PASSWORD);
        }
        catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
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
     * @param username A set of login credentials.
     * @return An existing user account.
     */
    public ExistingUser getExistingUser(Username username) {
        ExistingUser existingUser = null;
        ResultSet userResultSet;
        ResultSet unitResultSet;
        ArrayList<String> unitsList = new ArrayList<>();

        try {
            getUser.setString(1, username.getUsername());
            getUserUnits.setString(1, username.getUsername());

            userResultSet = getUser.executeQuery();

            if (userResultSet.isBeforeFirst()) {
                userResultSet.next();

                unitResultSet = getUserUnits.executeQuery();
                while (unitResultSet.next()) {
                    unitsList.add(unitResultSet.getString(1));
                }
                String[] units = new String[unitsList.size()];
                units = unitsList.toArray(units);

                existingUser = new ExistingUser(userResultSet.getString("username"), userResultSet.getString("password"), userResultSet.getBoolean("admin"), units);
            }
            else
            {
                existingUser = new ExistingUser(null, null, false, null);
            }
        }
        catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        }
        catch (Exception ignored) {
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
}
