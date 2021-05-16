package client;

public enum Page {
    myAccount("myAccount.fxml"),
    login("login.fxml");

    public final String path;

    Page(String path) {
        this.path = path;
    }
}
