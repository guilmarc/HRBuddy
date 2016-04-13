package hrbuddy;

import hrbuddy.Database.Database;
import hrbuddy.Models.Candidate;
import hrbuddy.Utils.Logger;
import hrbuddy.Utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.sql.ResultSet;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/SearchView.fxml"));

        Database.getInstance().getStatus();
        Database.getInstance().getConnection().close();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
