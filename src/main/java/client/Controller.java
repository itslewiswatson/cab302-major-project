package client;

import client.alert.AlertDialog;
import client.config.Page;
import common.domain.User;
import common.exceptions.NullResultException;
import javafx.stage.Stage;

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
            AlertDialog.serverCommunication();
        }
    }

    /**
     * Read the current object the input stream
     *
     * @param <T> Type to be returned
     * @return Object of type T
     * @throws NullResultException Null is read from the input stream
     */
    protected <T> T readObject() throws NullResultException {
        try {
            return readObject_();
        } catch (IOException e) {
            AlertDialog.serverCommunication();
        } catch (ClassNotFoundException e) {
            AlertDialog.fileError();
        }
        throw new NullResultException();
    }

    /**
     * @throws IOException            Any of the usual Input/Output related exceptions
     * @throws ClassNotFoundException Class of a serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    private <T> T readObject_() throws NullResultException, IOException, ClassNotFoundException {
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

    protected Stage getStage() {
        return clientController.getStage();
    }

    protected void switchToPage(Page page) {
        clientController.switchToPage(page);
    }
}
