package hrbuddy.Controllers;

import hrbuddy.Models.Candidate;
import hrbuddy.Models.Experience;
import hrbuddy.Utils.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class CandidateController implements Initializable, ControlledScreen{
    @FXML
    private ComboBox genderComboBox;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField homephoneTextField;

    @FXML
    private TextField mobilephoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TableView experiencesTableView;

    @FXML
    private TableColumn<Experience,String> experiencesFunctionColumn;

    @FXML
    private TableColumn<Experience,String> experiencesStartDateColumn;

    @FXML
    private TableColumn<Experience,String> experiencesEndDateColumn;

    @FXML
    private TableColumn<Experience,String> experiencesOrganisationColumn;
    @FXML
    private Button newButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane container;

    //  Id of the candidate to show
    protected String candidate_id = "-1";

    //  Instance of the shown candidate
    protected Candidate candidate;

    //  Define if the form is in edition mode
    protected boolean editionMode = false;

    //  References the parent view
    protected MainController parent;

    //  List of the candidate's experiences
    private ObservableList<Experience> experiences;

    /**
     * References the main parent of the view, the global container
     *
     * @param parent
     */
    public void setScreenParent(MainController parent){
        this.parent = parent;
    }

    /**
     * Assign the record id to be presented by the form
     *
     * @param id
     */
    public void loadRecord(int id){
        this.candidate_id = String.valueOf(id);
        this.loadCandidate();
    }

    /**
     * Initialize form events, like TextField leaving
     */
    public void initEvents(){
        firstnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.onInputValueChanged(null);
        });
        lastnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.onInputValueChanged(null);
        });
        homephoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.onInputValueChanged(null);
        });
        mobilephoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.onInputValueChanged(null);
        });
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.onInputValueChanged(null);
        });
        addressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.onInputValueChanged(null);
        });
    }

    /**
     * Initialize tables and assign cell factory
     */
    public void initTables(){
        experiencesFunctionColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("JobFunction"));
        experiencesStartDateColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("StartDate"));
        experiencesEndDateColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("EndDate"));
        experiencesOrganisationColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("Organisation"));
    }

    /**
     * Initialize everything else. This method is executed first on view load
     *
     * @param url
     * @param bundle
     */
    public void initialize(URL url, ResourceBundle bundle){
        this.initEvents();
        this.initListValues();
        this.initTables();
    }

    /**
     * Initialize every ComboBox contents
     */
    private void initListValues() {

    }

    /**
     * Event when the Nouveau button is pressed
     *
     * @param actionEvent
     */
    public void newCandidate(ActionEvent actionEvent) {
        this.candidate_id = "-1";
        this.candidate = null;
        this.clearForm();
    }

    /**
     * Fill the form with empty values
     */
    public void clearForm(){
        this.firstnameTextField.setText("");
        this.lastnameTextField.setText("");
        this.emailTextField.setText("");
        this.homephoneTextField.setText("");
        this.mobilephoneTextField.setText("");
        this.idTextField.setText("");
    }

    /**
     * Fill the form with the fetched candidate.
     */
    public void fillForm(){
        if(this.candidate != null){
            this.firstnameTextField.setText(this.candidate.getFirstname());
            this.lastnameTextField.setText(this.candidate.getLastname());
            this.emailTextField.setText(this.candidate.getEmail());
            this.homephoneTextField.setText(this.candidate.getHomePhone());
            this.mobilephoneTextField.setText(this.candidate.getCellPhone());
            this.idTextField.setText(this.candidate.getId());
            List<Experience> exp_list = this.candidate.getExperiences(true);
            if(exp_list.size() > 0){
                experiences = FXCollections.observableArrayList(exp_list);
                experiencesTableView.setItems(experiences);
            }
        }
        else{
            this.clearForm();
        }
    }

    /**
     * Fetch the candidate from the database
     */
    public void loadCandidate(){
        int id = Integer.parseInt(this.candidate_id);
        if(id > 0) {
            this.candidate = Candidate.find(id);
            this.fillForm();
            Logger.debug(String.valueOf(id));
        }
        else{
            Logger.debug("Couldn't load id : "+this.candidate_id);
        }
    }

    /**
     * Event when the Sauvegarder button is clicked, if the record was loaded from
     * the database, then it updates, else it insert it
     *
     * @param actionEvent
     */
    public void storeCandidate(ActionEvent actionEvent) {
        Candidate candidate;
        if(this.candidate_id.equals("-1")) {
            candidate = new Candidate(
                    this.firstnameTextField.getText(),
                    this.lastnameTextField.getText(),
                    this.addressTextField.getText(),
                    this.emailTextField.getText(),
                    this.homephoneTextField.getText(),
                    this.mobilephoneTextField.getText()
            );
        }
        else{
            candidate = Candidate.find(Integer.parseInt(this.candidate_id));
            candidate.setFirstname(this.firstnameTextField.getText());
            candidate.setLastname(this.lastnameTextField.getText());
            candidate.setAddress(this.addressTextField.getText());
            candidate.setEmail(this.emailTextField.getText());
            candidate.setHomePhone(this.homephoneTextField.getText());
            candidate.setCellPhone(this.mobilephoneTextField.getText());
        }
        candidate.save();
        this.editionMode = false;
        this.idTextField.setText(candidate.getId());
        eventTriggered();
    }

    /**
     * When form is modified, change the ui if needed
     */
    public void eventTriggered(){
        this.saveButton.setDisable(!this.editionMode);
        this.newButton.setDisable(this.editionMode);
    }

    /**
     * Event when a TextField input is modified
     *
     * @param event
     */
    public void onInputValueChanged(Event event) {
        this.editionMode = true;
        eventTriggered();
    }
}
