package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.dialog.TradeInfoDialogController;
import common.domain.Trade;
import common.domain.Unit;
import common.dto.GetUnitTradesDTO;
import common.dto.GetUnitsDTO;
import common.dto.RemoveTradeDTO;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
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
            AlertDialog.info("You are not part of any unit", "Contact an IT admin to be placed in a unit.");
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
        tableView.setRowFactory(tv -> {
            TableRow<Trade> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() != 2) return;
                Trade selectedTrade = tableView.getSelectionModel().getSelectedItem();
                if (selectedTrade == null) return;
                viewTradeInfo(selectedTrade);
            });
            return row;
        });
    }

    public void viewTradeInfo(Trade trade) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tradeDialog.fxml"));
            loader.setControllerFactory(c -> new TradeInfoDialogController(trade, true));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.centerOnScreen();
            dialog.initOwner(getStage());
            dialog.setScene(scene);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void deleteTrade() {
        Trade selectedTrade = tableView.getSelectionModel().getSelectedItem();
        if (selectedTrade == null) {
            AlertDialog.info("You must select a trade to remove", "Click on any item in the list, then navigate and click 'Remove'");
            return;
        };

        sendObject(new RemoveTradeDTO(selectedTrade.getTradeId()));
        try {
            Boolean success = readObject();
            if (!success) {
                AlertDialog.warning("Could not remove the selected trade", "Please try again");
                return;
            }
            ObservableList<Trade> visibleTrades = tableView.getItems().filtered(trade -> !trade.getTradeId().equals(selectedTrade.getTradeId()));
            tableView.setItems(visibleTrades);
        } catch (NullResultException e) {
            AlertDialog.warning("Could not remove the selected trade", "Please try again");
        }
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }
}
