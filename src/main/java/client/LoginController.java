package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.User;
import common.dto.LoginDTO;
import common.exceptions.NullResultException;
import common.services.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class is the Login GUI controller.
 */
public class LoginController extends Controller {

    public LoginController(ClientController clientController) {
        super(clientController);
    }

    /**
     * Username text field.
     */
    @FXML
    private TextField usernameField;

    /**
     * Password text field.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Login button action method.
     */
    public void login() {

        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginDTO loginDTO = new LoginDTO(username);

            sendObject(loginDTO);
            User user = readObject();

            if (user.getUsername() != null && PasswordHasher.checkPassword(password, user.getPassword())) {
                setUser(user);
                switchToPage(Page.myAccount);
            } else {
                AlertDialog.info("Incorrect username or password.", "Please try again.");
            }
        } catch (NullResultException e) {
            AlertDialog.warning("No such username exists", "Contact an IT admin for an account");
        } catch (Exception e) {
            e.printStackTrace();
            AlertDialog.serverCommunication();
        }
    }
}
