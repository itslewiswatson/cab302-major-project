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

    public TradeInfoDialogController(Trade trade, Boolean showUserInfo) {
        this.trade = trade;
        this.showUserInfo = showUserInfo;
    }

    @FXML
    private Label tradeId;

    @FXML
    private Label asset;

    @FXML
    private Label unitId;

    @FXML
    private Label dateListed;

    @FXML
    private Label tradeType;

    @FXML
    private Label initialQty;

    @FXML
    private Label filledQty;

    @FXML
    private Label price;

    @FXML
    private Button okButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tradeId.setText("Trade ID: " + trade.getTradeId());
        asset.setText("Asset: " + trade.getAsset().getAssetName());
        unitId.setText(showUserInfo ?
                "User: " + trade.getUser().getUsername() :
                "Unit ID: " + trade.getUnit().getUnitId()
        );
        dateListed.setText("Date Listed: " + trade.getDateListed().toString());
        tradeType.setText("Trade Type: " + trade.getType().toString());
        initialQty.setText("Initial Quantity: " + trade.getQuantity());
        filledQty.setText("Quantity Filled: " + trade.getQuantityFilled());
        price.setText("Price: " + trade.getPrice());
    }

    public void onClickOK() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
