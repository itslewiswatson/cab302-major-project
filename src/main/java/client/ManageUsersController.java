package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.dialog.NewUserDialogController;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.Unit;
import common.domain.User;
import common.dto.*;
import common.exceptions.NullResultException;
import common.services.PasswordHasher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageUsersController extends Controller implements Initializable {
    public ManageUsersController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private ComboBox<Unit> unitComboBox;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> usernameTableColumn;

    @FXML
    private TableColumn<User, String> adminTableColumn;

    @FXML
    private TableView<Unit> unitTableView;

    @FXML
    private TableColumn<Unit, String> unitNameColumn;

    @FXML
    private TextField updatePasswordField;

    @FXML
    private Button updatePasswordButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button accountTypeButton;

    @FXML
    private Button removeUnitButton;

    @FXML
    private Button addUnitButton;

    private ArrayList<User> fetchUsers() {
        sendObject(new GetUsersDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<Unit> fetchUserUnits(String userId) {
        sendObject(new GetUnitsDTO(userId));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    private ArrayList<Unit> fetchUnits() {
        sendObject(new GetUnitsDTO(null));
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUsersTable();
        setupUserUnitsTable();
        setupUnitsComboBox();
        populateUsersTable();
    }

    private void setupUnitsComboBox() {
        unitComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Unit unit) {
                return unit != null ? unit.getUnitName() : "";
            }

            @Override
            public Unit fromString(String s) {
                return null;
            }
        });
    }

    private void populateUnitComboBox(User selectedUser) {
        ArrayList<Unit> allUnits = fetchUnits();
        ArrayList<Unit> units = fetchUserUnits(selectedUser.getUserId());
        ArrayList<Unit> unitsNotIn = allUnits.stream()
                .filter(unit -> units.stream()
                        .filter(a -> a.getUnitId().equals(unit.getUnitId()))
                        .collect(Collectors.toCollection(ArrayList::new))
                        .size() == 0)
                .collect(Collectors.toCollection(ArrayList::new));

        unitComboBox.setItems(FXCollections.observableArrayList(unitsNotIn));
    }

    private void setupUsersTable() {
        usernameTableColumn.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().getUsername()));
        adminTableColumn.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().isAdmin() ? "Yes" : "No"));

        usersTableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() != 1) {
                    disableInputs();
                    return;
                }
                @Nullable User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
                if (selectedUser == null) {
                    disableInputs();
                    return;
                }
                populateUserUnitsTable(selectedUser);
                populateUnitComboBox(selectedUser);
                accountTypeButton.setText(selectedUser.isAdmin() ? "Make Standard" : "Make Admin");
                enableInputs();
            });
            return row;
        });
    }

    private void setupUserUnitsTable() {
        unitNameColumn.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().getUnitName()));
    }

    public void populateUsersTable() {
        ArrayList<User> users = fetchUsers();
        usersTableView.setItems(FXCollections.observableArrayList(users));
    }

    public void populateUserUnitsTable(User selectedUser) {
        ArrayList<Unit> units = fetchUserUnits(selectedUser.getUserId());
        unitTableView.setItems(FXCollections.observableArrayList(units));
    }

    private void disableInputs() {
        accountTypeButton.setDisable(true);
        updatePasswordField.setDisable(true);
        updatePasswordButton.setDisable(true);
        deleteUserButton.setDisable(true);
        removeUnitButton.setDisable(true);
        addUnitButton.setDisable(true);
        unitComboBox.setDisable(true);
    }

    private void enableInputs() {
        accountTypeButton.setDisable(false);
        updatePasswordField.setDisable(false);
        updatePasswordButton.setDisable(false);
        deleteUserButton.setDisable(false);
        removeUnitButton.setDisable(false);
        addUnitButton.setDisable(false);
        unitComboBox.setDisable(false);
    }

    @FXML
    public void changeAccountType() {
        User user = usersTableView.getSelectionModel().getSelectedItem();
        if (user == null) return;

        if (user.getUserId().equals(getUser().getUserId())) {
            AlertDialog.warning("You cannot change your own account type", "Please confer with another IT admin to do this");
            return;
        }

        user.setAdmin(!user.isAdmin());

        sendObject(new UpdateUserPermissionsDTO(user.getUserId(), user.isAdmin()));
        try {
            readObject();
        } catch (NullResultException e) {
            AlertDialog.error("Could not update user account type", "Please try again");
        }
        populateUsersTable();
    }

    @FXML
    public void deleteUser() {
        User user = usersTableView.getSelectionModel().getSelectedItem();
        if (user == null) return;

        if (user.getUserId().equals(getUser().getUserId())) {
            AlertDialog.warning("You cannot delete your own account", "Please confer with another IT admin to do this");
            return;
        }

        sendObject(new DeleteUserDTO(user.getUserId()));
        try {
            Boolean success = readObject();
            if (!success) {
                AlertDialog.error("Could not delete user", "Please try again");
                return;
            }
        } catch (NullResultException e) {
            AlertDialog.error("Could not delete user", "Please try again");
            return;
        }

        AlertDialog.info("Successfully deleted user " + user.getUsername());
        populateUsersTable();
    }

    @FXML
    public void updatePassword() {
        User user = usersTableView.getSelectionModel().getSelectedItem();
        if (user == null) return;

        String newPassword = updatePasswordField.getText();
        if (newPassword.length() <= 0) {
            AlertDialog.info("New password cannot be empty");
            return;
        }
        user.setPassword(PasswordHasher.hashPassword(newPassword));

        sendObject(new UpdatePasswordDTO(user.getUserId(), user.getPassword()));
        try {
            readObject();
        } catch (NullResultException e) {
            AlertDialog.error("Could not update password", "Please try again");
            return;
        }

        AlertDialog.info("Successfully changed password for user " + user.getUsername());
        populateUsersTable();
    }

    @FXML
    public void addToUnit() {
        @Nullable User user = usersTableView.getSelectionModel().getSelectedItem();
        if (user == null) return;

        @Nullable Unit unit = unitComboBox.getValue();
        if (unit == null) return;

        sendObject(new AddUserToUnitDTO(user.getUserId(), unit.getUnitId()));
        try {
            Boolean success = readObject();
            if (!success) {
                AlertDialog.error("Could not add user to unit", "Please try again");
                return;
            }
        } catch (NullResultException e) {
            AlertDialog.error("Could not add user to unit", "Please try again");
            return;
        }

        populateUnitComboBox(user);
        AlertDialog.info("Successfully added " + user.getUsername() + " to " + unit.getUnitName());
    }

    @FXML
    public void removeFromUnit() {
        @Nullable User user = usersTableView.getSelectionModel().getSelectedItem();
        if (user == null) return;

        @Nullable Unit unit = unitTableView.getSelectionModel().getSelectedItem();
        if (unit == null) return;

        sendObject(new RemoveUserFromUnitDTO(user.getUserId(), unit.getUnitId()));
        try {
            Boolean success = readObject();
            if (!success) {
                AlertDialog.error("Could not remove user from unit", "Please try again");
                return;
            }
        } catch (NullResultException e) {
            e.printStackTrace();
            AlertDialog.error("Could not remove user from unit", "Please try again");
            return;
        }

        populateUnitComboBox(user);
        AlertDialog.info("Successfully removed " + user.getUsername() + " from " + unit.getUnitName(), "Please try again");
    }

    public void viewNewUserDialog() {
        NewUserDTO dto = new NewUserDTO(null, null, null);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newUserDialog.fxml"));
            loader.setControllerFactory(c -> new NewUserDialogController(dto));
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

        // TODO more validation
        if (dto.getUsername() != null && dto.getPassword() != null && dto.isAdmin() != null) {
            sendObject(dto);
            try {
                User user = readObject();
                AlertDialog.info("Successfully created user " + user.getUsername(), "They can now log in with the password you just entered");
            } catch (NullResultException e) {
                AlertDialog.error("Could not create user", "Please try again");
                e.printStackTrace();
            }

            populateUsersTable();
        }
    }
}
