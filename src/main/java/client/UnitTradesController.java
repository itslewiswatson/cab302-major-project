package client;

import client.config.Page;
import common.domain.Trade;
import common.dto.GetUnitTradesDTO;
import common.exceptions.NullResultException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UnitTradesController extends Controller implements Initializable {
    public UnitTradesController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private ComboBox<String> unitComboBox;

    @FXML
    private TableView<Trade> tableView;

    private ArrayList<Trade> fetchUnitTrades(String unitId) {
        sendObject(new GetUnitTradesDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        setupRows();
    }

    private void populateTable() {
//        ArrayList<Trade> trades = fetchUnitTrades();
//        tableView.setItems(FXCollections.observableArrayList(trades));
    }

    private void setupRows() {
    }

    @SuppressWarnings("all")
    private void setupColumns() {
//        dateListed.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getDateListed().toString()));
//        asset.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAsset().getAssetName()));
//        quantity.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getQuantity() - p.getValue().getQuantityFilled()));
//        price.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPrice()));
//        tradeType.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getType()));
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }
}
