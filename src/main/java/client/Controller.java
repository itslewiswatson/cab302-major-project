package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {

    /**
     * The client input stream.
     */
    protected ObjectInputStream inputStream;

    /**
     * The client output stream.
     */
    protected ObjectOutputStream outputStream;

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
    protected void switchToPage(ActionEvent event, Page page) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + page));

        try {
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Try restarting the client or rebuilding the program.", ButtonType.OK);
            alert.setHeaderText("A program file has been deleted or become corrupted.");
            alert.showAndWait();
        }
    }
}
