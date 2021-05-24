package client;

import client.config.Page;
import common.domain.User;
import common.exceptions.NullResultException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Controller {
    private final ClientController clientController;

    public Controller(ClientController clientController) {
        this.clientController = clientController;
    }

    protected void sendObject(Object object) {
        ObjectOutputStream outputStream = clientController.getOutputStream();
        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "If problem persists restart the client.", ButtonType.OK);
            alert.setHeaderText("Cannot communicate with server.");
            alert.showAndWait();
        }
    }

    /**
     * Read the current object the input stream
     *
     * @param <T> Type to be returned
     * @return Object of type T
     * @throws NullResultException    Null is read from the input stream
     * @throws IOException            Any of the usual Input/Output related exceptions
     * @throws ClassNotFoundException Class of a serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    protected <T> T readObject() throws NullResultException, IOException, ClassNotFoundException {
        ObjectInputStream inputStream = clientController.getInputStream();
        T object = (T) inputStream.readObject();
        if (object == null) {
            throw new NullResultException();
        }
        return object;
    }

    protected void setUser(User user) {
        clientController.setUser(user);
    }

    protected User getUser() {
        return clientController.getUser();
    }

    protected void switchToPage(Page page) {
        clientController.switchToPage(page);
    }
}
