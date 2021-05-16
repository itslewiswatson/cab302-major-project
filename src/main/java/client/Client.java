package client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the client application.
 */
public class Client extends Application {

    /**
     *
     * Starts the JavaFX application.
     *
     * @param stage The initial stage.
     */
    @Override
    public void start(Stage stage) {
        ClientController clientController = new ClientController(stage);
        clientController.openLogin();
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}