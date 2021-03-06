package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.dialog.NewTradeDialogController;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Trade;
import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitTradesDTO;
import common.dto.GetUnitsDTO;
import common.dto.UpdatePasswordDTO;
import common.exceptions.NullResultException;
import common.services.PasswordHasher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This class is the My Account GUI controller.
 */
public class LandingController extends Controller implements Initializable {

    public LandingController(ClientController clientController) {
        super(clientController);
    }

    private Timeline notifier;

    @FXML
    private Label welcomeMessageLabel;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Button unitTrades;

    @FXML
    private Button newTrade;

    @FXML
    private Button manageAssetsButton;

    @FXML
    private Button manageUnitsButton;

    @FXML
    private Button manageUsersButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int notifyPeriod = 5;
        User user = getUser();
        displayWelcomeMessage(user.getUsername());
        unitTrades.setDisable(user.isAdmin());
        newTrade.setDisable(user.isAdmin());
        manageAssetsButton.setVisible(user.isAdmin());
        manageUnitsButton.setVisible(user.isAdmin());
        manageUsersButton.setVisible(user.isAdmin());

        notifier = new Timeline(
                new KeyFrame(
                        Duration.seconds(notifyPeriod),
                        event -> displayNotification()
                )
        );

        notifier.setCycleCount(Timeline.INDEFINITE);
        notifier.play();
    }

    public void changePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        try {
            if (currentPassword.length() <= 0 || newPassword.length() <= 0) {
                AlertDialog.info("Current password or new password cannot be empty.", "Please try again.");
                return;
            }

            if (!PasswordHasher.checkPassword(currentPassword, getUser().getPassword())) {
                AlertDialog.info("Current password is incorrect.", "Please try again.");
                return;
            }

            if (currentPassword.equals(newPassword)) {
                AlertDialog.info("Current password and new password are the same.", "Please try again.");
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
                AlertDialog.info("Password changed successfully.");
            } else {
                AlertDialog.error("Password could not be updated.", "Please contact an IT administrator.");
            }
        } catch (Exception e) {
            AlertDialog.serverCommunication();
        }
    }

    public void logout() {
        notifier.stop();
        switchToPage(Page.login);
    }

    public void allTrades() {
        notifier.stop();
        switchToPage(Page.allTrades);
    }

    public void tradeHistory() {
        notifier.stop();
        switchToPage(Page.tradeHistory);
    }

    public void assets() {
        notifier.stop();
        switchToPage(Page.assets);
    }

    public void unitTrades() {
        notifier.stop();
        switchToPage(Page.unitTrades);
    }

    public void newTrade() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newTrade.fxml"));
            loader.setControllerFactory(c -> new NewTradeDialogController(super.clientController));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.centerOnScreen();
            dialog.initOwner(getStage());
            dialog.setScene(scene);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageUnits() {
        notifier.stop();
        switchToPage(Page.manageUnits);
    }

    public void manageUsers() {
        notifier.stop();
        switchToPage(Page.manageUsers);
    }

    private void displayWelcomeMessage(String username) {
        welcomeMessageLabel.setText("Welcome, " + username + "!");
    }

    private void displayNotification() {
        ArrayList<Trade> allUnitTrades = new ArrayList<>();
        ArrayList<Unit> userUnits = fetchUserUnits();

        for (Unit unit: userUnits) {
            ArrayList<Trade> unitTrades = fetchUnitTrades(unit.getUnitId());
            allUnitTrades.addAll(unitTrades);
        }

        ArrayList<Trade> newFulfilledTrades = allUnitTrades.stream()
                .filter(allUnitTrade -> allUnitTrade.getDateFilled() != null)
                .collect(Collectors.toCollection(ArrayList::new));


        if (!previousFulfilledTradesInitialised)
        {
            previousFulfilledTrades = newFulfilledTrades;
            previousFulfilledTradesInitialised = true;
            return;
        }


        if (newFulfilledTrades.size() > previousFulfilledTrades.size())
        {
            Notifications.create()
                    .title("Trade/s Reconciled")
                    .text("Trade/s listed by one of your units has been reconciled.")
                    .showInformation();
        }

        previousFulfilledTrades = newFulfilledTrades;
    }

    private ArrayList<Unit> fetchUserUnits() {
        sendObject(new GetUnitsDTO(getUser().getUserId()));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<Trade> fetchUnitTrades(String unitId) {
        sendObject(new GetUnitTradesDTO(unitId));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }
}
