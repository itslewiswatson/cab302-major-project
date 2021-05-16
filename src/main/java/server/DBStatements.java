package server;

import common.domain.Trade;
import common.domain.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class controls database access and contains its required statements.
 */
public class DBStatements {
    /**
     * SQL statement to retrieve a user by their username
     */
    private static final String GET_USER = "SELECT * FROM users WHERE username = ?";

    /**
     * SQL statement to retrive a user by their ID
     */
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    /**
     * SQL statement to retrieve the organisation units of a given user.
     */
    private static final String GET_USER_UNITS = "SELECT unitName FROM unitusers WHERE username = ?";

    /**
     * SQL statement to insert a user.
     */
    private static final String INSERT_USER = "INSERT INTO users (username, password, admin) VALUES (?, ?, ?);";

    /**
     * SQL statement to update a given user's password.
     */
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE username = ?";

    /**
     * SQL statement to insert a new trade.
     */
    private static final String NEW_TRADE = "" +
            "INSERT INTO trades (tradeId, unitId, assetId, userId, dateListed, type, quantity, price, quantityFilled, dateFilled) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    /**
     * A precompiled SQL statement to insert a user.
     */
    private PreparedStatement insertUser;

    /**
     * A precompiled SQL statement to retrieve a given user.
     */
    private PreparedStatement getUser;

    /**
     * A precompiled SQL statement to retrieve a given user.
     */
    private PreparedStatement getUserById;

    /**
     * A precompiled SQL statement to retrieve the organisation units of a given user.
     */
    private PreparedStatement getUserUnits;

    /**
     * A precompiled SQL statement to update a given user's password.
     */
    private PreparedStatement updatePassword;

    /**
     * A precompiled SQL statement to inset a new trade.
     */
    private PreparedStatement newTrade;

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
            getUserById = connection.prepareStatement(FIND_USER_BY_ID);
        } catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        }
    }

    public void createTrade(Trade trade) {
        try {
            newTrade.setString(1, trade.getTradeId());
            newTrade.setString(2, trade.getUnitId());
            newTrade.setString(3, trade.getAssetId());
            newTrade.setString(4, trade.getUserId());
            newTrade.setDate(5, Date.valueOf(trade.getDateListed()));
            newTrade.setString(6, trade.getType().toString());
            newTrade.setInt(7, trade.getQuantity());
            newTrade.setInt(8, trade.getPrice());
            newTrade.setInt(9, trade.getQuantityFilled());
            newTrade.setDate(10, Date.valueOf(trade.getDateFilled()));

            newTrade.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Adds the provided new user to the database.
     *
     * @param user A new user account.
     */
    public void addNewUser(User user) {
        try {
            insertUser.setString(1, user.getUserId());
            insertUser.setString(2, user.getUsername());
            insertUser.setString(3, user.getPassword());
            insertUser.setString(4, user.getAdmin());

            insertUser.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public User findUserById(String userId) {
        User user = null;
        ResultSet userResultSet;

        try {
            getUserById.setString(1, userId);
            userResultSet = getUserById.executeQuery();
            if (userResultSet.isBeforeFirst()) {
                userResultSet.next();

                String[] units = {};

                user = new User(
                        userResultSet.getString("id"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password"),
                        userResultSet.getBoolean("admin"),
                        units
                );
            }
        } catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        } catch (Exception ignored) {
        }

        return user;
    }

    /**
     * Retrieves the existing user's details from the database that correspond to the provided credential's username.
     *
     * @param username A set of login credentials.
     * @return An existing user account.
     */
    public User getUserByUsername(String username) {
        User user = null;
        ResultSet userResultSet;
        ResultSet unitResultSet;
        ArrayList<String> unitsList = new ArrayList<>();

        try {
            getUser.setString(1, username);
            // getUserUnits.setString(1, username);

            userResultSet = getUser.executeQuery();

            if (userResultSet.isBeforeFirst()) {
                userResultSet.next();

//                unitResultSet = getUserUnits.executeQuery();
//                while (unitResultSet.next()) {®
//                    unitsList.add(unitResultSet.getString(1));
//                }
//                String[] units = new String[unitsList.size()];
//                units = unitsList.toArray(units);
                String[] units = {};

                user = new User(
                        userResultSet.getString("id"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password"),
                        userResultSet.getBoolean("admin"),
                        units
                );
            } else {
                user = new User(null, null, null, false, null);
            }
        } catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        } catch (Exception ignored) {
        }

        return user;
    }

    /**
     * Changes the provided existing user's password in the database.
     *
     * @param user An existing user account.
     */
    public void changePassword(User user) {
        try {
            updatePassword.setString(1, user.getPassword());
            updatePassword.setString(2, user.getUsername());
            updatePassword.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
