package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.dataTypes.TradeType;
import common.domain.Asset;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.GetAssetsDTO;
import common.dto.GetUnitAssetsDTO;
import common.dto.GetUnitsDTO;
import common.dto.NewTradeDTO;
import common.exceptions.NullResultException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
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
        unitComboBox.setItems(FXCollections.observableArrayList(userUnits));
        if (userUnits.size() == 1) {
            unitComboBox.setValue(userUnits.get(0));
        }
    }

    public void populateTradeTypeComboBox() {
        assetComboBox.setValue(null);
        creditsQuantityLabel.setText("");
        quantityTextField.setText("");
        priceTextField.setText("");

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
        }
        else if (selectedTradeType == TradeType.SELL)
        {
            ArrayList<UnitAsset> unitAssets = fetchUnitAssets();
            assets = unitAssets.stream()
                    .map(UnitAsset::getAsset).collect(Collectors.toCollection(ArrayList::new));
        }
        else {
            AlertDialog.info("Trade type not selected.", "Please select a trade type before selecting an asset.");
            return;
        }

        assetComboBox.setItems(FXCollections.observableArrayList(assets));
        if (assets.size() == 1) {
            assetComboBox.setValue(assets.get(0));
        }
    }

    public void setCreditsQuantityLabel() {
        if (tradeTypeComboBox.getValue() == TradeType.BUY) {
            creditsQuantityLabel.setText("Available Credits: " + unitComboBox.getValue().getCredits());
        }
        else {
            creditsQuantityLabel.setText("Available Quantity: " + fetchUnitAssetQuantity());
        }
    }

    public void listTrade() {
        Stage stage = (Stage) listButton.getScene().getWindow();

        String quantityString = quantityTextField.getText();
        String priceString = priceTextField.getText();

        if (textFieldsValid(quantityString, priceString))
        {
            sendObject(new NewTradeDTO(
                    assetComboBox.getValue().getAssetId(),
                    unitComboBox.getValue().getUnitId(),
                    getUser().getUserId(),
                    tradeTypeComboBox.getValue(),
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
                if (quantity > fetchUnitAssetQuantity())
                {
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

                if (quantity * price > unitComboBox.getValue().getCredits())
                {
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
}
