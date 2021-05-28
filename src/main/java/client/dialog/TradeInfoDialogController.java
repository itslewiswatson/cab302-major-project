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

    public TradeInfoDialogController(Trade trade) {
        this.trade = trade;
    }

    @FXML
    private Label tradeId;

    @FXML
    private Label assetId;

    @FXML
    private Label userId;

    @FXML
    private Button okButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tradeId.setText("Trade ID: " + trade.getTradeId());
        assetId.setText("Asset ID: " + trade.getAsset().getAssetId());
        userId.setText("User ID: " + trade.getUserId());
    }

    public void onClickOK() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
