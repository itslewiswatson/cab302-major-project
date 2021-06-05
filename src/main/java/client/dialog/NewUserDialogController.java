package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.User;
import common.dto.NewUserDTO;
import common.services.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserDialogController extends Controller {
    private final NewUserDTO dto;

    public NewUserDialogController(ClientController clientController, NewUserDTO dto) {
        super(clientController);
        this.dto = dto;
    }

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox isAdminCheckbox;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    public void clickCreate() {
        String username = usernameTextField.getText();
        String unhashedPassword = passwordTextField.getText();
        String hashedPassword = PasswordHasher.hashPassword(unhashedPassword);
        boolean isAdmin = isAdminCheckbox.isSelected();

        if (username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
            AlertDialog.warning("Username invalid", "Username must be at least " + User.USERNAME_MIN_LENGTH + " characters and no more than " + User.USERNAME_MAX_LENGTH + " characters");
            return;
        }

        if (unhashedPassword.length() < User.PASSWORD_MIN_LENGTH) {
            AlertDialog.warning("Password invalid", "Password must be at least " + User.PASSWORD_MIN_LENGTH + " characters");
            return;
        }

        dto.setUsername(username);
        dto.setPassword(hashedPassword);
        dto.setAdmin(isAdmin);

        clickCancel();
    }

    public void clickCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
