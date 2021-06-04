package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.dialog.TradeInfoDialogController;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Trade;
import common.domain.Unit;
import common.dto.GetUnitTradesDTO;
import common.dto.GetUnitsDTO;
import common.dto.RemoveTradeDTO;
import common.exceptions.NullResultException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UnitTradesController extends Controller implements Initializable {

    Timeline refresher;

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

    @FXML
    private TableColumn<Trade, String> fulfilled;

    @FXML
    private Button removeTradeButton;

    public UnitTradesController(ClientController clientController) {
        super(clientController);
    }

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
        int refreshPeriod = 5;

        setupColumns();
        setupRows();
        setupComboBox();
        populateTable();

        refresher = new Timeline(
                new KeyFrame(
                        Duration.seconds(refreshPeriod),
                        event -> populateTable()
                )
        );

        refresher.setCycleCount(Timeline.INDEFINITE);
        refresher.play();
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

    public void populateUnitComboBox() {
        ArrayList<Unit> userUnits = fetchUserUnits();
        unitComboBox.setItems(FXCollections.observableArrayList(userUnits));
        if (userUnits.size() == 1) {
            unitComboBox.setValue(userUnits.get(0));
        }
    }

    @FXML
    public void populateTable() {
        Unit selectedUnit = unitComboBox.getValue();

        if (selectedUnit != null) {
            Trade selectedTrade;
            int previouslySelectedTrade = tableView.getSelectionModel().getSelectedIndex();

            ObservableList<Trade> trades = FXCollections.observableArrayList();

            SortedList<Trade> sortedTrades = new SortedList<>(trades);
            sortedTrades.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedTrades);
            trades.addAll(fetchUnitTrades(selectedUnit.getUnitId()));

            tableView.getFocusModel().focus(previouslySelectedTrade);
            tableView.getSelectionModel().select(previouslySelectedTrade);

            selectedTrade = tableView.getSelectionModel().getSelectedItem();
            if (selectedTrade != null) removeTradeButton.setDisable(selectedTrade.getDateFilled() != null);
            else removeTradeButton.setDisable(true);
        }
        else {
            removeTradeButton.setDisable(true);
        }
    }

    private void setupRows() {
        tableView.setRowFactory(tv -> {
            TableRow<Trade> row = new TableRow<>();

            row.setOnMouseClicked(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() < 1) return;
                Trade selectedTrade = tableView.getSelectionModel().getSelectedItem();

                if (mouseEvent.getClickCount() != 2) {
                    removeTradeButton.setDisable(selectedTrade == null || selectedTrade.getDateFilled() != null);
                    return;
                }

                viewTradeInfo(selectedTrade);
            });
            return row;
        });
    }

    public void viewTradeInfo(Trade trade) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tradeDialog.fxml"));
            loader.setControllerFactory(c -> new TradeInfoDialogController(trade, true, trade.getDateFilled() != null));
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns() {
        dateListed.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getDateListed().toString()));
        listedBy.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getUser().getUsername()));
        asset.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAsset().getAssetName()));
        quantity.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getQuantity() - p.getValue().getQuantityFilled()));
        price.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPrice()));
        tradeType.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getType()));
        fulfilled.setCellValueFactory(p -> p.getValue().getDateFilled() != null ?
                        new ReadOnlyObjectWrapper("Yes") :
                        new ReadOnlyObjectWrapper("No")
                );
    }

    @FXML
    public void deleteTrade() {
        Trade selectedTrade = tableView.getSelectionModel().getSelectedItem();
        if (selectedTrade == null) {
            AlertDialog.info("You must select a trade to remove", "Click on any item in the list, then navigate and click 'Remove'");
            return;
        }

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
        refresher.stop();
        switchToPage(Page.myAccount);
    }
}
