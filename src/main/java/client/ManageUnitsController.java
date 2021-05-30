package client;

import client.config.Page;
import common.domain.Unit;
import common.dto.GetUnitsDTO;
import common.exceptions.NullResultException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupComboBox();
        populateUnitComboBox();
    }

    private ArrayList<Unit> fetchUnits() {
        sendObject(new GetUnitsDTO(null));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private void setupComboBox() {
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
