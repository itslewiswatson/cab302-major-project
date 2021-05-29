package client;

import client.config.Page;
import common.domain.Trade;
import common.domain.Unit;
import common.dto.GetTradesDTO;
import common.dto.GetUnitTradesDTO;
import common.dto.GetUnitsDTO;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UnitTradesController extends Controller implements Initializable {
    public UnitTradesController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private ComboBox<Unit> unitComboBox;

    @FXML
    private TableView<Trade> tableView;

    @FXML
    private TableColumn<Trade, String> dateListed;

    @FXML
    private TableColumn<Trade, String> listedBy;

    @FXML
    private TableColumn<Trade, String> asset;

    @FXML
    private TableColumn<Trade, String> quantity;

    @FXML
    private TableColumn<Trade, String> price;

    @FXML
    private TableColumn<Trade, String> tradeType;

    private ArrayList<Trade> fetchUnitTrades(String unitId) {
        sendObject(new GetUnitTradesDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<Unit> fetchUserUnits() {
        sendObject(new GetUnitsDTO(getUser().getUserId()));
        try {
            return readObject();
        } catch (NullResultException e) {
            // TODO use an alert here instead
            return new ArrayList<>();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        setupRows();
        setupComboBox();
        populateUnitComboBox();
        populateTable();
    }

    private void setupComboBox() {
        unitComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Unit unit) {
                return unit != null ? unit.getUnitName() : "";
            }

            @Override
            public Unit fromString(String s) {
                return null;
            }
        });
    }

    private void populateUnitComboBox() {
        ArrayList<Unit> userUnits = fetchUserUnits();
        unitComboBox.setItems(FXCollections.observableArrayList(userUnits));
        if (userUnits.size() == 1) {
            unitComboBox.setValue(userUnits.get(0));
        }
    }

    private void populateTable() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit != null) {
            ArrayList<Trade> trades = fetchUnitTrades(selectedUnit.getUnitId());
            tableView.setItems(FXCollections.observableArrayList(trades));
        }
    }

    private void setupRows() {
    }

    @SuppressWarnings("all")
    private void setupColumns() {
        dateListed.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getDateListed().toString()));
        listedBy.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getUser().getUsername()));
        asset.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAsset().getAssetName()));
        quantity.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getQuantity() - p.getValue().getQuantityFilled()));
        price.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPrice()));
        tradeType.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getType()));
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }
}
