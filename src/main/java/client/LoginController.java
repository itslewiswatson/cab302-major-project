package client;

import common.domain.User;
import common.dto.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

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
     *
     * @param event Event action.
     */
    public void submitLogin(ActionEvent event) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (password.length() >= User.USERNAME_MIN_LENGTH && password.length() <= User.USERNAME_MAX_LENGTH) {
                LoginDTO loginDTO = new LoginDTO(username);

                outputStream.writeObject(loginDTO);
                outputStream.flush();

                Object object = inputStream.readObject();

                if (object instanceof User) {
                    User user = (User) object;

                    if (user.getUsername() != null && BCrypt.checkpw(password, user.getPassword())) {
                        switchToMyAccount(event);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                        alert.setHeaderText("Incorrect Username or Password.");
                        alert.showAndWait();
                    }
                }
            } else {
                throw new Exception();
            }
        } catch (IOException | ClassNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please try again.", ButtonType.OK);
            alert.setHeaderText("Invalid Username or Password.");
            alert.showAndWait();
        }
    }

    /**
     * Method to switch to My Account page.
     *
     * @param event Event action.
     */
    public void switchToMyAccount(ActionEvent event) {
        switchToPage(event, Page.myAccount);
    }
}
