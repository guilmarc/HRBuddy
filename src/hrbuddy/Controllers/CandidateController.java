package hrbuddy.Controllers;

import hrbuddy.Models.Candidate;
import hrbuddy.Utils.Logger;
import javafx.event.ActionEvent;
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
    protected String candidate_id = "-1";
    protected Candidate candidate;

    public Button newButton;
    public Button closeButton;
    public Button saveButton;
    public Button searchButton;
    public AnchorPane container;


    public void saveCandidate(ActionEvent actionEvent) {
        this.candidate_id = "4";
        this.loadCandidate();
    }

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
}
