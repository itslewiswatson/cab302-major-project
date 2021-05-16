package client;

import common.ExistingUser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientController {

    /**
     * The client input stream.
     */
    private ObjectInputStream inputStream;

    /**
     * The client output stream.
     */
    private ObjectOutputStream outputStream;

    private final Stage stage;

    private ExistingUser user;

    ClientController(Stage stage) {
        this.stage = stage;
    }

    ObjectInputStream getInputStream() {
        return inputStream;
    }

    ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    ExistingUser getUser() {
        return user;
    }

    void setUser(ExistingUser user) {
        this.user = user;
    }

    void openLogin() {
        openSocket();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

        try {
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setClientController(this);

            stage.setTitle("Client");
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

    /**
     * Method to switch to My Account page.
     */
    void switchToMyAccount() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myAccount.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MyAccountController myAccountController = loader.getController();
            myAccountController.setClientController(this);
            myAccountController.displayUserDetails();

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
            exception.printStackTrace();
        }
    }

    void switchToLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            LoginController loginController = loader.getController();
            loginController.setClientController(this);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
            exception.printStackTrace();
        }
    }

    private void openSocket() {
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
}
