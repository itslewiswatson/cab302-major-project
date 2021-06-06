package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.dataTypes.TradeType;
import common.domain.Asset;
import common.domain.Trade;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.*;
import common.exceptions.NullResultException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewTradeDialogController extends Controller implements Initializable {

    @FXML
    private ComboBox<Unit> unitComboBox;

    @FXML
    private ComboBox<TradeType> tradeTypeComboBox;

    @FXML
    private ComboBox<Asset> assetComboBox;

    @FXML
    private Label creditsQuantityLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button listButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label headerLabel;

    @FXML
    private LineChart<Integer, Integer> historyLineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public NewTradeDialogController(ClientController clientController) {
        super(clientController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUnitComboBox();
        setupTradeTypeComboBox();
        setupAssetComboBox();
    }

    private void setupUnitComboBox() {
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

    private void setupTradeTypeComboBox() {
        tradeTypeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TradeType tradeType) {
                return tradeType.toString();
            }

            @Override
            public TradeType fromString(String s) {
                return null;
            }
        });
    }

    private void setupAssetComboBox() {
        assetComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Asset asset) {
                return asset != null ? asset.getAssetName() : "";
            }

            @Override
            public Asset fromString(String s) {
                return null;
            }
        });
    }

    public void populateUnitComboBox() {
        tradeTypeComboBox.setValue(null);
        assetComboBox.setValue(null);
        creditsQuantityLabel.setText("");
        quantityTextField.setText("");
        priceTextField.setText("");

        ArrayList<Unit> userUnits = fetchUserUnits();

        if (userUnits.isEmpty()) {
            AlertDialog.info("You currently don't belong to any units.",
                    "Contact an IT admin to be added to one.");
            return;
        }

        unitComboBox.setItems(FXCollections.observableArrayList(userUnits));
        if (userUnits.size() == 1) {
            unitComboBox.setValue(userUnits.get(0));
        }
    }

    public void populateTradeTypeComboBox() {
        if (unitComboBox.getValue() == null) {
            AlertDialog.info("Unit not selected.", "Please select a unit before selecting a trade type.");
            return;
        }

        assetComboBox.setValue(null);
        creditsQuantityLabel.setText("");
        quantityTextField.setText("");
        priceTextField.setText("");

        Unit unit = fetchUnit(unitComboBox.getValue().getUnitId());

        if (unit == null) {
            AlertDialog.serverCommunication();
            return;
        }

        if (unit.getCredits() == 0 && fetchUnitAssets().isEmpty()) {
            AlertDialog.info("Unit " + unit.getUnitName() + " does not have any credits or assets.",
                    "Please select another unit.");
            return;
        }

        if (unit.getCredits() == 0) {
            tradeTypeComboBox.setValue(TradeType.SELL);
            return;
        }

        if (fetchUnitAssets().isEmpty()) {
            tradeTypeComboBox.setValue(TradeType.BUY);
            return;
        }

        tradeTypeComboBox.setItems(FXCollections.observableArrayList(TradeType.values()));
    }

    public void populateAssetComboBox() {
        creditsQuantityLabel.setText("");
        quantityTextField.setText("");
        priceTextField.setText("");

        ArrayList<Asset> assets;
        TradeType selectedTradeType = tradeTypeComboBox.getValue();

        if (selectedTradeType == TradeType.BUY) {
            assets = fetchAssets();
        } else if (selectedTradeType == TradeType.SELL) {
            ArrayList<UnitAsset> unitAssets = fetchUnitAssets();
            assets = unitAssets.stream()
                    .map(UnitAsset::getAsset).collect(Collectors.toCollection(ArrayList::new));
        } else {
            AlertDialog.info("Trade type not selected.", "Please select a trade type before selecting an asset.");
            return;
        }

        assetComboBox.setItems(FXCollections.observableArrayList(assets));
        if (assets.size() == 1) {
            assetComboBox.setValue(assets.get(0));
        }
    }

    public void updateDisplays() {
        if (tradeTypeComboBox.getValue() == TradeType.BUY) {
            creditsQuantityLabel.setText("Available Credits: " + unitComboBox.getValue().getCredits());
        } else {
            creditsQuantityLabel.setText("Available Quantity: " + fetchUnitAssetQuantity());
        }

        Asset selectedAsset = assetComboBox.getValue();
        if (selectedAsset != null) {
            headerLabel.setText("Viewing price history for " + selectedAsset.getAssetName());
        } else {
            headerLabel.setText("Select an asset to view its price history");
        }
        priceHistory(selectedAsset);
    }

    private void priceHistory(@Nullable Asset asset) {
        if (asset == null) {
            historyLineChart.setUserData(null);
            return;
        }

        historyLineChart.getData().clear();

        ArrayList<Trade> historicTradesByAsset = fetchHistoricTradesByAsset(asset);
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();

        for (Trade trade : historicTradesByAsset) {
            long dateListedEpoch = trade.getDateListed().toEpochSecond(LocalTime.NOON, ZoneOffset.MIN);
            series.getData().add(new XYChart.Data(Math.toIntExact(dateListedEpoch), trade.getPrice()));
        }

        historyLineChart.getData().add(series);
    }

    public void listTrade() {
        Stage stage = (Stage) listButton.getScene().getWindow();

        Unit unit = unitComboBox.getValue();
        TradeType tradeType = tradeTypeComboBox.getValue();
        Asset asset = assetComboBox.getValue();
        String quantityString = quantityTextField.getText();
        String priceString = priceTextField.getText();

        if (unit == null || tradeType == null || asset == null) {
            AlertDialog.info("Incomplete new trade form.", "Please enter any empty fields and try again.");
            return;
        }

        if (textFieldsValid(quantityString, priceString)) {
            sendObject(new NewTradeDTO(
                    asset.getAssetId(),
                    unit.getUnitId(),
                    getUser().getUserId(),
                    tradeType,
                    Integer.parseInt(quantityString),
                    Integer.parseInt(priceString)
            ));

            try {
                if (readObject() == null) throw new NullResultException();

                AlertDialog.info("Trade listed successfully!", "");
                stage.close();
            } catch (NullResultException e) {
                AlertDialog.info("Could not list trade!", "Please try again.");

                unitComboBox.setValue(null);
                tradeTypeComboBox.setValue(null);
                assetComboBox.setValue(null);
                creditsQuantityLabel.setText("");
                quantityTextField.setText("");
                priceTextField.setText("");
            }
        }
    }

    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean textFieldsValid(String quantityString, String priceString) {
        int quantity;

        try {
            quantity = Integer.parseInt(quantityString);

            if (quantity < 0) {
                AlertDialog.info("Invalid quantity!", "Please enter a quantity greater than zero.");
                return false;
            }

            if (tradeTypeComboBox.getValue() == TradeType.SELL) {
                if (quantity > fetchUnitAssetQuantity()) {
                    AlertDialog.info("Unit " + unitComboBox.getValue().getUnitName()
                            + " has insufficient quantity of asset " + assetComboBox.getValue().getAssetName()
                            + " to list trade!", "Please enter a quantity less than "
                            + fetchUnitAssetQuantity() + ".");
                    return false;
                }
            }
        } catch (NumberFormatException exception) {
            AlertDialog.info("Invalid quantity!", "Please enter a number greater than 0 but less " +
                    "than 2,147,483,647.");
            return false;
        }

        try {
            int price = Integer.parseInt(priceString);

            if (price < 0) {
                AlertDialog.info("Invalid price!", "Please enter a price greater than zero.");
                return false;
            }

            if (tradeTypeComboBox.getValue() == TradeType.BUY) {
                if (unitComboBox.getValue().getCredits() == 0) {
                    AlertDialog.info("Unit " + unitComboBox.getValue().getUnitName()
                            + " has insufficient credits to list trade!", "Please try again when unit "
                            + unitComboBox.getValue().getUnitName() + " has sufficient credits.");
                    return false;
                }

                if (quantity * price > unitComboBox.getValue().getCredits()) {
                    AlertDialog.info("Unit " + unitComboBox.getValue().getUnitName()
                            + " has insufficient credits to list trade!", "Please enter a quantity and price"
                            + " combination that results in a total price less than "
                            + unitComboBox.getValue().getCredits() + ".");
                    return false;
                }
            }

            return true;
        } catch (NumberFormatException exception) {
            AlertDialog.info("Invalid price!", "Please enter a number greater than 0 but less " +
                    "than 2,147,483,647.");
            return false;
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

    private Unit fetchUnit(String unitId) {
        ArrayList<Unit> units = fetchUserUnits();

        return units.stream()
                .filter(u -> u.getUnitId().equals(unitId))
                .findFirst()
                .orElse(null);
    }

    private ArrayList<Asset> fetchAssets() {
        sendObject(new GetAssetsDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            AlertDialog.info("There are no assets.", "Contact an IT admin to list some assets.");
            return new ArrayList<>();
        }
    }

    private ArrayList<UnitAsset> fetchUnitAssets() {
        sendObject(new GetUnitAssetsDTO(unitComboBox.getValue().getUnitId()));
        try {
            return readObject();
        } catch (NullResultException e) {
            AlertDialog.info("Unit " + unitComboBox.getValue().getUnitName()
                    + " currently has no assets.", "Try buying an asset or contacting an IT admin to add some.");
            return new ArrayList<>();
        }
    }

    private int fetchUnitAssetQuantity() {
        if (assetComboBox.getValue() == null) return 0;

        sendObject(new GetUnitAssetsDTO(unitComboBox.getValue().getUnitId()));

        try {
            ArrayList<UnitAsset> unitAssets = readObject();

            UnitAsset asset = unitAssets.stream()
                    .filter(unitAsset -> unitAsset.getAsset().getAssetId().equals(assetComboBox.getValue().getAssetId()))
                    .findFirst()
                    .orElse(null);

            if (asset == null) throw new NullResultException();

            return asset.getQuantity();
        } catch (NullResultException e) {
            AlertDialog.info("Unit " + unitComboBox.getValue().getUnitName()
                            + " no longer has any of asset " + assetComboBox.getValue().getAssetName() + ".",
                    "Try buying some or contacting an IT admin to add some.");
            return 0;
        }
    }

    private ArrayList<Trade> fetchHistoricTradesByAsset(Asset asset) {
        sendObject(new GetHistoricTradesDTO());
        try {
            ArrayList<Trade> historicTrades = readObject();
            return historicTrades.stream()
                    .filter(trade -> trade.getAsset().getAssetId().equals(asset.getAssetId()))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }
}
