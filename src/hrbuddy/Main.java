package hrbuddy;

import hrbuddy.Controllers.DialogController;
import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Query;
import hrbuddy.Database.QueryBuilder.SearchQuery;
import hrbuddy.Models.Candidate;
import hrbuddy.Models.Candidate2;
import hrbuddy.Models.Experience;
import hrbuddy.Models.Model;
import hrbuddy.Utils.Logger;
import hrbuddy.Utils.Utils;
import hrbuddy.Views.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 768;
    private final String APP_NAME = "HRBuddy";
    private final String MAIN_VIEW = "Views/MainView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(this.MAIN_VIEW));


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
