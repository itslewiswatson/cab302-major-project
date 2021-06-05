package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Unit;
import common.dto.NewUnitDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUnitDialogController extends Controller {
    private final NewUnitDTO dto;

    @FXML
    private TextField unitNameTextField;

    @FXML
    private TextField creditsTextField;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    public NewUnitDialogController(ClientController clientController, NewUnitDTO dto) {
        super(clientController);
        this.dto = dto;
    }

    public void clickCreate() {
        String unitName = unitNameTextField.getText();
        String textCredits = creditsTextField.getText();

        if (unitName.length() < Unit.NAME_MIN_LENGTH) {
            AlertDialog.warning("Unit name invalid", "Unit name must be at least " + Unit.NAME_MIN_LENGTH + " characters");
            return;
        }

        if (!textCredits.matches("[0-9]+")) {
            AlertDialog.warning("Credits invalid", "Credits may only be a whole number zero or greater");
            return;
        }

        int credits = Integer.parseInt(textCredits);

        dto.setUnitName(unitName);
        dto.setCredits(credits);

        clickCancel();
    }

    public void clickCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
