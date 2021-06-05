package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.UnitAsset;
import common.dto.CreateOrUpdateUnitAssetDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUnitAssetDialogController extends Controller implements Initializable {
    private final CreateOrUpdateUnitAssetDTO dto;
    private final UnitAsset unitAsset;

    @FXML
    private TextField oldQtyTextField;

    @FXML
    private TextField newQtyTextField;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    public EditUnitAssetDialogController(ClientController clientController, UnitAsset unitAsset, CreateOrUpdateUnitAssetDTO dto) {
        super(clientController);
        this.unitAsset = unitAsset;
        this.dto = dto;
    }

    public void clickCreate() {
        String textQuantity = newQtyTextField.getText();
        if (!textQuantity.matches("[0-9]+")) {
            AlertDialog.error("Quantity invalid", "Quantity may only be a numeric value");
            return;
        }

        int quantity = Integer.parseInt(textQuantity);

        if (quantity <= 0) {
            AlertDialog.error("Quantity cannot be less than or equal to zero");
            return;
        }

        dto.setQuantity(quantity);

        clickCancel();
    }

    public void clickCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        oldQtyTextField.setText(String.valueOf(unitAsset.getQuantity()));
    }
}
