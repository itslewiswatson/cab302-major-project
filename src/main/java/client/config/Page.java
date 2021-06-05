package client.config;

import client.*;
import client.strategy.Controller;

public enum Page {
    myAccount("myAccount.fxml", MyAccountController.class),
    login("login.fxml", LoginController.class),
    allTrades("allTrades.fxml", AllTradesController.class),
    tradeHistory("tradeHistory.fxml", TradeHistoryController.class),
    unitTrades("unitTrades.fxml", UnitTradesController.class),
    assets("manageAssets.fxml", ManageAssetsController.class),
    manageUnits("manageUnits.fxml", ManageUnitsController.class),
    manageUsers("manageUsers.fxml", ManageUsersController.class);

    public final String path;
    public final Class<? extends Controller> namespace;

    Page(String path, Class<? extends Controller> namespace) {
        this.path = path;
        this.namespace = namespace;
    }
}
