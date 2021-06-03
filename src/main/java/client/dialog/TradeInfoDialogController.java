package client.dialog;

import common.domain.Trade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TradeInfoDialogController implements Initializable {
    private final Trade trade;
    private final Boolean showUserInfo;
    private final Boolean isHistoricTrade;

    public TradeInfoDialogController(Trade trade, Boolean showUserInfo, Boolean isHistoricTrade) {
        this.trade = trade;
        this.showUserInfo = showUserInfo;
        this.isHistoricTrade = isHistoricTrade;
    }

    @FXML
    private Label tradeId;

    @FXML
    private Label asset;

    @FXML
    private Label unitOrUser;

    @FXML
    private Label dateListed;

    @FXML
    private Label tradeType;

    @FXML
    private Label initialQty;

    @FXML
    private Label filledQtyOrDate;

    @FXML
    private Label price;

    @FXML
    private Button okButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tradeId.setText("Trade ID: " + trade.getTradeId());
        asset.setText("Asset: " + trade.getAsset().getAssetName());
        unitOrUser.setText(showUserInfo ?
                "User: " + trade.getUser().getUsername() :
                "Unit: " + trade.getUnit().getUnitName()
        );
        dateListed.setText("Date Listed: " + trade.getDateListed().toString());
        tradeType.setText("Trade Type: " + trade.getType().toString());
        initialQty.setText((isHistoricTrade ?
                "Quantity: ":
                "Initial Quantity: ") + trade.getQuantity());
        filledQtyOrDate.setText(isHistoricTrade ?
                "Date Filled: " + trade.getDateFilled() :
                "Quantity Filled: " + trade.getQuantityFilled());
        price.setText("Price: " + trade.getPrice());
    }

    public void onClickOK() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
