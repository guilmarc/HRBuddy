package hrbuddy;

import hrbuddy.Database.Database;
import hrbuddy.Models.Candidate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SearchController implements Initializable {


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jobtypeComboBox.getItems().addAll("TEST","TEST");


        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("lastname"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("HomePhone"));
        cellphoneColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("CellPhone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Candidate,String>("email"));

        buildSearchTextFieldData();
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
                System.out.println("Double clicked");
            }
        }
    }


    private ObservableList<Candidate> candidates;


    public void buildSearchTextFieldData(){
        candidates = FXCollections.observableArrayList();
        candidates = (ObservableList<Candidate>)Candidate.search(this.searchTextField.getText(), "firstname");
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
