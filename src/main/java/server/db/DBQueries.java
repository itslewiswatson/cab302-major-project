package server.db;

public class DBQueries {
    /**
     * SQL statement to retrieve a user by their username
     */
    public static final String FIND_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ? LIMIT 1";

    /**
     * SQL statement to retrieve a user by their ID
     */
    public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ? LIMIT 1";

    /**
     * SQL statement to retrieve the organisation units of a given user.
     */
    public static final String GET_USER_UNITS = "SELECT unit_id FROM unitusers WHERE user_id = ?";

    /**
     * SQL statement to insert a user.
     */
    public static final String INSERT_USER = "INSERT INTO users (id, username, password, admin) VALUES (?, ?, ?, ?);";

    /**
     * SQL statement to update a given user's password.
     */
    public static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE ID = ?";

    /**
     * SQL statement to select unfulfilled trades.
     */
    public static final String GET_ACTIVE_TRADES = "SELECT * FROM trades INNER JOIN units ON unit_id = units.id INNER JOIN assets ON asset_id = assets.id INNER JOIN users ON user_id = users.id WHERE date_filled IS NULL";

    /**
     * SQL statement to blindly retrieve all units.
     */
    public static final String GET_UNITS = "SELECT * FROM units";

    /**
     * SQL statement to retrieve a unit by its ID.
     */
    public static final String GET_UNIT_BY_ID = "SELECT * FROM units WHERE id = ?";

    /**
     * SQL statement to select a user's given units.
     */
    public static final String GET_UNITS_BY_IDS = "SELECT id, name, credits FROM units WHERE id IN (SELECT unit_id FROM unitusers WHERE user_id = ?)";

    /**
     * SQL statement to insert a new trade.
     */
    public static final String NEW_TRADE = "" +
            "INSERT INTO trades (id, unit_id, asset_id, user_id, date_listed, type, quantity, price, quantity_filled, date_filled) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    /**
     * SQL statement to retrieve assets and their amounts.
     */
    public static final String GET_ASSETS = "SELECT A.id, A.name, A.date_added, COALESCE(SUM(quantity), 0) AS qty FROM assets A LEFT JOIN unitassets u on A.id = u.asset_id GROUP BY A.id;";

    /**
     * SQL statement to retrieve an asset by its ID.
     */
    public static final String GET_ASSET_BY_ID = "SELECT * FROM assets WHERE id = ?";

    /**
     * SQL statement to retrieve an asset by its name.
     */
    public static final String GET_ASSET_BY_NAME = "SELECT * FROM assets WHERE name = ?";

    /**
     * SQL statement to insert a new a asset.
     */
    public static final String NEW_ASSET = "INSERT INTO assets (id, name, date_added) VALUES (?, ?, NOW())";

    /**
     * SQL statement to retrieve all trades for a specific unit.
     */
    public static final String GET_UNIT_TRADES = "SELECT * FROM trades T " +
            "INNER JOIN assets A ON asset_id = A.id " +
            "INNER JOIN users U ON user_id = U.id " +
            "INNER JOIN units on units.id = unit_id " +
            "WHERE unit_id = ?";

    /**
     * SQL statement to find a single trade by its ID.
     */
    public static final String FIND_TRADE_BY_ID = "SELECT * FROM trades " +
            "INNER JOIN units ON unit_id = units.id " +
            "INNER JOIN assets ON asset_id = assets.id " +
            "INNER JOIN users ON user_id = users.id " +
            "WHERE trades.id = ? " +
            "AND date_filled IS NULL";

    /**
     * SQL statement to delete a trade.
     */
    public static final String DELETE_TRADE = "DELETE FROM trades WHERE id = ?";

    /**
     * SQL statement to update the credit's of a specified unit.
     */
    public static final String UPDATE_UNIT = "UPDATE units SET credits = ? WHERE id = ?";

    /**
     * SQL statement to fetch unit assets by unit ID;
     */
    public static final String GET_UNIT_ASSETS_BY_UNIT = "SELECT * FROM unitassets INNER JOIN assets A ON asset_id = A.id WHERE unit_id = ?";

    /**
     * SQL statement to fetch a user asset by its ID.
     */
    public static final String GET_UNIT_ASSET = "SELECT * FROM unitassets INNER JOIN assets A ON asset_id = A.id WHERE unit_id = ? AND asset_id = ?";

    /**
     * SQL statement to remove a unit asset by its ID.
     */
    public static final String REMOVE_UNIT_ASSET = "DELETE FROM unitassets WHERE unit_id = ? AND asset_id = ?";

    public static final String UPDATE_UNIT_ASSET = "UPDATE unitassets SET quantity = ? WHERE unit_id = ? AND asset_id = ?";

    public static final String ADD_UNIT_ASSET = "INSERT INTO unitassets (unit_id, asset_id, quantity) VALUES (?, ?, ?)";

    public static final String GET_HISTORIC_TRADES = "SELECT * FROM trades " +
            "INNER JOIN assets ON asset_id = assets.id " +
            "INNER JOIN users ON users.id = user_id " +
            "INNER JOIN units ON units.id = unit_id " +
            "WHERE date_filled IS NOT NULL";

    public static final String GET_UNIT_USERS = "SELECT * FROM users WHERE id IN (SELECT user_id FROM unitusers WHERE unit_id = ?)";

    public static final String UPDATE_TRADE = "UPDATE trades SET quantity_filled = ?, date_filled = ? WHERE id = ?";

    public static final String GET_USERS = "SELECT * FROM users";

    public static final String UPDATE_USER_PERMISSION = "UPDATE users SET admin = ? WHERE id = ?";

    public static final String DELETE_ASSET = "DELETE FROM assets WHERE id = ?";

    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public static final String ADD_USER_TO_UNIT = "INSERT INTO unitusers (user_id, unit_id) VALUES (?, ?)";

    public static final String REMOVE_USER_FROM_UNIT = "DELETE FROM unitusers WHERE user_id = ? AND unit_id = ?";

    public static final String FIND_UNIT_BY_NAME = "SELECT * FROM units WHERE name = ?";

    public static final String NEW_UNIT = "INSERT INTO units (id, name, credits) VALUES (?, ?, ?)";
}
