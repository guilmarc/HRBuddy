package hrbuddy.Controllers;

import hrbuddy.Utils.Logger;
import hrbuddy.Views.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nboisvert on 13/04/16.
 */
public class MainController {
    protected Map<String, View> views = new HashMap<String,View>(){{
        put("candidate",new View("Candidat", "../Views/CandidateView.fxml"));
        put("search_candidate",new View("Recherche candidat", "../Views/SearchView.fxml"));
    }};

    public TabPane mainTabPane;

    public void newTab(String view) {
        if(views.containsKey(view)) {
            try {
                View selected_view = views.get(view);
                Tab tab = new Tab(selected_view.getName());
                Parent root = FXMLLoader.load(getClass().getResource(selected_view.getPath()));
                AnchorPane pane = new AnchorPane(root);
                tab.setContent(pane);
                mainTabPane.getTabs().add(tab);
            }
            catch (Exception e){
                DialogController.warning("La vue demandée est inexistante");
                Logger.except(e.getMessage());
            }
        }
        else{
            DialogController.warning("La vue demandée est inexistante");
        }
    }

    public void loadCandidateTab(){

    }

    public void newCandidateTab(ActionEvent actionEvent){
        this.newTab("candidate");
    }

    public void newQuotation(ActionEvent actionEvent) {
        this.newTab("quotation");
    }

    public void closeApplication(ActionEvent actionEvent) {
    }

    public void serachCandidate(ActionEvent actionEvent) {
        this.newTab("search_candidate");
    }
}
