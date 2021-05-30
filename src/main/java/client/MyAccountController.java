package client;

import client.config.Page;
import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitsDTO;
import common.dto.UpdatePasswordDTO;
import common.exceptions.NullResultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.mindrot.jbcrypt.BCrypt;

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
    private Button assetsButton;

    public void changePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        try {
            if (currentPassword.length() <= 0 || newPassword.length() <= 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                alert.setHeaderText("Current Password and New Password cannot be empty.");
                alert.showAndWait();
                return;
            }

            if (!BCrypt.checkpw(currentPassword, getUser().getPassword())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                alert.setHeaderText("Current Password is incorrect.");
                alert.showAndWait();
                return;
            }

            if (currentPassword.equals(newPassword)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                alert.setHeaderText("New Password and Current Password are the same.");
                alert.showAndWait();
                return;
            }

            User currentUser = getUser();
            String newPasswordHashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO(
                    currentUser.getUserId(),
                    currentUser.getPassword(),
                    newPasswordHashed
            );

            sendObject(updatePasswordDTO);
            User existingUser = readObject();

            currentUser.setPassword(existingUser.getPassword());

            if (BCrypt.checkpw(newPassword, existingUser.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setHeaderText("Password changed successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Contact administrator.", ButtonType.OK);
                alert.setHeaderText("Password could not be updated.");
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

    public void allTrades() {
        switchToPage(Page.allTrades);
    }

    public void assets() {
        switchToPage(Page.assets);
    }

    public void unitTrades() {
        switchToPage(Page.unitTrades);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = getUser();
        displayUserDetails(user);
        if (!user.isAdmin()) {
            anchorPane.getChildren().remove(assetsButton);
        }
    }
}
