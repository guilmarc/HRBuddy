package hrbuddy.Controllers;

import hrbuddy.Models.Candidate;
import hrbuddy.Utils.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class CandidateController implements Initializable, ControlledScreen{
    public ComboBox genderComboBox;
    public TextField firstnameTextField;
    public TextField lastnameTextField;
    public TextField homephoneTextField;
    public TextField mobilephoneTextField;
    public TextField emailTextField;
    public TextField addressTextField;
    public TextField idTextField;
    protected String candidate_id = "-1";
    protected Candidate candidate;
    protected boolean editionMode = false;
    protected MainController parent;


    public Button newButton;
    public Button saveButton;
    public Button searchButton;
    public AnchorPane container;

    ObservableList<String> genderComboBoxValues =
            FXCollections.observableArrayList(
                    "-",
                    "Homme",
                    "Femme"
            );

    public void setScreenParent(MainController parent){
        this.parent = parent;
    }
    public void loadRecord(int id){
        this.candidate_id = String.valueOf(id);
        this.loadCandidate();
    }

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

    public void initialize(URL url, ResourceBundle bundle){
        this.initEvents();
        this.initListValues();
    }

    private void initListValues() {
        this.genderComboBox.setValue(this.genderComboBoxValues);
    }

    public void newCandidate(ActionEvent actionEvent) {
        this.candidate_id = "-1";
        this.clearForm();
    }

    public void clearForm(){
        this.firstnameTextField.setText("");
        this.lastnameTextField.setText("");
        this.emailTextField.setText("");
        this.homephoneTextField.setText("");
        this.mobilephoneTextField.setText("");
        this.idTextField.setText("");
    }

    public void fillForm(){
        if(this.candidate != null){
            this.firstnameTextField.setText(this.candidate.getFirstname());
            this.lastnameTextField.setText(this.candidate.getLastname());
            this.emailTextField.setText(this.candidate.getEmail());
            this.homephoneTextField.setText(this.candidate.getHomePhone());
            this.mobilephoneTextField.setText(this.candidate.getCellPhone());
            this.idTextField.setText(this.candidate.getId());
        }
        else{
            Logger.debug("Couldn't load id : "+this.candidate_id);
        }
    }

    public void loadCandidate(){
        int id = Integer.parseInt(this.candidate_id);
        if(id > 0) {
            this.candidate = Candidate.find(id);
            this.fillForm();
            Logger.debug(String.valueOf(id));
        }
    }


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

    public void eventTriggered(){
        this.saveButton.setDisable(!this.editionMode);
        this.newButton.setDisable(this.editionMode);
    }

    public void onInputValueChanged(Event event) {
        this.editionMode = true;
        eventTriggered();
    }
}
