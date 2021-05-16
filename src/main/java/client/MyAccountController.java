package client;

import common.ExistingUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

/**
 * This class is the My Account GUI controller.
 */
public class MyAccountController {

    private ClientController clientController;

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

    void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void changePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        try {
            if (currentPassword.length() > 0 && newPassword.length() > 0) {
                if (BCrypt.checkpw(currentPassword, clientController.getUser().getPassword())) {
                    if (!currentPassword.equals(newPassword)) {
                        ExistingUser newPasswordUser = clientController.getUser();
                        newPasswordUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));

                        clientController.getOutputStream().writeObject(newPasswordUser);
                        clientController.getOutputStream().flush();

                        ExistingUser existingUser = (ExistingUser) clientController.getInputStream().readObject();

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
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        } catch (ClassNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
        }
    }

    public void logout() {
        clientController.switchToLogin();
    }

    public void displayUserDetails() {
        displayUsername(clientController.getUser().getUsername());
        displayAccountType(clientController.getUser().isAdmin());
        displayUnits(clientController.getUser().getUnits());
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
