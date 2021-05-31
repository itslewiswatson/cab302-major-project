package client;

import client.alert.AlertDialog;
import client.config.Page;
import common.domain.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    protected Stage getStage() {
        return stage;
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
            AlertDialog.error("Could not establish communication with the server", "Ensure the server is running and the configuration file is correct");
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
            Constructor<?> controller = Class.forName(page.namespace).getConstructor(ClientController.class);
            loader.setControllerFactory(Controller -> {
                try {
                    return controller.newInstance(this);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    AlertDialog.fileError();
                }
                return null;
            });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException exception) {
            exception.printStackTrace();
            AlertDialog.fileError();
        }
    }
}
