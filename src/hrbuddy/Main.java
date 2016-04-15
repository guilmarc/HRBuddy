package hrbuddy;

import hrbuddy.Controllers.DialogController;
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
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 768;
    private final String APP_NAME = "HRBuddy";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));

        Database.getInstance().getStatus();
        Database.getInstance().getConnection().close();
        primaryStage.setTitle(this.APP_NAME);
        primaryStage.setScene(new Scene(root,this.SCENE_WIDTH,this.SCENE_HEIGHT));
        primaryStage.setMinHeight(this.SCENE_HEIGHT);
        primaryStage.setMinWidth(this.SCENE_WIDTH);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
