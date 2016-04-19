package hrbuddy.Controllers;

import hrbuddy.Utils.Logger;
import hrbuddy.Views.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nboisvert on 13/04/16.
 */
public class MainController extends TabPane{
    protected Map<String, View> views = new HashMap<String,View>(){{
        put("candidate",new View("Candidat", "CandidateView.fxml"));
        put("search_candidate",new View("Recherche candidat", "SearchView.fxml"));
    }};

    public TabPane mainTabPane;
    protected HashMap<String,Node> tabs = new HashMap<>();

    public void newTab(String view, int id) {
        if(views.containsKey(view)) {
            try {
                View selected_view = views.get(view);
                Tab tab = new Tab(selected_view.getName());
                FXMLLoader loader = new FXMLLoader(getClass().getResource(selected_view.getPath()));
                Parent root = (Parent)loader.load();
                AnchorPane pane = new AnchorPane(root);
                AnchorPane.setBottomAnchor(root,0.0);
                AnchorPane.setLeftAnchor(root,0.0);
                AnchorPane.setRightAnchor(root,0.0);
                AnchorPane.setTopAnchor(root,0.0);
                tab.setContent(pane);
                ControlledScreen c = loader.getController();
                c.setScreenParent(this);
                c.loadRecord(id);
                this.tabs.put(selected_view.getName(),root);
                mainTabPane.getTabs().add(tab);
                mainTabPane.getSelectionModel().select(tab);
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

    public void loadCandidateTab(int id){
        this.newTab("candidate",id);
    }

    public void newCandidateTab(ActionEvent actionEvent){
        this.newTab("candidate",-1);
    }

    public void newQuotation(ActionEvent actionEvent) {
        this.newTab("quotation",-1);
    }

    public void searchCandidate(ActionEvent actionEvent) {
        this.newTab("search_candidate",-1);
    }

    public void closeApplication(ActionEvent actionEvent) {
        ((Stage) this.mainTabPane.getScene().getWindow()).close();
    }
}
