package hrbuddy;

import hrbuddy.Database.Database;
import hrbuddy.Models.Candidate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/SearchView.fxml"));

        ResultSet rs = Candidate.all();

        while (rs.next()) {
            System.out.println(rs.getString("firstname"));
        }

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
