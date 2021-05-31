package server;

import common.dataTypes.TradeType;
import common.domain.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class controls database access and contains its required statements.
 */
public class DBStatements {
    /**
     * A precompiled SQL statement to insert a user.
     */
    private PreparedStatement insertUser;

    /**
     * A precompiled SQL statement to retrieve a given user.
     */
    private PreparedStatement getUserByUsername;

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
     * A precompiled SQL statement to retrieve all unfulfilled trades.
     */
    private PreparedStatement getActiveTrades;

    /**
     * A precompiled SQL statement to retrieve all fulfilled trades.
     */
    private PreparedStatement getHistoricTrades;

    /**
     * A precompiled SQL statement to retrieve all units.
     */
    private PreparedStatement getUnits;

    /**
     * A precompiled SQL statement to retrieve a unit by its ID.
     */
    private PreparedStatement getUnitById;

    /**
     * A precompiled SQL statement to retrieve units by their IDs.
     */
    private PreparedStatement getUnitsByIds;

    /**
     * A precompiled SQL statement to insert a new trade.
     */
    private PreparedStatement newTrade;

    /**
     * A precompiled SQL statement to retrieve all assets.
     */
    private PreparedStatement getAssets;

    /**
     * A precompiled SQL statement to retrieve an asset by its ID.
     */
    private PreparedStatement getAssetById;

    /**
     * A precompiled SQL statement to insert a new asset.
     */
    private PreparedStatement newAsset;

    /**
     * A precompiled SQL statement to retrieve all trades for a given unit.
     */
    private PreparedStatement getUnitTrades;

    /**
     * A precompiled SQL statement to delete a trade.
     */
    private PreparedStatement deleteTrade;

    /**
     * A precompiled SQL statement to find a trade by its ID.
     */
    private PreparedStatement getTradeById;

    /**
     * A precompiled SQL statement to update a unit.
     */
    private PreparedStatement updateUnit;

    /**
     * A precompiled SQL statement to get unit assets by a unit's ID.
     */
    private PreparedStatement getUnitAssetsById;

    /**
     * A precompiled to get a unit asset by its composite key.
     */
    private PreparedStatement getUnitAsset;

    /**
     * A precompiled SQL statement to remove a unit asset.
     */
    private PreparedStatement deleteUnitAsset;

    private PreparedStatement createUnitAsset;

    private PreparedStatement updateUnitAssetQuantity;

    private PreparedStatement getUnitUsers;

    /**
     * Creates a DBStatements object.
     */
    public DBStatements() {
        Connection connection = DBConnection.getConnection();

        try {
            insertUser = connection.prepareStatement(DBQueries.INSERT_USER);
            getUserByUsername = connection.prepareStatement(DBQueries.FIND_USER_BY_USERNAME);
            getUserById = connection.prepareStatement(DBQueries.FIND_USER_BY_ID);
            getUserUnits = connection.prepareStatement(DBQueries.GET_USER_UNITS);
            updatePassword = connection.prepareStatement(DBQueries.UPDATE_PASSWORD);
            getActiveTrades = connection.prepareStatement(DBQueries.GET_ACTIVE_TRADES);
            getUnitsByIds = connection.prepareStatement(DBQueries.GET_UNITS_BY_IDS);
            newTrade = connection.prepareStatement(DBQueries.NEW_TRADE);
            getAssets = connection.prepareStatement(DBQueries.GET_ASSETS);
            newAsset = connection.prepareStatement(DBQueries.NEW_ASSET);
            getUnitTrades = connection.prepareStatement(DBQueries.GET_UNIT_ACTIVE_TRADES);
            deleteTrade = connection.prepareStatement(DBQueries.DELETE_TRADE);
            getTradeById = connection.prepareStatement(DBQueries.FIND_TRADE_BY_ID);
            getUnits = connection.prepareStatement(DBQueries.GET_UNITS);
            getUnitById = connection.prepareStatement(DBQueries.GET_UNIT_BY_ID);
            updateUnit = connection.prepareStatement(DBQueries.UPDATE_UNIT);
            getUnitAssetsById = connection.prepareStatement(DBQueries.GET_UNIT_ASSETS_BY_UNIT);
            getUnitAsset = connection.prepareStatement(DBQueries.GET_UNIT_ASSET);
            deleteUnitAsset = connection.prepareStatement(DBQueries.REMOVE_UNIT_ASSET);
            getAssetById = connection.prepareStatement(DBQueries.GET_ASSET_BY_ID);
            createUnitAsset = connection.prepareStatement(DBQueries.ADD_UNIT_ASSET);
            updateUnitAssetQuantity = connection.prepareStatement(DBQueries.UPDATE_UNIT_ASSET);
            getHistoricTrades = connection.prepareStatement(DBQueries.GET_HISTORIC_TRADES);
            getUnitUsers = connection.prepareStatement(DBQueries.GET_UNIT_USERS);
        } catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        }
    }

    public void createTrade(Trade trade) {
        try {
            newTrade.setString(1, trade.getTradeId());
            newTrade.setString(2, trade.getUnitId());
            newTrade.setString(3, trade.getAsset().getAssetId());
            newTrade.setString(4, trade.getUser().getUserId());
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

    public void addAsset(Asset asset) {
        try {
            newAsset.setString(1, asset.getAssetId());
            newAsset.setString(2, asset.getAssetName());
            newAsset.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
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

                user = new User(
                        userResultSet.getString("id"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password"),
                        userResultSet.getBoolean("admin")
                );

                String[] units = findUserUnitIds(user);
                for (String unitId : units) {
                    user.addUnit(unitId);
                }

                return user;
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
    public User findUserByUsername(String username) {
        User user = null;
        ResultSet userResultSet;

        try {
            getUserByUsername.setString(1, username);
            userResultSet = getUserByUsername.executeQuery();

            if (userResultSet.isBeforeFirst()) {
                userResultSet.next();

                user = new User(
                        userResultSet.getString("id"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password"),
                        userResultSet.getBoolean("admin")
                );

                String[] units = findUserUnitIds(user);
                for (String unitId : units) {
                    user.addUnit(unitId);
                }

                return user;
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
    public void updatePassword(User user) {
        try {
            updatePassword.setString(1, user.getPassword());
            updatePassword.setString(2, user.getUserId());
            updatePassword.execute();
        } catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        }
    }

    /**
     * Retrieves the user's units
     *
     * @param user An existing user account.
     * @return A list of units the user is part of.
     */
    private String[] findUserUnitIds(User user) {
        ResultSet unitResultSet;
        ArrayList<String> unitsList = new ArrayList<>();

        try {
            getUserUnits.setString(1, user.getUserId());
            unitResultSet = getUserUnits.executeQuery();
            while (unitResultSet.next()) {
                unitsList.add(unitResultSet.getString(1));
            }
            String[] units = new String[unitsList.size()];
            units = unitsList.toArray(units);
            return units;
        } catch (SQLException exception) {
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        } catch (Exception ignored) {
        }

        String[] units = new String[unitsList.size()];
        units = unitsList.toArray(units);
        return units;
    }

    public ArrayList<Trade> getActiveTrades() {
        ResultSet tradeResultSet;
        ArrayList<Trade> trades = new ArrayList<>();

        try {
            tradeResultSet = getActiveTrades.executeQuery();

            while (tradeResultSet.next()) {
                Trade trade = new Trade(
                        tradeResultSet.getString("id"),
                        tradeResultSet.getString("unit_id"),
                        new Asset(
                                tradeResultSet.getString("asset_id"),
                                tradeResultSet.getString("name")
                        ),
                        new User(
                                tradeResultSet.getString("users.id"),
                                tradeResultSet.getString("users.username"),
                                tradeResultSet.getString("users.password"),
                                tradeResultSet.getBoolean("users.admin")
                        ),
                        tradeResultSet.getDate("date_listed").toLocalDate(),
                        TradeType.valueOf(tradeResultSet.getString("type")),
                        tradeResultSet.getInt("quantity"),
                        tradeResultSet.getInt("price"),
                        tradeResultSet.getInt("quantity_filled"),
                        null
                );
                trades.add(trade);
            }
            return trades;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return trades;
    }

    public ArrayList<Trade> fetchHistoricTrades() {
        ResultSet tradeResultSet;
        ArrayList<Trade> trades = new ArrayList<>();

        try {
            tradeResultSet = getHistoricTrades.executeQuery();

            while (tradeResultSet.next()) {
                Trade trade = new Trade(
                        tradeResultSet.getString("id"),
                        tradeResultSet.getString("unit_id"),
                        new Asset(
                                tradeResultSet.getString("asset_id"),
                                tradeResultSet.getString("name")
                        ),
                        new User(
                                tradeResultSet.getString("users.id"),
                                tradeResultSet.getString("users.username"),
                                tradeResultSet.getString("users.password"),
                                tradeResultSet.getBoolean("users.admin")
                        ),
                        tradeResultSet.getDate("date_listed").toLocalDate(),
                        TradeType.valueOf(tradeResultSet.getString("type")),
                        tradeResultSet.getInt("quantity"),
                        tradeResultSet.getInt("price"),
                        tradeResultSet.getInt("quantity_filled"),
                        null
                );
                trades.add(trade);
            }

            return trades;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return trades;
    }

    /**
     * Retrieve units corresponding to their IDs
     *
     * @return Array of units
     */
    public ArrayList<Unit> findUserUnits(User user) {
        ResultSet unitResultSet;
        ArrayList<Unit> units = new ArrayList<>();

        try {
            getUnitsByIds.setString(1, user.getUserId());
            unitResultSet = getUnitsByIds.executeQuery();

            while (unitResultSet.next()) {
                Unit unit = new Unit(
                        unitResultSet.getString("id"),
                        unitResultSet.getString("name"),
                        unitResultSet.getInt("credits")
                );
                units.add(unit);
            }

            return units;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return units;
    }

    /**
     * @return
     */
    public ArrayList<FullAsset> findAssets() {
        ResultSet assetResultSet;
        ArrayList<FullAsset> assets = new ArrayList<>();

        try {
            assetResultSet = getAssets.executeQuery();

            while (assetResultSet.next()) {
                FullAsset fullAsset = new FullAsset(
                        assetResultSet.getString("A.id"),
                        assetResultSet.getString("A.name"),
                        assetResultSet.getDate("A.date_added").toLocalDate(),
                        assetResultSet.getInt("qty")
                );
                assets.add(fullAsset);
            }

            return assets;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return assets;
    }

    public ArrayList<Trade> findUnitTrades(String unitId) {
        ResultSet tradeResultSet;
        ArrayList<Trade> trades = new ArrayList<>();

        try {
            getUnitTrades.setString(1, unitId);
            tradeResultSet = getUnitTrades.executeQuery();

            while (tradeResultSet.next()) {
                Trade trade = new Trade(
                        tradeResultSet.getString("T.id"),
                        tradeResultSet.getString("T.unit_id"),
                        new Asset(
                                tradeResultSet.getString("A.id"),
                                tradeResultSet.getString("A.name")
                        ),
                        new User(
                                tradeResultSet.getString("U.id"),
                                tradeResultSet.getString("username"),
                                tradeResultSet.getString("password"),
                                tradeResultSet.getBoolean("admin")
                        ),
                        tradeResultSet.getDate("date_listed").toLocalDate(),
                        TradeType.valueOf(tradeResultSet.getString("type")),
                        tradeResultSet.getInt("quantity"),
                        tradeResultSet.getInt("price"),
                        tradeResultSet.getInt("quantity_filled"),
                        null
                );
                trades.add(trade);
            }

            return trades;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return trades;
    }

    public void removeTrade(Trade trade) throws SQLException {
        deleteTrade.setString(1, trade.getTradeId());
        deleteTrade.executeQuery();
    }

    public Trade findTradeById(String tradeId) {
        ResultSet tradeResultSet;
        Trade trade = null;

        try {
            getTradeById.setString(1, tradeId);
            tradeResultSet = getTradeById.executeQuery();

            if (tradeResultSet.isBeforeFirst()) {
                tradeResultSet.next();

                trade = new Trade(
                        tradeResultSet.getString("T.id"),
                        tradeResultSet.getString("T.unit_id"),
                        new Asset(
                                tradeResultSet.getString("A.id"),
                                tradeResultSet.getString("A.name")
                        ),
                        new User(
                                tradeResultSet.getString("U.id"),
                                tradeResultSet.getString("username"),
                                tradeResultSet.getString("password"),
                                tradeResultSet.getBoolean("admin")
                        ),
                        tradeResultSet.getDate("date_listed").toLocalDate(),
                        TradeType.valueOf(tradeResultSet.getString("type")),
                        tradeResultSet.getInt("quantity"),
                        tradeResultSet.getInt("price"),
                        tradeResultSet.getInt("quantity_filled"),
                        null
                );

                return trade;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return trade;
    }

    public ArrayList<Unit> findUnits() {
        ResultSet unitResultSet;
        ArrayList<Unit> units = new ArrayList<>();

        try {
            unitResultSet = getUnits.executeQuery();

            while (unitResultSet.next()) {
                Unit unit = new Unit(
                        unitResultSet.getString("id"),
                        unitResultSet.getString("name"),
                        unitResultSet.getInt("credits")
                );
                units.add(unit);
            }

            return units;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.err.println("Access to the database was denied. Ensure MySQL server is running.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return units;
    }

    public void updateUnitCredits(Unit unit) throws SQLException {
        updateUnit.setInt(1, unit.getCredits());
        updateUnit.setString(2, unit.getUnitId());
        updateUnit.executeQuery();
    }

    public Unit findUnitById(String unitId) {
        ResultSet unitResultSet;
        Unit unit = null;

        try {
            getUnitById.setString(1, unitId);
            unitResultSet = getUnitById.executeQuery();

            if (unitResultSet.isBeforeFirst()) {
                unitResultSet.next();

                unit = new Unit(
                        unitResultSet.getString("id"),
                        unitResultSet.getString("name"),
                        unitResultSet.getInt("credits")
                );

                return unit;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return unit;
    }

    public ArrayList<UnitAsset> findUnitAssetsByUnit(String unitId) {
        ResultSet unitAssetResultSet;
        ArrayList<UnitAsset> unitAssets = new ArrayList<>();

        try {
            getUnitAssetsById.setString(1, unitId);
            unitAssetResultSet = getUnitAssetsById.executeQuery();

            while (unitAssetResultSet.next()) {
                UnitAsset unitAsset = new UnitAsset(
                        unitAssetResultSet.getString("unit_id"),
                        new Asset(
                                unitAssetResultSet.getString("A.id"),
                                unitAssetResultSet.getString("name")
                        ),
                        unitAssetResultSet.getInt("quantity")
                );
                unitAssets.add(unitAsset);
            }

            return unitAssets;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return unitAssets;
    }

    public UnitAsset findUnitAsset(String unitId, String assetId) {
        ResultSet unitAssetResultSet;
        UnitAsset unitAsset = null;

        try {
            getUnitAsset.setString(1, unitId);
            getUnitAsset.setString(2, assetId);
            unitAssetResultSet = getUnitAsset.executeQuery();

            if (unitAssetResultSet.isBeforeFirst()) {
                unitAssetResultSet.next();

                unitAsset = new UnitAsset(
                        unitAssetResultSet.getString("unit_id"),
                        new Asset(
                                unitAssetResultSet.getString("A.id"),
                                unitAssetResultSet.getString("name")
                        ),
                        unitAssetResultSet.getInt("quantity")
                );

                return unitAsset;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return unitAsset;
    }

    public void removeUnitAsset(UnitAsset unitAsset) throws SQLException {
        deleteUnitAsset.setString(1, unitAsset.getUnitId());
        deleteUnitAsset.setString(2, unitAsset.getAsset().getAssetId());
        deleteUnitAsset.executeQuery();
    }

    public Asset findAssetById(String assetId) throws SQLException {
        ResultSet assetResultSet;

        getAssetById.setString(1, assetId);
        assetResultSet = getAssetById.executeQuery();

        if (assetResultSet.isBeforeFirst()) {
            assetResultSet.next();

            return new Asset(
                    assetResultSet.getString("id"),
                    assetResultSet.getString("name")
            );
        }

        return null;
    }

    public void updateUnitAsset(UnitAsset unitAsset) throws SQLException {
        updateUnitAssetQuantity.setInt(1, unitAsset.getQuantity());
        updateUnitAssetQuantity.setString(2, unitAsset.getUnitId());
        updateUnitAssetQuantity.setString(3, unitAsset.getAsset().getAssetId());
        updateUnitAssetQuantity.execute();
    }

    public void addUnitAsset(UnitAsset unitAsset) throws SQLException {
        createUnitAsset.setString(1, unitAsset.getUnitId());
        createUnitAsset.setString(2, unitAsset.getAsset().getAssetId());
        createUnitAsset.setInt(3, unitAsset.getQuantity());
        createUnitAsset.execute();
    }

    public ArrayList<User> fetchUnitUsers(String unitId) {
        ResultSet userResultSet;
        ArrayList<User> users = new ArrayList<>();

        try {
            getUnitUsers.setString(1, unitId);
            userResultSet = getUnitUsers.executeQuery();

            while (userResultSet.next()) {
                User user = new User(
                        userResultSet.getString("id"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password"),
                        userResultSet.getBoolean("admin")
                );
                users.add(user);
            }

            return users;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return users;
    }
}
