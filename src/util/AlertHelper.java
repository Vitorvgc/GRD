package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


public class AlertHelper {

    public static Alert error(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static Alert error(String message) {
        return error("Error", message);
    }

    public static Alert confirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static Alert confirmation(String message) {
        return confirmation("Confirmation", message);
    }

    public static Alert confirmation(String title, String message, String okText, String cancelText) {

        ButtonType ok = new ButtonType(okText, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(cancelText, ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ok, cancel);
        alert.setTitle(title);
        alert.setHeaderText(null);

        return alert;
    }

    public static Alert warning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static Alert warning(String message) {
        return warning("Warning", message);
    }

    public static Alert warning(String title, String message, String okText, String cancelText) {

        ButtonType ok = new ButtonType(okText, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(cancelText, ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.WARNING, message, ok, cancel);
        alert.setTitle(title);
        alert.setHeaderText(null);

        return alert;
    }

    public static Alert information(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static Alert information(String message) {
        return information("Information", message);
    }
}
