package hrbuddy.Controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by nboisvert on 13/04/16.
 */
public class DialogController {
    public static Alert dialog(String message, Alert.AlertType type){
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
        return alert;
    }
    public static Alert warning(String message){
        return DialogController.dialog(message, Alert.AlertType.WARNING);
    }
    public static Alert confirmation(String message){
        return DialogController.dialog(message, Alert.AlertType.CONFIRMATION);
    }
    public static Alert error(String message){
        return DialogController.dialog(message, Alert.AlertType.ERROR);
    }

    public static Alert noButton(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
        return alert;
    }
}
