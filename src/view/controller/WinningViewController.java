package view.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.*;

/**
 * Controller to manage the interactions between the model and the WinningView <br>
 *     (the one displayed at the end of the game)
 *
 * @author vareversat
 */
public class WinningViewController implements Controller {

    @FXML
    private Button mainMenuButton;
    @FXML
    private TextField pseudoTextField;
    @FXML
    private Button quitButton;
    @FXML
    private FlowPane saveMethodFlowPane;
    @FXML
    private ChoiceBox<DataManager> dataManagerChoiceBox;

    private final Manager manager;
    private final Board board;

    public WinningViewController(Manager manager, Board board){
        this.manager = manager;
        this.board = board;
    }

    /**
     * @param event Not used in this case
     * Action when the leader board button is pressed. Save the player with the selected way of saving </br>
     * then go to the main menu
     */
    @FXML
    private void handleMainMenuButtonAction(ActionEvent event){
        //Save the player with the selected way of saving
        savePlayer(dataManagerChoiceBox.getValue());

        //Close the current view and display the main menu
        DialogService.openFirstView(manager);

        //PopUp to notify the player that his game is saved
        DialogService.openPopUpView("Your game have been correctly saved !");

        //Close the current stage
        Stage stage = (Stage) mainMenuButton.getScene().getWindow();
        stage.close();
    }

    /**
     * @param dataManager Which datamanager will be used to save the data
     * This method is called when you click on the serialization you chosen
     */

    private void savePlayer(DataManager dataManager){
        //Create a new score with the player
        Score score = board.getScore();

        //Set the way to save and load the data
        manager.setDataManager(dataManager);

        //Load all players
        manager.loadPlayers();

        //Verify if the player exist or not. Them put him into the List
        manager.createOrUpdatePlayer(pseudoTextField.getText(), score);

        //Write the player into the correct type of file
        manager.savePlayers();
    }

    /**
     * @param event Not used in this case
     * Action when the quit button is pressed. Close current </br>
     * then exit
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent event){
        //Open the first view
        DialogService.openFirstView(manager);

        //Close the current stage
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize(){
        //Get the available way of save and load data and put them into the choice box
        dataManagerChoiceBox.setItems(FXCollections.observableArrayList(Constants.AVAILABLE_DATA_INITIALIZERS));

        //Set the default value
        dataManagerChoiceBox.getSelectionModel().select(0);

        //If the pseudoTextFiled is empty, you can only click on the quit button.
        //But when it is not empty, you can only click on the main menu button.
        mainMenuButton.disableProperty().bind(Bindings.isEmpty(pseudoTextField.textProperty()));
        quitButton.disableProperty().bind(Bindings.isNotEmpty(pseudoTextField.textProperty()));
        saveMethodFlowPane.visibleProperty().bind(Bindings.isNotEmpty(pseudoTextField.textProperty()));

        //Apply a style sheet to the scene
        Platform.runLater(() -> quitButton.getScene().getStylesheets().add("/view/css/Style.css"));
    }
}
