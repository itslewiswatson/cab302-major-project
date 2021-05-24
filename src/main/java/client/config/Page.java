package client.config;

public enum Page {
    myAccount("myAccount.fxml", "client.MyAccountController"),
    login("login.fxml", "client.LoginController");

    public final String path;
    public final String namespace;

    Page(String path, String namespace) {
        this.path = path;
        this.namespace = namespace;
    }
}
