package hrbuddy;

import hrbuddy.Database.Database;
import hrbuddy.Database.Drivers.Driver;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Predicates.RawPredicate;
import hrbuddy.Database.QueryBuilder.Query.DeleteQuery;
import hrbuddy.Database.Schema.Schema;
import hrbuddy.Models.Candidate;
import hrbuddy.Utils.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

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
