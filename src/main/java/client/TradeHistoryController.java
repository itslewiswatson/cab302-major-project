package client;

import client.config.Page;
import client.dialog.TradeInfoDialogController;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Trade;
import common.domain.Unit;
import common.dto.GetHistoricTradesDTO;
import common.dto.GetUnitTradesDTO;
import common.dto.GetUnitsDTO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TradeHistoryController extends Controller implements Initializable {
    private Timeline refresher;

    @FXML
    private TableView<Trade> tableView;

    @FXML
    private TableColumn<Trade, String> dateListed;

    @FXML
    private TableColumn<Trade, String> dateFulfilled;

    @FXML
    private TableColumn<Trade, String> asset;

    @FXML
    private TableColumn<Trade, String> quantity;

    @FXML
    private TableColumn<Trade, String> price;

    @FXML
    private TableColumn<Trade, String> tradeType;

    public TradeHistoryController(ClientController clientController) {
        super(clientController);
    }

    private ArrayList<Trade> fetchHistoricTrades() {
        sendObject(new GetHistoricTradesDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int refreshPeriod = 5;

        setupColumns();
        setupRows();
        populateTable();

        refresher = new Timeline(
                new KeyFrame(
                        Duration.seconds(refreshPeriod),
                        event -> {
                            populateTable();
                            displayNotification();
                        }
                )
        );

        refresher.setCycleCount(Timeline.INDEFINITE);
        refresher.play();
    }

    public void populateTable() {
        int previouslySelectedTrade = tableView.getSelectionModel().getSelectedIndex();

        ObservableList<Trade> trades = FXCollections.observableArrayList();

        SortedList<Trade> sortedTrades = new SortedList<>(trades);

        sortedTrades.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedTrades);

        trades.addAll(fetchHistoricTrades());

        tableView.getFocusModel().focus(previouslySelectedTrade);
        tableView.getSelectionModel().select(previouslySelectedTrade);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns() {
        dateListed.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getDateListed().toString()));
        dateFulfilled.setCellValueFactory(p -> new ReadOnlyObjectWrapper(Objects.requireNonNull(p.getValue().getDateFilled()).toString()));
        asset.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAsset().getAssetName()));
        quantity.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getQuantity() - p.getValue().getQuantityFilled()));
        price.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPrice()));
        tradeType.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getType()));
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

    public void goBack() {
        refresher.stop();
        switchToPage(Page.myAccount);
    }

    public void viewTradeInfo(Trade trade) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tradeDialog.fxml"));
            loader.setControllerFactory(c -> new TradeInfoDialogController(trade, false, true));
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

    private void displayNotification() {
        ArrayList<Trade> allUnitTrades = new ArrayList<>();
        ArrayList<Unit> userUnits = fetchUserUnits();

        for (Unit unit: userUnits) {
            ArrayList<Trade> unitTrades = fetchUnitTrades(unit.getUnitId());
            allUnitTrades.addAll(unitTrades);
        }

        ArrayList<Trade> newFulfilledTrades = allUnitTrades.stream()
                .filter(allUnitTrade -> allUnitTrade.getDateFilled() != null)
                .collect(Collectors.toCollection(ArrayList::new));


        if (!previousFulfilledTradesInitialised)
        {
            previousFulfilledTrades = newFulfilledTrades;
            previousFulfilledTradesInitialised = true;
            return;
        }


        if (newFulfilledTrades.size() > previousFulfilledTrades.size())
        {
            Notifications.create()
                    .title("Trade/s Reconciled")
                    .text("Trade/s listed by one of your units has been reconciled.")
                    .showInformation();
        }

        previousFulfilledTrades = newFulfilledTrades;
    }

    private ArrayList<Unit> fetchUserUnits() {
        sendObject(new GetUnitsDTO(getUser().getUserId()));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<Trade> fetchUnitTrades(String unitId) {
        sendObject(new GetUnitTradesDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }
}
