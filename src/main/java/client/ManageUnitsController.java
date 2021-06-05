package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.dialog.EditUnitAssetDialogController;
import client.dialog.NewUnitDialogController;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.*;
import common.dto.*;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
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
    private Label unitNameLabel;

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

    private ArrayList<Trade> fetchHistoricTrades() {
        sendObject(new GetHistoricTradesDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
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

    private ArrayList<UnitAsset> fetchUnitAssets(String unitId) {
        sendObject(new GetUnitAssetsDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ArrayList<User> fetchUnitUsers(Unit unit) {
        String unitId = unit.getUnitId();
        sendObject(new GetUnitUsersDTO(unitId));
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

        unitAssetTableView.setRowFactory(tv -> {
            TableRow<UnitAsset> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() != 2) {
                    return;
                }
                @Nullable UnitAsset selectedUnitAsset = tv.getSelectionModel().getSelectedItem();
                if (selectedUnitAsset == null) return;
                viewEditQuantityDialog(selectedUnitAsset);
            });
            return row;
        });
    }

    private ArrayList<UnitAsset> populateUnitAssetTable(Unit unit) {
        ArrayList<UnitAsset> unitAssets = fetchUnitAssets(unit);
        unitAssetTableView.setItems(FXCollections.observableArrayList(unitAssets));
        return unitAssets;
    }

    private void populateUnitAssetTable() {
        Unit unit = unitComboBox.getValue();
        if (unit == null) return;
        ArrayList<UnitAsset> unitAssets = fetchUnitAssets(unit);
        unitAssetTableView.setItems(FXCollections.observableArrayList(unitAssets));
    }

    @FXML
    private void selectUnit() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit == null) {
            disableInputs();
            return;
        }

        showUnitInfo(selectedUnit);
    }

    private void showUnitInfo(Unit unit) {
        addAssetTextField.clear();
        creditsTextField.clear();
        allAssetsComboBox.setValue(null);

        ArrayList<User> unitUsers = fetchUnitUsers(unit);
        userCountLabel.setText("Users: " + unitUsers.size());

        ArrayList<UnitAsset> unitAssets = populateUnitAssetTable(unit);
        assetsHeldLabel.setText("Assets Held: " + unitAssets.size());

        ArrayList<Trade> tradesPending = fetchUnitTrades(unit);
        pendingTrades.setText("Pending Trades: " + tradesPending.size());

        ArrayList<Trade> tradesHistoric = fetchHistoricTrades();
        completedTrades.setText("Completed Trades: " + tradesHistoric.size());

        unitNameLabel.setText("Unit Name: " + unit.getUnitName());
        creditsLabel.setText("Credits: " + unit.getCredits());
        enableInputs();
    }

    @FXML
    private void updateCredits() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit == null) return;

        String textCredits = creditsTextField.getText();
        if (!textCredits.matches("[0-9]+")) {
            AlertDialog.error("Invalid input for credits", "Please enter a numeric value");
            return;
        }
        int credits = Integer.parseInt(textCredits);

        sendObject(new UpdateCreditsDTO(selectedUnit.getUnitId(), credits));
        try {
            Unit result = readObject();
            showUnitInfo(result);
        } catch (NullResultException e) {
            AlertDialog.error("Could not update unit credits", "Please try again");
        } finally {
            creditsTextField.clear();
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
                AlertDialog.warning("Could not remove asset from unit", "Please try again");
                return;
            }

            ArrayList<UnitAsset> unitAssets = fetchUnitAssets(selectedUnitAsset.getUnitId());
            unitAssetTableView.setItems(FXCollections.observableArrayList(unitAssets));
        } catch (NullResultException e) {
            AlertDialog.warning("Could not remove asset from unit", "Please try again");
        }
    }

    @FXML
    private void addAsset() {
        Unit selectedUnit = unitComboBox.getValue();
        if (selectedUnit == null) return;

        String textQty = addAssetTextField.getText();
        if (!textQty.matches("[0-9]+")) {
            AlertDialog.error("Invalid input for quantity", "Please enter a numeric value");
            return;
        }
        int qty = Integer.parseInt(textQty);

        Asset asset = allAssetsComboBox.getValue();
        if (asset == null) return;

        ObservableList<UnitAsset> currentUnitAssets = unitAssetTableView.getItems();
        FilteredList<UnitAsset> filteredUnitAssets = currentUnitAssets.filtered(unitAsset -> unitAsset.getAsset().getAssetId().equals(asset.getAssetId()));
        if (filteredUnitAssets.size() == 1) {
            AlertDialog.warning("This asset is already owned by this unit", "Please edit the existing value in the table");
            return;
        }

        sendObject(new CreateOrUpdateUnitAssetDTO(selectedUnit.getUnitId(), asset.getAssetId(), qty));
        try {
            readObject();
        } catch (NullResultException e) {
            e.printStackTrace();
        } finally {
            addAssetTextField.clear();
            allAssetsComboBox.setValue(null);
        }

        populateUnitAssetTable(selectedUnit);
    }

    public void viewNewUnitDialog() {
        NewUnitDTO dto = new NewUnitDTO(null, null);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newUnitDialog.fxml"));
            loader.setControllerFactory(c -> new NewUnitDialogController(super.clientController, dto));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.centerOnScreen();
            dialog.initOwner(getStage());
            dialog.setScene(scene);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
        } catch (IOException e) {
            AlertDialog.fileError();
        }

        populateUnitComboBox();
    }

    public void viewEditQuantityDialog(UnitAsset unitAsset) {
        CreateOrUpdateUnitAssetDTO dto = new CreateOrUpdateUnitAssetDTO(
                unitAsset.getUnitId(),
                unitAsset.getAsset().getAssetId(),
                null
        );

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newUnitDialog.fxml"));
            loader.setControllerFactory(c -> new EditUnitAssetDialogController(super.clientController, dto));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.centerOnScreen();
            dialog.initOwner(getStage());
            dialog.setScene(scene);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
        } catch (IOException e) {
            AlertDialog.fileError();
        }

        if (dto.getQuantity() == null) {
            AlertDialog.error("Could not edit unit asset", "Please try again");
            return;
        }

        sendObject(dto);
        try {
            readObject();
            AlertDialog.info("Successfully updated quantity of " + unitAsset.getAsset().getAssetName() + " to " + dto.getQuantity());
        } catch (NullResultException e) {
            AlertDialog.error("Could not edit unit asset", "Please try again");
        }

        populateUnitAssetTable();
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
