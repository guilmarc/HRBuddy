package hrbuddy.Controllers;

import hrbuddy.Database.Database;
import hrbuddy.Database.QueryBuilder.Predicates.Predicate;
import hrbuddy.Database.QueryBuilder.Query.DeleteQuery;
import hrbuddy.Models.Candidate;
import hrbuddy.Models.Experience;
import hrbuddy.Models.Formation;
import hrbuddy.Models.Postulation;
import hrbuddy.Utils.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class CandidateController implements Initializable, ControlledScreen{
    @FXML
    private AnchorPane container;

    @FXML
    private TextField idTextField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField homephoneTextField;

    @FXML
    private TextField mobilephoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TableView<Experience> experiencesTableView;

    @FXML
    private TableColumn<Experience, String> experiencesFunctionColumn;

    @FXML
    private TableColumn<Experience, String> experiencesStartDateColumn;

    @FXML
    private TableColumn<Experience, String> experiencesEndDateColumn;

    @FXML
    private TableColumn<Experience, String> experiencesOrganisationColumn;

    @FXML
    private Button addExperienceButton;

    @FXML
    private Button removeExperienceButton;

    @FXML
    private TableView<Formation> formationsTableView;

    @FXML
    private TableColumn<Formation, String> formationsDiplomaColumn;

    @FXML
    private TableColumn<Formation, String> formationsDateColumn;

    @FXML
    private Button addFormationButton;

    @FXML
    private Button removeFormationButton;

    @FXML
    private TableView<Postulation> postulationsTableView;

    @FXML
    private TableColumn<Postulation, String> postulationsDateColumn;

    @FXML
    private TableColumn<Postulation, String> postulationsJobColumn;

    @FXML
    private TableColumn<Postulation, String> postulationsStatusColumn;

    @FXML
    private TableColumn<Postulation, String> postulationsReasonColumn;

    @FXML
    private Button addPostulationButton;

    @FXML
    private Button removePostulationButton;


    @FXML
    private Button newButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button searchButton;

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

    //  List of the candidate's formations
    private ObservableList<Formation> formations;

    //  List of the candidate's formations
    private ObservableList<Postulation> postulations;


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

    /*Callback<TableColumn, TableCell> cellFactory =
            new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    return new EditingCell();
                }
            };*/




    public void initTables(){
        experiencesFunctionColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("JobFunction"));
        experiencesFunctionColumn.setCellFactory(TextFieldTableCell.<Experience>forTableColumn());
        experiencesFunctionColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Experience, String> t) -> {
                    Experience thisExperience = ((Experience) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisExperience.setJobFunction(t.getNewValue());
                    thisExperience.save();
                });

        experiencesStartDateColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("StartDate"));
        experiencesStartDateColumn.setCellFactory(TextFieldTableCell.<Experience>forTableColumn());
        experiencesStartDateColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Experience, String> t) -> {
                    Experience thisExperience = ((Experience) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisExperience.setStartDate(t.getNewValue());
                    thisExperience.save();
                });

        experiencesEndDateColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("EndDate"));
        experiencesEndDateColumn.setCellFactory(TextFieldTableCell.<Experience>forTableColumn());
        experiencesEndDateColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Experience, String> t) -> {
                    Experience thisExperience = ((Experience) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisExperience.setEndDate(t.getNewValue());
                    thisExperience.save();
                });

        experiencesOrganisationColumn.setCellValueFactory(new PropertyValueFactory<Experience,String>("Organisation"));
        experiencesOrganisationColumn.setCellFactory(TextFieldTableCell.<Experience>forTableColumn());
        experiencesOrganisationColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Experience, String> t) -> {
                    Experience thisExperience = ((Experience) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisExperience.setOrganisation(t.getNewValue());
                    thisExperience.save();
                });


        formationsDiplomaColumn.setCellValueFactory(new PropertyValueFactory<Formation,String>("Diploma"));
        formationsDiplomaColumn.setCellFactory(TextFieldTableCell.<Formation>forTableColumn());
        formationsDiplomaColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Formation, String> t) -> {
                    Formation thisFormation = ((Formation) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisFormation.setDiploma(t.getNewValue());
                    thisFormation.save();
                });

        formationsDateColumn.setCellValueFactory(new PropertyValueFactory<Formation,String>("DateFormation"));
        formationsDateColumn.setCellFactory(TextFieldTableCell.<Formation>forTableColumn());
        formationsDateColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Formation, String> t) -> {
                    Formation thisFormation = ((Formation) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisFormation.setDateFormation(t.getNewValue());
                    thisFormation.save();
                });

        postulationsDateColumn.setCellValueFactory(new PropertyValueFactory<Postulation,String>("Date"));
        postulationsDateColumn.setCellFactory(TextFieldTableCell.<Postulation>forTableColumn());
        postulationsDateColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Postulation, String> t) -> {
                    Postulation thisPostulation = ((Postulation) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisPostulation.setDate(t.getNewValue());
                    thisPostulation.save();
                });

        postulationsJobColumn.setCellValueFactory(new PropertyValueFactory<Postulation,String>("JobPostulated"));
        postulationsJobColumn.setCellFactory(TextFieldTableCell.<Postulation>forTableColumn());
        postulationsJobColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Postulation, String> t) -> {
                    Postulation thisPostulation = ((Postulation) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisPostulation.setJobPostulated(t.getNewValue());
                    thisPostulation.save();
                });

        postulationsStatusColumn.setCellValueFactory(new PropertyValueFactory<Postulation,String>("Status"));
        postulationsStatusColumn.setCellFactory(TextFieldTableCell.<Postulation>forTableColumn());
        postulationsStatusColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Postulation, String> t) -> {
                    Postulation thisPostulation = ((Postulation) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisPostulation.setStatus(t.getNewValue());
                    thisPostulation.save();
                });

        postulationsReasonColumn.setCellValueFactory(new PropertyValueFactory<Postulation,String>("Reason"));
        postulationsReasonColumn.setCellFactory(TextFieldTableCell.<Postulation>forTableColumn());
        postulationsReasonColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Postulation, String> t) -> {
                    Postulation thisPostulation = ((Postulation) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                    thisPostulation.setReason(t.getNewValue());
                    thisPostulation.save();
                });
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
        genderComboBox.getItems().addAll("Homme", "Femme");
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
            experiences = FXCollections.observableArrayList(exp_list);
            experiencesTableView.setItems(experiences);

            List<Formation> formation_list = this.candidate.getFormations(true);
            formations = FXCollections.observableArrayList(formation_list);
            formationsTableView.setItems(formations);

            List<Postulation> postulation_list = this.candidate.getPostulations(true);
            postulations = FXCollections.observableArrayList(postulation_list);
            postulationsTableView.setItems(postulations);

            if(exp_list.size() > 0){
                //Retiré du if car ça crée un null pointer sur l'ajout !

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

    public void removeExperience(){
        if(!this.experiencesTableView.getSelectionModel().isEmpty()) {
            int id = Integer.parseInt(this.experiencesTableView.getSelectionModel().getSelectedItem().getId());
            Database.getInstance().execute(new DeleteQuery(Experience.getTable(), new Predicate("id", String.valueOf(id))));
            this.experiences.remove(this.experiencesTableView.getSelectionModel().getFocusedIndex());
            this.experiencesTableView.refresh();
        }
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


    public void handleExperiencesTableViewClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            this.removeExperienceButton.setDisable((this.experiencesTableView.getSelectionModel().isEmpty()));
        }
    }

    public void handleFormationsTableViewClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            this.removeFormationButton.setDisable((this.formationsTableView.getSelectionModel().isEmpty()));
        }
    }

    public void handlePostulationsTableViewClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            this.removePostulationButton.setDisable((this.postulationsTableView.getSelectionModel().isEmpty()));
        }
    }

    public void onExperienceDeleteClick(ActionEvent actionEvent) {
        this.removeExperience();
        this.removeExperienceButton.setDisable((this.experiencesTableView.getSelectionModel().isEmpty()));
    }

    public void handleAddExperienceButtonAction(ActionEvent actionEvent) {

        Experience newExperience = new Experience(this.candidate_id, "nouveau", "nouveau", "nouveau", "nouveau");
        newExperience.save();
        experiences.add(newExperience);

    }

    public void handleAddFormationButtonAction(ActionEvent actionEvent) {

        Formation newFormation = new Formation(this.candidate_id, "nouveau", "nouveau");
        newFormation.save();
        formations.add(newFormation);

    }

    public void handleRemoveFormationButtonAction(ActionEvent actionEvent) {

        if(!this.formationsTableView.getSelectionModel().isEmpty()) {
            int id = Integer.parseInt(this.formationsTableView.getSelectionModel().getSelectedItem().getId());
            Database.getInstance().execute(new DeleteQuery(Formation.getTable(), new Predicate("id", String.valueOf(id))));
            this.formations.remove(this.formationsTableView.getSelectionModel().getFocusedIndex());
            this.formationsTableView.refresh();
        }
        this.removeFormationButton.setDisable((this.formationsTableView.getSelectionModel().isEmpty()));
    }

    public void handleAddPostulationButtonAction(ActionEvent actionEvent) {

        Postulation newPostulation = new Postulation(this.candidate_id, "nouveau", "nouveau", "nouveau", "nouveau");
        newPostulation.save();
        postulations.add(newPostulation);

    }

    public void handleRemovePostulationButtonAction(ActionEvent actionEvent) {

        if(!this.postulationsTableView.getSelectionModel().isEmpty()) {
            int id = Integer.parseInt(this.postulationsTableView.getSelectionModel().getSelectedItem().getId());
            Database.getInstance().execute(new DeleteQuery(Experience.getTable(), new Predicate("id", String.valueOf(id))));
            this.postulations.remove(this.postulationsTableView.getSelectionModel().getFocusedIndex());
            this.postulationsTableView.refresh();
        }
        this.removeExperienceButton.setDisable((this.experiencesTableView.getSelectionModel().isEmpty()));
    }







}
