package client;

import client.config.Page;
import common.domain.FullAsset;
import common.dto.GetAssetsDTO;
import common.exceptions.NullResultException;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AssetsController extends Controller implements Initializable {
    public AssetsController(ClientController clientController) {
        super(clientController);
    }

    private ArrayList<FullAsset> fetchAssets() {
        sendObject(new GetAssetsDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(fetchAssets());
    }
}
