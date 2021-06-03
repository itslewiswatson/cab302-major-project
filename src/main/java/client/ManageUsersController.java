package client;

import client.config.Page;
import common.domain.User;
import common.dto.GetUsersDTO;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUsersController extends Controller implements Initializable {
    public ManageUsersController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> usernameTableColumn;

    @FXML
    private TableColumn<User, String> adminTableColumn;

    private ArrayList<User> fetchUsers() {
        sendObject(new GetUsersDTO());
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
    }

    private void setupUsersTable() {
        usernameTableColumn.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().getUsername()));
        adminTableColumn.setCellValueFactory(v -> new ReadOnlyObjectWrapper<>(v.getValue().isAdmin() ? "Yes" : "No"));

        ArrayList<User> users = fetchUsers();
        usersTableView.setItems(FXCollections.observableArrayList(users));
    }
}
