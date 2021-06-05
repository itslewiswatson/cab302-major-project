package client.strategy;

import client.alert.AlertDialog;
import client.config.Page;
import common.domain.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.nio.file.Paths;
import java.util.Properties;

public class ClientController {

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
     * Handle switching between pages
     *
     * @param page The page to switch to
     */
    protected void switchToPage(Page page) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + page.path));

        try {
            Constructor<?> controller = (page.namespace).getConstructor(ClientController.class);
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
        } catch (IOException | NoSuchMethodException exception) {
            exception.printStackTrace();
            AlertDialog.fileError();
        }
    }

    /**
     * Initialising method.
     */
    private void openSocket() {
        InetAddress hostName = getHostName();
        int portNumber = getPortNumber();

        if (hostName == null || portNumber == -1) return;

        try {
            Socket clientSocket = new Socket(hostName, portNumber);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException exception) {
            AlertDialog.error("Could not establish communication with the server", "Ensure the server is running and the configuration file is correct.");
            Platform.exit();
        }
    }

    private InetAddress getHostName() {
        Properties properties = new Properties();
        FileInputStream fileInputStream;

        try {
            URI jarFileURI = ClientController.class.getProtectionDomain().getCodeSource().getLocation().toURI();

            File jarFile = Paths.get(jarFileURI).toFile();
            File propertiesFile = new File(jarFile.getParentFile(), "client.properties");

            fileInputStream = new FileInputStream(propertiesFile);
            properties.load(fileInputStream);
            fileInputStream.close();

            String hostName = properties.getProperty("hostname");

            return InetAddress.getByName(hostName);

        } catch (FileNotFoundException exception) {
            AlertDialog.error("client.properties is not where it is expected to be.",
                    "Ensure it is in the same directory as client.jar.");
            Platform.exit();
        } catch (UnknownHostException exception) {
            AlertDialog.error("Invalid host name.",
                    "Please change the hostname property in client.properties to be a valid host name.");
            Platform.exit();
        } catch (IOException exception) {
            AlertDialog.error("client.properties has become corrupted.",
                    "Rebuild the program.");
            Platform.exit();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getPortNumber() {
        Properties properties = new Properties();
        FileInputStream fileInputStream;

        try {
            URI jarFileURI = ClientController.class.getProtectionDomain().getCodeSource().getLocation().toURI();

            File jarFile = Paths.get(jarFileURI).toFile();
            File propertiesFile = new File(jarFile.getParentFile(), "client.properties");

            fileInputStream = new FileInputStream(propertiesFile);
            properties.load(fileInputStream);
            fileInputStream.close();

            String portNumberString = properties.getProperty("port");

            int portNumber = Integer.parseInt(portNumberString);

            if (portNumber < 1024 || portNumber > 65535) throw new Exception();

            return portNumber;

        } catch (FileNotFoundException exception) {
            AlertDialog.error("client.properties is not where it is expected to be.",
                    "Ensure it is in the same directory as client.jar.");
            Platform.exit();
        } catch (IOException exception) {
            AlertDialog.error("client.properties has become corrupted.",
                    "Rebuild the program.");
            Platform.exit();
        } catch (Exception exception) {
            AlertDialog.error("Invalid port number.",
                    "Please change the port property in client.properties to be a number between 1024 and 65535.");
            Platform.exit();
        }

        return -1;
    }
}
