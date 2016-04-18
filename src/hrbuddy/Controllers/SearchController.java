package hrbuddy.Controllers;

import hrbuddy.Database.Database;
import hrbuddy.Models.Candidate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable, ControlledScreen {
    @FXML
    private Button newButton;

    @FXML
    private TableColumn idColumn;

    @FXML
    private CheckBox firstnameCheckBox;

    @FXML
    private CheckBox lastnameCheckBox;

    @FXML
    private CheckBox addressCheckBox;

    @FXML
    private CheckBox emailCheckBox;

    @FXML
    private CheckBox homephoneCheckBox;

    @FXML
    private CheckBox cellphoneCheckBox;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> jobtypeComboBox;

    @FXML
    private ComboBox<String> formationComboBox;

    @FXML
    private TableView<Candidate> resultTableView;

    @FXML
    private TableColumn<Candidate, String> firstnameColumn;

    @FXML
    private TableColumn<Candidate, String> lastnameColumn;

    @FXML
    private TableColumn<Candidate, String> telephoneColumn;

    @FXML
    private TableColumn<Candidate, String> cellphoneColumn;

    @FXML
    private TableColumn<Candidate, String> emailColumn;

    private ObservableList<Candidate> candidates;

    private MainController parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jobtypeComboBox.getItems().addAll("TEST","TEST");


        idColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("lastname"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("HomePhone"));
        cellphoneColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("CellPhone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("email"));

        //buildSearchTextFieldData();
    }
    public void setScreenParent(MainController parent){
        this.parent = parent;
    }
    public void loadRecord(int id){
    }

    public void handleSearchTextFieldAction(ActionEvent event) {
        buildSearchTextFieldData();
    }

    public void handleFormationComboBoxAction(ActionEvent event) {
        System.out.println("TEST DE CLICK");
    }

    public void handleJobTypeComboBoxAction(ActionEvent event) {
        System.out.println("TEST DE CLICK");
    }

    public void handleResultTableViewDoubleClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                int id = Integer.parseInt(this.resultTableView.getSelectionModel().getSelectedItem().getId());
                System.out.println("Double clicked");
                this.parent.loadCandidateTab(id);
            }
        }
    }




    public void buildSearchTextFieldData(){
        List<String> fields = new ArrayList<>();
        if(this.firstnameCheckBox.isSelected()){
            fields.add("firstname");
        }
        if(this.lastnameCheckBox.isSelected()){
            fields.add("lastname");
        }
        if(this.addressCheckBox.isSelected()){
            fields.add("address");
        }
        if(this.emailCheckBox.isSelected()){
            fields.add("email");
        }
        if(this.homephoneCheckBox.isSelected()){
            fields.add("home_phone");
        }
        if(this.cellphoneCheckBox.isSelected()){
            fields.add("cell_phone");
        }
        candidates = FXCollections.observableArrayList(Candidate.search(this.searchTextField.getText(),fields));
        resultTableView.setItems(candidates);
    }

    public void promptNewCandidateView(ActionEvent actionEvent) {
        this.parent.loadCandidateTab(-1);
    }
}
