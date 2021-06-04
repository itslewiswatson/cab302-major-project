package client.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertDialog {
    private static void base(Alert.AlertType alertType, String header, String content) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public static void warning(String header, String content) {
        base(Alert.AlertType.WARNING, header, content);
    }

    public static void info(String header, String content) {
        base(Alert.AlertType.INFORMATION, header, content);
    }

    public static void info(String header) {
        info(header, "");
    }

    public static void error(String header) {
        error(header, "");
    }

    public static void error(String header, String content) {
        base(Alert.AlertType.ERROR, header, content);
    }

    public static void serverCommunication() {
        warning("Cannot communicate with server", "If the problem persists, restart the client.");
    }

    public static void fileError() {
        error("A program file has been deleted or corrupted", "Try restarting the client or rebuilding the program");
    }
}
