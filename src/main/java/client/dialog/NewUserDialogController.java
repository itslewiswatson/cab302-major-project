package client.dialog;

import common.dto.NewUserDTO;
import common.services.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserDialogController {
    private final NewUserDTO dto;

    public NewUserDialogController(NewUserDTO dto) {
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
        dto.setUsername(usernameTextField.getText());
        dto.setPassword(PasswordHasher.hashPassword(passwordTextField.getText()));
        dto.setAdmin(isAdminCheckbox.isSelected());

        clickCancel();
    }

    public void clickCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
