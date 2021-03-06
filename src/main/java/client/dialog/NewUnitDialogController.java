package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Unit;
import common.dto.NewUnitDTO;
import common.exceptions.NullResultException;
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
    private Button cancelButton;

    public NewUnitDialogController(ClientController clientController, NewUnitDTO dto) {
        super(clientController);
        this.dto = dto;
    }

    public void clickCreate() {
        String unitName = unitNameTextField.getText();
        String textCredits = creditsTextField.getText();

        if (unitName.length() < Unit.NAME_MIN_LENGTH) {
            AlertDialog.warning("Invalid unit name.", "Unit name must be at least " + Unit.NAME_MIN_LENGTH
                    + " characters long.");
            return;
        }

        if (!textCredits.matches("[0-9]+")) {
            AlertDialog.warning("Invalid number of credits.", "Credits must be a whole number which " +
                    "greater than zero.");
            return;
        }

        int credits = Integer.parseInt(textCredits);

        dto.setUnitName(unitName);
        dto.setCredits(credits);

        if (dto.getUnitName() == null || dto.getCredits() == null) {
            AlertDialog.error("Could not create unit.", "Please try again.");
            return;
        }

        sendObject(dto);
        try {
            Unit unit = readObject();
            AlertDialog.info("Successfully created unit " + unit.getUnitName() + " with " + unit.getCredits() + " credits", "You may now add users and assets as you please");
        } catch (NullResultException e) {
            AlertDialog.error("Could not create unit.", "Ensure a unit with the same name doesn't already " +
                    "exist and try again.");
        }

        clickCancel();
    }

    public void clickCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
