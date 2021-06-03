package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitsDTO;
import common.dto.UpdatePasswordDTO;
import common.exceptions.NullResultException;
import common.services.PasswordHasher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is the My Account GUI controller.
 */
public class MyAccountController extends Controller implements Initializable {

    public MyAccountController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private AnchorPane anchorPane;

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

    @FXML
    private Button manageAssetsButton;

    @FXML
    private Button manageUnitsButton;

    @FXML
    private Button manageUsersButton;

    public void changePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        try {
            if (currentPassword.length() <= 0 || newPassword.length() <= 0) {
                AlertDialog.info("Current password and new password cannot be empty", "Please try again");
                return;
            }

            if (!PasswordHasher.checkPassword(currentPassword, getUser().getPassword())) {
                AlertDialog.info("Current password is incorrect", "Please try again");
                return;
            }

            if (currentPassword.equals(newPassword)) {
                AlertDialog.info("Current password and new password are the same", "Please try again");
                return;
            }

            User currentUser = getUser();
            String newPasswordHashed = PasswordHasher.hashPassword(newPassword);

            UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO(
                    currentUser.getUserId(),
                    newPasswordHashed
            );

            sendObject(updatePasswordDTO);
            User existingUser = readObject();

            currentUser.setPassword(existingUser.getPassword());

            if (PasswordHasher.checkPassword(newPassword, existingUser.getPassword())) {
                AlertDialog.info("Password changed successfully");
            } else {
                AlertDialog.error("Password could not be updated", "Please contact an IT administrator");
            }
        } catch (Exception e) {
            AlertDialog.serverCommunication();
        }
    }

    public void logout() {
        switchToPage(Page.login);
    }

    public void allTrades() {
        switchToPage(Page.allTrades);
    }

    public void tradeHistory() {
        switchToPage(Page.tradeHistory);
    }

    public void assets() {
        switchToPage(Page.assets);
    }

    public void unitTrades() {
        switchToPage(Page.unitTrades);
    }

    public void manageUnits() {
        switchToPage(Page.manageUnits);
    }

    public void manageUsers() {
        switchToPage(Page.manageUsers);
    }

    private ArrayList<Unit> fetchUserUnits(String userId) {
        try {
            sendObject(new GetUnitsDTO(userId));
            return readObject();
        } catch (NullResultException exception) {
            return new ArrayList<>();
        }
    }

    public void displayUserDetails(User user) {
        try {
            ArrayList<Unit> units = fetchUserUnits(user.getUserId());
            String[] unitNames = units.stream().map(Unit::getUnitName).toArray(String[]::new);

            displayUsername(user.getUsername());
            displayAccountType(user.isAdmin());
            displayUnits(unitNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayUsername(String username) {
        usernameLabel.setText("Username: " + username);
    }

    private void displayAccountType(boolean isAdmin) {
        accountTypeLabel.setText("Account Type: " + (isAdmin ? "Administrator" : "Standard"));
    }

    private void displayUnits(String[] units) {
        if (units.length > 0) {
            ObservableList<String> unitsList = FXCollections.observableArrayList(units);
            unitsComboBox.setItems(unitsList);
        } else {
            unitsComboBox.setValue("None");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = getUser();
        displayUserDetails(user);
        manageAssetsButton.setDisable(!user.isAdmin());
        manageUnitsButton.setDisable(!user.isAdmin());
        manageUsersButton.setDisable(!user.isAdmin());
    }
}
