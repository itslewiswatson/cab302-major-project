package client;

import client.strategy.ClientController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This class is the client application.
 */
public class Client extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage The initial stage.
     */
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("Electronic Asset Trading Platform");

        ClientController clientController = new ClientController(stage);
        clientController.initialise();
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}