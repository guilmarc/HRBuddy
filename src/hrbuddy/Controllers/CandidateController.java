package hrbuddy.Controllers;

import hrbuddy.Models.Candidate;
import hrbuddy.Utils.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by nboisvert on 2016-04-12.
 */
public class CandidateController {
    public ComboBox titleComboBox;
    public TextField firstnameTextField;
    public TextField lastnameTextField;
    public TextField homephoneTextField;
    public TextField mobilephoneTextField;
    public TextField emailTextField;
    public TextField addressTextField;
    protected String candidate_id = "-1";
    protected Candidate candidate;
    protected boolean editionMode = false;

    public Button newButton;
    public Button closeButton;
    public Button saveButton;
    public Button searchButton;
    public AnchorPane container;

    public void closeWindow(ActionEvent actionEvent) {
    }
    public void newCandidate(ActionEvent actionEvent) {
    }

    public void fillForm(){
        if(this.candidate != null){
            this.firstnameTextField.setText(this.candidate.getFirstname());
            this.lastnameTextField.setText(this.candidate.getLastname());
            this.emailTextField.setText(this.candidate.getEmail());
            this.homephoneTextField.setText(this.candidate.getHomePhone());
            this.mobilephoneTextField.setText(this.candidate.getCellPhone());
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
        Candidate candidate = new Candidate(
                this.firstnameTextField.getText(),
                this.lastnameTextField.getText(),
                this.addressTextField.getText(),
                this.emailTextField.getText(),
                this.homephoneTextField.getText(),
                this.mobilephoneTextField.getText()
        );
        candidate.save();
        this.editionMode = false;
    }

    public void eventTriggered(){
        this.saveButton.setDisable(!this.editionMode);
        this.newButton.setDisable(this.editionMode);
    }

    public void onInputValueChanged(Event event) {
        Logger.debug("Triggered");
        this.editionMode = true;
        eventTriggered();
    }
}
