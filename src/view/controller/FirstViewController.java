package view.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.DialogService;
import model.Manager;

/**
 * Controller to manage the interactions between the model and the FirstView
 *
 * @author vareversat
 */
public class FirstViewController implements Controller{

    @FXML
    private Button leaderBoardButton;
    @FXML
    private Button playButton;

    private final Manager manager;

    public FirstViewController(Manager manager){
        this.manager = manager;
    }

    /**
     * @param event Not used in this case
     * Action when the play button is pressed. Close current </br>
     * then open the set up view
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent event) {
        //Open the set up view
        DialogService.openSetUpView(manager);

        //Close the current stage
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();
    }

    /**
     * @param event Not used in this case
     * Action when the leader board button is pressed. Close current </br>
     * then open the leader board view
     */
    @FXML
    private void handleLeaderBoardButtonAction(ActionEvent event){
        //Open the leader board view
        DialogService.openLeaderBoardView(manager);

        //Close the current stage
        Stage stage = (Stage) leaderBoardButton.getScene().getWindow();
        stage.close();
    }

    /**
     * @param event Not used in this case
     * Action when the quit button is pressed. Close current </br>
     * then exit
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent event){
        //Quit the application
        Platform.exit();
    }

    @FXML
    private void initialize(){
        //Apply a style sheet to the scene
        //We use a "Platform.runLater()" because we need to wait that the playButton is loaded into the scene
        Platform.runLater(() -> playButton.getScene().getStylesheets().add("/view/css/Style.css"));
    }


}
