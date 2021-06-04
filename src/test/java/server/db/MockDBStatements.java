package server.db;

import common.dataTypes.TradeType;
import common.domain.*;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MockDBStatements implements DBStrategy {
    public ArrayList<User> users;
    public ArrayList<Trade> trades;
    public ArrayList<Asset> assets;
    public ArrayList<FullAsset> fullAssets;
    public ArrayList<Unit> units;
    public ArrayList<UnitAsset> unitAssets;

    public MockDBStatements() {
        this.users = new ArrayList<>();
        users.add(new User("ID1", "Harry", "", false));
        users.add(new User("ID2", "Bruce", "", true));
        users.add(new User("ID3", "Heidi", "", false));
        users.add(new User("ID4", "Maddy", "", true));
        users.add(new User("ID5", "George", "", false));

        this.assets = new ArrayList<>();
        assets.add(new Asset("ID6", "Beef"));
        assets.add(new Asset("ID7", "Lettuce"));
        assets.add(new Asset("ID8", "Staplers"));
        assets.add(new Asset("ID9", "Pencils"));

        this.fullAssets = new ArrayList<>();
        this.fullAssets.add(new FullAsset("ID6", "Beef", LocalDate.EPOCH, 80));
        this.fullAssets.add(new FullAsset("ID7", "Lettuce", LocalDate.EPOCH, 80));
        this.fullAssets.add(new FullAsset("ID8", "Staplers", LocalDate.EPOCH, 80));
        this.fullAssets.add(new FullAsset("ID9", "Pencils", LocalDate.EPOCH, 80));

        this.units = new ArrayList<>();
        units.add(new Unit("ID10", "Engineering", 120));
        units.add(new Unit("ID11", "Manufacturing", 170));
        units.add(new Unit("ID12", "Accounting", 90));

        for (Unit unit : units) {
            User user1 = findUserById("ID1");
            User user2 = findUserById("ID2");
            User user3 = findUserById("ID3");
            User user4 = findUserById("ID4");
            User user5 = findUserById("ID5");
            unit.addUser(user1);
            unit.addUser(user2);
            unit.addUser(user3);
            unit.addUser(user4);
            unit.addUser(user5);
            user1.addUnit(unit.getUnitId());
            user2.addUnit(unit.getUnitId());
            user3.addUnit(unit.getUnitId());
            user4.addUnit(unit.getUnitId());
            user5.addUnit(unit.getUnitId());
        }

        this.unitAssets = new ArrayList<>();
        this.unitAssets.add(new UnitAsset("ID10", findAssetById("ID6"), 30));
        this.unitAssets.add(new UnitAsset("ID11", findAssetById("ID8"), 20));
        this.unitAssets.add(new UnitAsset("ID12", findAssetById("ID9"), 100));

        this.trades = new ArrayList<>();
        this.trades.add(
                new Trade(
                        "ID13",
                        findUnitById("ID10"),
                        findAssetById("ID9"),
                        findUserById("ID1"),
                        LocalDate.EPOCH,
                        TradeType.BUY,
                        10,
                        2,
                        0,
                        null
                )
        );
        this.trades.add(
                new Trade(
                        "ID14",
                        findUnitById("ID10"),
                        findAssetById("ID9"),
                        findUserById("ID1"),
                        LocalDate.EPOCH,
                        TradeType.SELL,
                        20,
                        3,
                        0,
                        null
                )
        );
    }

    @Override
    public void createTrade(Trade trade) {
        trades.add(trade);
    }

    @Override
    public void addAsset(Asset asset) {
        FullAsset fullAsset = new FullAsset(asset.getAssetId(), asset.getAssetName(), LocalDate.now(), 0);
        assets.add(asset);
        fullAssets.add(fullAsset);
    }

    @Override
    public void addNewUser(User user) {
        users.add(user);
    }

    @Override
    public @Nullable User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public @Nullable User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updatePassword(User user) {
        User existingUser = findUserById(user.getUserId());
        if (existingUser == null) {
            return;
        }
        existingUser.setPassword(user.getPassword());
    }

    @Override
    public ArrayList<Trade> getActiveTrades() {
        return trades.stream()
                .filter(trade -> trade.getDateFilled() == null)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Trade> fetchHistoricTrades() {
        return trades.stream()
                .filter(trade -> trade.getDateFilled() != null)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Unit> findUserUnits(User user) {
        return units.stream()
                .filter(unit -> unit.getUsers().contains(user))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<FullAsset> findAssets() {
        return fullAssets;
    }

    @Override
    public ArrayList<Trade> findUnitTrades(String unitId) {
        return trades.stream()
                .filter(trade -> trade.getUnit().getUnitId().equals(unitId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void removeTrade(Trade trade) throws SQLException {
        trades.remove(trade);
    }

    @Override
    public @Nullable Trade findTradeById(String tradeId) {
        for (Trade trade : trades) {
            if (trade.getTradeId().equals(tradeId)) {
                return trade;
            }
        }

        return null;
    }

    @Override
    public ArrayList<Unit> findUnits() {
        return units;
    }

    @Override
    public boolean updateUnitCredits(Unit unit) {
        return false;
    }

    @Override
    public @Nullable Unit findUnitById(String unitId) {
        return null;
    }

    @Override
    public ArrayList<UnitAsset> findUnitAssetsByUnit(String unitId) {
        return null;
    }

    @Override
    public @Nullable UnitAsset findUnitAsset(String unitId, String assetId) {
        return null;
    }

    @Override
    public void removeUnitAsset(UnitAsset unitAsset) throws SQLException {

    }

    @Override
    public @Nullable Asset findAssetById(String assetId) {
        return null;
    }

    @Override
    public void updateUnitAsset(UnitAsset unitAsset) {

    }

    @Override
    public void addUnitAsset(UnitAsset unitAsset) {

    }

    @Override
    public ArrayList<User> fetchUnitUsers(String unitId) {
        Unit unit = findUnitById(unitId);
        if (unit == null) return null;
        return unit.getUsers();
    }

    @Override
    public void updateTrade(Trade trade) {

    }

    @Override
    public ArrayList<User> fetchUsers() {
        return users;
    }

    @Override
    public void updateUserPermissions(User user) {

    }

    @Override
    public void removeAsset(Asset asset) throws SQLException {
        assets.remove(asset);
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        users.remove(user);
    }

    @Override
    public void addUserToUnit(User user, Unit unit) throws SQLException {
        unit.addUser(user);
        user.addUnit(unit.getUnitId());
    }

    @Override
    public void removeUserFromUnit(User user, Unit unit) throws SQLException {
        unit.getUsers().remove(user);
        user.getUnits().remove(unit.getUnitId());
    }
}
