package server.db;

import common.domain.*;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBStrategy {
    void createTrade(Trade trade);

    void addAsset(Asset asset);

    void addUser(User user) throws SQLException;

    @Nullable User findUserById(String userId);

    @Nullable User findUserByUsername(String username);

    void updatePassword(User user);

    ArrayList<Trade> getActiveTrades();

    ArrayList<Trade> fetchHistoricTrades();

    ArrayList<Unit> findUserUnits(User user);

    ArrayList<FullAsset> findAssets();

    ArrayList<Trade> findUnitTrades(String unitId);

    void removeTrade(Trade trade) throws SQLException;

    @Nullable Trade findTradeById(String tradeId);

    ArrayList<Unit> findUnits();

    boolean updateUnitCredits(Unit unit);

    @Nullable Unit findUnitById(String unitId);

    ArrayList<UnitAsset> findUnitAssetsByUnit(String unitId);

    @Nullable UnitAsset findUnitAsset(String unitId, String assetId);

    void removeUnitAsset(UnitAsset unitAsset) throws SQLException;

    @Nullable FullAsset findAssetById(String assetId);

    @Nullable Asset findAssetByName(String assetName);

    void updateUnitAsset(UnitAsset unitAsset);

    void addUnitAsset(UnitAsset unitAsset);

    ArrayList<User> fetchUnitUsers(String unitId);

    ArrayList<User> fetchUsers();

    void updateUserPermissions(User user);

    void removeAsset(Asset asset) throws SQLException;

    void deleteUser(User user) throws SQLException;

    void addUserToUnit(User user, Unit unit) throws SQLException;

    void removeUserFromUnit(User user, Unit unit) throws SQLException;

    @Nullable Unit findUnitByName(String unitName);

    void addUnit(Unit unit) throws SQLException;
}
