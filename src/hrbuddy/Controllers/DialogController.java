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
    public static void dialog(String message, Alert.AlertType type){
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
    public static void warning(String message){
        DialogController.dialog(message, Alert.AlertType.WARNING);
    }
    public static void confirmation(String message){
        DialogController.dialog(message, Alert.AlertType.CONFIRMATION);
    }
    public static void error(String message){
        DialogController.dialog(message, Alert.AlertType.ERROR);
    }
}
