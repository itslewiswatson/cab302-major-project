package client;

import client.config.Page;
import common.domain.FullAsset;
import common.domain.Trade;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.*;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUnitsController extends Controller implements Initializable {
    public ManageUnitsController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private TextField creditsTextField;

    @FXML
    private Button saveCreditsButton;

    @FXML
    private ComboBox<Unit> unitComboBox;

    @FXML
    private ComboBox<FullAsset> allAssetsComboBox;

    @FXML
    private Label unitIdLabel;

    @FXML
    private Label userCountLabel;

    @FXML
    private Label assetsHeldLabel;

    @FXML
    private Label creditsLabel;

    @FXML
    private Label pendingTrades;

    @FXML
    private Label completedTrades;

    @FXML
    private Button addAssetButton;

    @FXML
    private TextField addAssetTextField;

    @FXML
    private Button removeAssetButton;

    @FXML
    private TableView<UnitAsset> unitAssetTableView;

    @FXML
    private TableColumn<UnitAsset, String> assetNameTableCol;

    @FXML
    private TableColumn<UnitAsset, Integer> quantityTableCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUnitComboBox();
        populateUnitComboBox();

        setupAssetComboBox();
        populateAssetComboBox();

        setupUnitAssetTable();
    }

    private ArrayList<Unit> fetchUnits() {
        sendObject(new GetUnitsDTO(null));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<FullAsset> fetchAllAssets() {
        sendObject(new GetAssetsDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<Trade> fetchUnitTrades(Unit unit) {
        String unitId = unit.getUnitId();
        sendObject(new GetUnitTradesDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<UnitAsset> fetchUnitAssets(Unit unit) {
        String unitId = unit.getUnitId();
        sendObject(new GetUnitAssetsDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void populateAssetComboBox() {
        ArrayList<FullAsset> assets = fetchAllAssets();
        allAssetsComboBox.setItems(FXCollections.observableArrayList(assets));
    }

    private void setupAssetComboBox() {
        allAssetsComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(FullAsset asset) {
                return asset != null ? asset.getAssetName() : "";
            }

            @Override
            public FullAsset fromString(String s) {
                return null;
            }
        });
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

    private void setupUnitAssetTable() {
        assetNameTableCol.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().getAsset().getAssetName()));
        quantityTableCol.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().getQuantity()));
    }

    private void populateUnitAssetTable(Unit unit) {
        ArrayList<UnitAsset> unitAssets = fetchUnitAssets(unit);
        unitAssetTableView.setItems(FXCollections.observableArrayList(unitAssets));
    }

    @FXML
    private void onSelectUnit() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit == null) {
            disableInputs();
            return;
        }

        showUnitInfo(selectedUnit);
    }

    private void showUnitInfo(Unit unit) {
        populateUnitAssetTable(unit);

        ArrayList<Trade> trades = fetchUnitTrades(unit);
        pendingTrades.setText("Pending Trades: " + trades.size());

        unitIdLabel.setText("Unit ID: " + unit.getUnitId());
        userCountLabel.setText("Users: " + unit.getUsers().size());
        assetsHeldLabel.setText("Assets Held: " + unit.getUnitAssets().size());
        creditsLabel.setText("Credits: " + unit.getCredits());
        enableInputs();
    }

    @FXML
    private void updateCredits() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit == null) return;

        String textCredits = creditsTextField.getText();
        if (!textCredits.matches("[0-9]+")) {
            System.out.println("Invalid credits '" + textCredits + "'");
            return;
        }
        int credits = Integer.parseInt(textCredits);

        sendObject(new UpdateCreditsDTO(selectedUnit.getUnitId(), credits));
        try {
            Unit result = readObject();
            showUnitInfo(result);
        } catch (NullResultException e) {
            // TODO show alert here
        }
    }

    private void populateUnitComboBox() {
        ArrayList<Unit> units = fetchUnits();
        unitComboBox.setItems(FXCollections.observableArrayList(units));
    }

    @FXML
    private void removeAsset() {
        UnitAsset selectedUnitAsset = unitAssetTableView.getSelectionModel().getSelectedItem();
        if (selectedUnitAsset == null) return;

        RemoveUnitAssetDTO dto = new RemoveUnitAssetDTO(selectedUnitAsset.getUnitId(), selectedUnitAsset.getAsset().getAssetId());
        sendObject(dto);

        try {
            Boolean success = readObject();
            if (!success) {
                // TODO show alert here
                return;
            }
            ObservableList<UnitAsset> visibleUnitAssets = unitAssetTableView.getItems().filtered(unitAsset -> !unitAsset.getAsset().getAssetId().equals(selectedUnitAsset.getAsset().getAssetId()) && !unitAsset.getUnitId().equals(selectedUnitAsset.getUnitId()));
            unitAssetTableView.setItems(visibleUnitAssets);
        } catch (NullResultException e) {
            // TODO show alert here
        }
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }

    private void disableInputs() {
        creditsTextField.setDisable(true);
        saveCreditsButton.setDisable(true);
        addAssetButton.setDisable(true);
        removeAssetButton.setDisable(true);
        addAssetTextField.setDisable(true);
        allAssetsComboBox.setDisable(true);
    }

    private void enableInputs() {
        creditsTextField.setDisable(false);
        saveCreditsButton.setDisable(false);
        addAssetButton.setDisable(false);
        removeAssetButton.setDisable(false);
        addAssetTextField.setDisable(false);
        allAssetsComboBox.setDisable(false);
    }
}
