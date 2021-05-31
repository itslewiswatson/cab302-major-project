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

    public static void error(String header, String content) {
        base(Alert.AlertType.ERROR, header, content);
    }
}
