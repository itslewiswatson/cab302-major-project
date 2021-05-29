package server;

public class DBQueries {
    /**
     * SQL statement to retrieve a user by their username
     */
    public static final String FIND_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ? LIMIT 1";

    /**
     * SQL statement to retrive a user by their ID
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
    public static final String GET_ACTIVE_TRADES = "SELECT * FROM trades INNER JOIN assets ON asset_id = assets.id INNER JOIN users ON users.id = user_id WHERE date_filled IS NULL";

    /**
     * SQL statement to select
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
     * SQL statement to insert a new a asset.
     */
    public static final String NEW_ASSET = "INSERT INTO assets (id, name, date_added) VALUES (?, ?, NOW())";

    /**
     * SQL statement to retrieve all active trades for a specific unit.
     */
    public static final String GET_UNIT_ACTIVE_TRADES = "SELECT * FROM trades T " +
            "INNER JOIN assets A ON asset_id = A.id " +
            "INNER JOIN users U ON user_id = U.id " +
            "WHERE unit_id = ? " +
            "AND date_filled IS NULL";
}
