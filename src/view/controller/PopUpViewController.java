package view.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller to manage the interactions between the model and the PopUpView <br>
 *     (the one which display messages)
 *
 * @author vareversat
 */
public class PopUpViewController implements Controller{

    @FXML
    private Button closeButton;

    @FXML
    private Label errorLabel;

    private String msg;

    public PopUpViewController(String msg) {
        this.msg = msg;
    }

    /**
     * @param event Not used in this case
     * Action when the leader board button is pressed. Close current </br>
     * then open the leader board view
     */
    @FXML
    private void handleCloseButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize(){
        //Set the message displayed into the popup
        errorLabel.setText(msg);

        //Set a style sheet the scene
        Platform.runLater(() -> errorLabel.getScene().getStylesheets().add("/view/css/Style.css"));
    }
}
