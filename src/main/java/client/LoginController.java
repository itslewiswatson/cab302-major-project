package client;

import common.ExistingUser;
import common.Username;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is the Login GUI controller.
 */
public class LoginController {

    /**
     * The client input stream.
     */
    private ObjectInputStream inputStream;

    /**
     * The client output stream.
     */
    private ObjectOutputStream outputStream;

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
     * Initialising method.
     */
    @FXML
    public void initialize() {
        String host = "127.0.0.1";
        int portNumber = 1234;

        try {
            Socket clientSocket = new Socket(host, portNumber);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ensure the server is running and the configuration file is correct.", ButtonType.OK);
            alert.setHeaderText("Could not connect to server.");
            alert.showAndWait();
            Platform.exit();
        }
    }

    /**
     * Login button action method.
     *
     * @param event Event action.
     */
    public void submitLogin(ActionEvent event) {
        int minLength = 1;
        int maxLength = 255;

        try {
            Username username = new Username(usernameField.getText());
            String password = passwordField.getText();

            if (password.length() >= minLength && password.length() <= maxLength)
            {
                outputStream.writeObject(username);
                outputStream.flush();

                Object object = inputStream.readObject();

                if (object instanceof ExistingUser) {
                    ExistingUser existingUser = (ExistingUser) object;

                    if (existingUser.getUsername() != null && BCrypt.checkpw(password, existingUser.getPassword()))
                    {
                        switchToMyAccount(event);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please try again.", ButtonType.OK);
                        alert.setHeaderText("Incorrect Username or Password.");
                        alert.showAndWait();
                    }
                }
            }
            else {
                throw new Exception();
            }
        }
        catch (IOException | ClassNotFoundException exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        }
        catch (Exception exception) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myAccount.fxml"));

        try {
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
        }

    }
}
