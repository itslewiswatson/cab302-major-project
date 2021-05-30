package client;

import client.config.Page;
import common.domain.FullAsset;
import common.domain.Unit;
import common.dto.GetAssetsDTO;
import common.dto.GetUnitsDTO;
import common.dto.UpdateCreditsDTO;
import common.exceptions.NullResultException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUnitComboBox();
        populateUnitComboBox();

        setupAssetComboBox();
        populateAssetComboBox();
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

    @FXML
    private void onSelectUnit() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit == null) {
            disableSaveCredits();
            return;
        }
        ;

        showUnitInfo(selectedUnit);
    }

    private void showUnitInfo(Unit unit) {
        unitIdLabel.setText("Unit ID: " + unit.getUnitId());
        userCountLabel.setText("Users: " + unit.getUsers().size());
        assetsHeldLabel.setText("Assets Held: " + unit.getUnitAssets().size());
        creditsLabel.setText("Credits: " + unit.getCredits());
        enableSaveCredits();
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

    public void goBack() {
        switchToPage(Page.myAccount);
    }

    private void disableSaveCredits() {
        creditsTextField.setDisable(true);
        saveCreditsButton.setDisable(true);
    }

    private void enableSaveCredits() {
        creditsTextField.setDisable(false);
        saveCreditsButton.setDisable(false);
    }
}
