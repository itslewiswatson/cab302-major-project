package client;

import common.ExistingUser;
import common.Username;
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
public class LoginController {

    private ClientController clientController;

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
        int minLength = 1;
        int maxLength = 255;

        try {
            Username username = new Username(usernameField.getText());
            String password = passwordField.getText();

            if (password.length() >= minLength && password.length() <= maxLength)
            {
                clientController.getOutputStream().writeObject(username);
                clientController.getOutputStream().flush();

                ExistingUser existingUser = (ExistingUser) clientController.getInputStream().readObject();

                if (existingUser.getUsername() != null && BCrypt.checkpw(password, existingUser.getPassword()))
                {
                    clientController.setUser(existingUser);
                    clientController.switchToMyAccount();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                    alert.setHeaderText("Incorrect Username or Password.");
                    alert.showAndWait();
                }
            }
            else {
                throw new Exception();
            }
        }
        catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        }
        catch (ClassNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
        }
        catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please try again.", ButtonType.OK);
            alert.setHeaderText("Invalid Username or Password.");
            alert.showAndWait();
        }
    }

    void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
