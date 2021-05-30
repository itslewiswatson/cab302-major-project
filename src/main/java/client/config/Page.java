package client.config;

public enum Page {
    myAccount("myAccount.fxml", "client.MyAccountController"),
    login("login.fxml", "client.LoginController"),
    allTrades("allTrades.fxml", "client.AllTradesController"),
    unitTrades("unitTrades.fxml", "client.UnitTradesController"),
    assets("assets.fxml", "client.AssetsController"),
    manageUnits("manageUnits.fxml", "client.ManageUnitsController");

    public final String path;
    public final String namespace;

    Page(String path, String namespace) {
        this.path = path;
        this.namespace = namespace;
    }
}
