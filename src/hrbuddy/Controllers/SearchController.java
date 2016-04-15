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


    public TableColumn idColumn;
    public CheckBox firstnameCheckBox;
    public CheckBox lastnameCheckBox;
    public CheckBox addressCheckBox;
    public CheckBox emailCheckBox;
    public CheckBox homephoneCheckBox;
    public CheckBox cellphoneCheckBox;
    public Button searchButton;
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


    private ObservableList<Candidate> candidates;


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




    /*public void buildSearchTextFieldData2(){
        candidates = FXCollections.observableArrayList();
        try{
            String SQL = "Select * from viewSearchCandidates WHERE SearchField LIKE '%" + this.searchTextField.getText() +  "%'" ;  // + this.searchTextField.getText();
            ResultSet rs = Database.getInstance().getConnection().createStatement().executeQuery(SQL);

            while(rs.next()){
                Candidate candidate = new Candidate();
                candidate.setFirstname(rs.getString("firstname"));
                candidate.setLastname(rs.getString("lastname"));
                candidate.setHomePhone(rs.getString("home_phone"));
                candidate.setCellPhone(rs.getString("cell_phone"));
                candidate.setEmail(rs.getString("email"));

                candidates.add(candidate);
            }
            resultTableView.setItems(candidates);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }*/


}
