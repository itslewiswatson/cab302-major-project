package client;

import common.domain.User;
import common.dto.UpdatePasswordDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class is the My Account GUI controller.
 */
public class MyAccountController extends Controller {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private ComboBox<String> unitsComboBox;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    public void changePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        try {
            if (currentPassword.length() > 0 && newPassword.length() > 0) {
                if (BCrypt.checkpw(currentPassword, getUser().getPassword())) {
                    if (!currentPassword.equals(newPassword)) {
                        User newPasswordUser = getUser();
                        String newPasswordHashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO(
                                newPasswordUser.getUserId(),
                                newPasswordUser.getPassword(),
                                newPasswordHashed
                        );

                        sendObject(updatePasswordDTO);
                        User existingUser = (User) readObject();

                        newPasswordUser.setPassword(existingUser.getPassword());

                        if (BCrypt.checkpw(newPassword, existingUser.getPassword())) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                            alert.setHeaderText("Password changed successfully.");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Contact administrator.", ButtonType.OK);
                            alert.setHeaderText("Password could not be updated.");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                        alert.setHeaderText("New Password and Current Password are the same.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                    alert.setHeaderText("Current Password is incorrect.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                alert.setHeaderText("Current Password and New Password cannot be empty.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        }
    }

    public void logout() {
        switchToPage(Page.login);
    }

    public void displayUserDetails() {
        displayUsername(getUser().getUsername());
        displayAccountType(getUser().isAdmin());
        displayUnits((String[]) getUser().getUnits().toArray());
    }

    private void displayUsername(String username) {
        usernameLabel.setText("Username: " + username);
    }

    private void displayAccountType(boolean isAdmin) {
        if (isAdmin) {
            accountTypeLabel.setText("Account Type: Administrator");
        } else {
            accountTypeLabel.setText("Account Type: Standard");
        }
    }

    private void displayUnits(String[] units) {
        if (units.length > 0) {
            ObservableList<String> unitsList = FXCollections.observableArrayList(units);
            unitsComboBox.setItems(unitsList);
        } else {
            unitsComboBox.setValue("None");
        }
    }
}
