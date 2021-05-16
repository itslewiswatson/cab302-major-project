package client;

import common.domain.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

    private User user;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    protected final Stage stage;

    public ClientController(Stage stage) {
        this.stage = stage;
    }

    protected User getUser() {
        return user;
    }

    protected void setUser(User user) {
        this.user = user;
    }

    public void initialise() {
        openSocket();
        switchToPage(Page.login);
    }

    protected ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    protected ObjectInputStream getInputStream() {
        return inputStream;
    }

    /**
     * Initialising method.
     */
    @FXML
    private void openSocket() {
        String host = "127.0.0.1";
        int portNumber = 1234;

        try {
            Socket clientSocket = new Socket(host, portNumber);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ensure the server is running and the configuration file is correct.", ButtonType.OK);
            alert.setHeaderText("Could not connect to server.");
            alert.showAndWait();
            Platform.exit();
        }
    }

    /**
     * Handle switching between pages
     *
     * @param page The page to switch to
     */
    protected void switchToPage(Page page) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + page.path));

        try {
            Parent root = loader.load();
            Controller mainController = loader.getController();
            mainController.setClientController(this);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
        }
    }

}
