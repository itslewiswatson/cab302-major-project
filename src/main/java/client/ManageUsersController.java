package client;

import client.alert.AlertDialog;
import client.config.Page;
import common.domain.Unit;
import common.domain.User;
import common.dto.GetUnitsDTO;
import common.dto.GetUsersDTO;
import common.dto.UpdateUserPermissionsDTO;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
        populateUnitComboBox();
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

    private void populateUnitComboBox() {
        ArrayList<Unit> units = fetchUnits();
        unitComboBox.setItems(FXCollections.observableArrayList(units));
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
                ;
                @Nullable User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
                if (selectedUser == null) {
                    disableInputs();
                    return;
                }
                ;
                populateUserUnitsTable(selectedUser);
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
}
