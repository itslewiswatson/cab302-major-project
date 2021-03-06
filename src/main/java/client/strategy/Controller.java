package client.strategy;

import client.alert.AlertDialog;
import client.config.Page;
import common.domain.Trade;
import common.domain.User;
import common.exceptions.NullResultException;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Abstracted controller with common functionality
 */
public class Controller {
    protected final ClientController clientController;

    protected static ArrayList<Trade> previousFulfilledTrades = new ArrayList<>();

    protected static Boolean previousFulfilledTradesInitialised = false;

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
     * Read the current object from the input stream
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
     * Generic interface to read objects from the input stream without complex casting
     *
     * @throws IOException            Any of the usual Input/Output related exceptions
     * @throws ClassNotFoundException Class of a serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    private <T> T readObject_() throws NullResultException, IOException, ClassNotFoundException {
        ObjectInputStream inputStream = clientController.getInputStream();
        T object = (T) inputStream.readObject();
        // Treat null responses from the server as errors
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
