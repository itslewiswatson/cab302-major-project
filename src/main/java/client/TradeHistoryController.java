package client;

import client.config.Page;
import client.dialog.TradeInfoDialogController;
import common.domain.Trade;
import common.dto.GetHistoricTradesDTO;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TradeHistoryController extends TableController implements Initializable {
    public TradeHistoryController(ClientController clientController) {
        super(clientController);
    }

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

    private ArrayList<Trade> fetchHistoricTrades() {
        sendObject(new GetHistoricTradesDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            //AlertDialog.info("There are no historic trades at the moment", "No trades have been fulfilled. Check back later.");
            return new ArrayList<>();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        setupRows();
        populateTable();

        Thread refreshThread = new TableRefreshScheduler<>(this);
        refreshThread.start();
    }

    public void populateTable() {
        ArrayList<Trade> trades = fetchHistoricTrades();
        tableView.setItems(FXCollections.observableArrayList(trades));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns() {
        dateListed.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getDateListed().toString()));
        dateFulfilled.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getDateFilled().toString()));
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
}
