package client;

import common.domain.User;
import common.dto.LoginDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class is the Login GUI controller.
 */
public class LoginController extends Controller {

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
            Object object = readObject();

            if (object instanceof User) {
                User user = (User) object;

                if (user.getUsername() != null && BCrypt.checkpw(password, user.getPassword())) {
                    setUser(user);
                    switchToPage(Page.myAccount);

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                    alert.setHeaderText("Incorrect Username or Password.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        }
    }
}
