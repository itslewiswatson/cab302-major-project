package client;

import common.domain.Trade;
import common.dto.GetTradesDTO;
import common.exceptions.NullResultException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TradesController extends Controller implements Initializable {
    public TradesController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private ListView<String> tradesList;

    private ArrayList<Trade> getActiveTrades() {
        sendObject(new GetTradesDTO());

        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Trade> trades = getActiveTrades();
        for (Trade trade : trades) {
            tradesList.getItems().add(trade.getTradeId());
        }
    }
}
