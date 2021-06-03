package client;

import client.strategy.ClientController;
import client.strategy.Controller;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController extends Controller implements Initializable {
    public TestController(ClientController clientController) {
        super(clientController);
    }

    private URL url;

    public URL getUrl() {
        return url;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.url = url;
    }
}