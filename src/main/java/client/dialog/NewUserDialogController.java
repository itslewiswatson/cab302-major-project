package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.User;
import common.dto.NewUserDTO;
import common.exceptions.NullResultException;
import common.services.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserDialogController extends Controller {

    public NewUserDialogController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox isAdminCheckbox;

    @FXML
    private Button cancelButton;

    public void clickCreate() {
        String username = usernameTextField.getText();
        String plainTextPassword = passwordTextField.getText();
        String hashedPassword = PasswordHasher.hashPassword(plainTextPassword);
        boolean isAdmin = isAdminCheckbox.isSelected();

        if (username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
            AlertDialog.warning("Invalid username.", "Username must be at least "
                    + User.USERNAME_MIN_LENGTH + " characters long and no more than " + User.USERNAME_MAX_LENGTH
                    + " characters long.");
            return;
        }

        if (plainTextPassword.length() < User.PASSWORD_MIN_LENGTH) {
            AlertDialog.warning("Invalid password.", "Password must be at least "
                    + User.PASSWORD_MIN_LENGTH + " characters long.");
            return;
        }

        NewUserDTO dto = new NewUserDTO(username, hashedPassword, isAdmin);

        sendObject(dto);
        try {
            User user = readObject();
            AlertDialog.info("Successfully created user " + user.getUsername() + ".", "They can now log " +
                    "in using the password you created for them.");
        } catch (NullResultException e) {
            AlertDialog.error("Could not create user.", "Ensure a user with same username doesn't already" +
                    " exist and try again.");
        }

        clickCancel();
    }

    public void clickCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
