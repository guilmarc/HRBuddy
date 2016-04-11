package hrbuddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {


    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> jobtypeComboBox;

    @FXML
    private ComboBox<String> formationComboBox;

    @FXML
    private TableView<?> resultTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jobtypeComboBox.getItems().addAll("TEST","TEST");
    }

    public void handleSearchTextFieldAction(ActionEvent event) {
        System.out.println("TEST DE CLICK");
    }

    public void handleFormationComboBoxAction(ActionEvent event) {
        System.out.println("TEST DE CLICK");
    }

    public void handleJobTypeComboBoxAction(ActionEvent event) {
        System.out.println("TEST DE CLICK");
    }



}
