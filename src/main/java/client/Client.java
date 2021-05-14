package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 */
public class Client extends Application {
    /**
     *
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.DARKGREY);

        stage.setTitle("Client");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}