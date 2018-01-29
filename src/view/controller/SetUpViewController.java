package view.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static model.Constants.*;

/**
 * Controller to manage the interactions between the model and the SetUpView <br>
 *     (the one designed to set up the game)
 *
 * @author vareversat
 */
public class SetUpViewController implements Controller {

    @FXML
    private Button returnButton;
    @FXML
    private Button playButton;

    @FXML
    private ChoiceBox<Theme> themeChoiceBox;
    @FXML
    private ChoiceBox<Integer> occurrenceChoiceBox;
    @FXML
    private ChoiceBox<Integer> configHChoiceBox;
    @FXML
    private ChoiceBox<Integer> configLChoiceBox;

    private final Manager manager;

    public SetUpViewController(Manager manager){
        this.manager = manager;
    }

    /**
     * @param event
     * Action when the return button is pressed. Close the current </br>
     * and open the first window.
     */
    @FXML
    private void handleReturnButtonAction(ActionEvent event){
        //Open the first view of the application
        DialogService.openFirstView(manager);

        //Close the current view
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
    }

    /**
     * @param event
     * Actions when the play button is pressed. Get all values from the </br>
     * different ChoiceBoxes, check if the values are matching together then </br>
     * close the current window to display the game window
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent event) {
        //Get the values from the different ChoiceBoxes
        Theme theme = themeChoiceBox.getValue();

        //Parse them to make suitable for exploitation
        int occurrence = Integer.parseInt(occurrenceChoiceBox.getValue().toString());
        int sizeH = Integer.parseInt(configHChoiceBox.getValue().toString());
        int sizeL = Integer.parseInt(configLChoiceBox.getValue().toString());

        //Check if the player chose matching values
        if(checkIfCanPlay(sizeH, sizeL, occurrence)){

            //Create the board with the different values
            Board board = BoardFactory.createBoard(sizeH, sizeL, theme, occurrence);

            //Close the current stage
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.close();

            //Display the game stage
            DialogService.openMainView(manager, board);
        }
        else{
            //If the conditions are haven't been met, display an error message
            DialogService.openPopUpView("You cannot play with this setup !");
        }
    }


    /**
     * @param sizeH Height of the board
     * @param sizeL Width of the board
     * @param occurrence Card's occurrence
     * @return true if the values are matching, false otherwise
     * Private method to check if the number of cards (sizeH*sizeL) </br>
     * is matching with the occurrence.
     */
    private boolean checkIfCanPlay(int sizeH, int sizeL, int occurrence){
        int gridSize = sizeH*sizeL;

        return (gridSize % occurrence) == 0 && occurrence <= gridSize;
    }

    /**
     * @param themeList Themes available from the manager.
     * Load all themes into the theme ChoiceBox.
     */
    private void initializeThemeChoiceBox(List<Theme> themeList){
        themeChoiceBox.setItems(FXCollections.observableArrayList(themeList));

        //Set the default theme
        themeChoiceBox.setValue(themeList.get(0));
    }

    /**
     * Load the occurrence ChoiceBox. Get values from constant class @see {@link model.Constants}
     */
    private void initializeOccurrenceChoiceBox(){
        ArrayList<Integer> occurrenceList = new ArrayList<>();

        for(int k = MINIMAL_CARD_OCCURRENCES,  l = 0; k <= MAX_CARD_OCCURRENCES; k++, l++ )
            occurrenceList.add(l, k);

        occurrenceChoiceBox.setItems(FXCollections.observableArrayList(occurrenceList));

        //Set default value
        occurrenceChoiceBox.getSelectionModel().select(0);
    }

    /**
     * Load the ChoiceBoxes for the height and the width of the board @see {@link model.Constants}
     */
    private void initializeConfigChoicesBoxes(){
        ArrayList<Integer> configList1 = new ArrayList<>();
        ArrayList<Integer> configList2 = new ArrayList<>();

        for(int k = MINIMAL_GRID_HEIGHT_SIZE,  l = 0; k <= MAXIMAL_GRID_HEIGHT_SIZE; k++, l++ )
            configList1.add(l, k);

        for(int k = MINIMAL_GRID_WIDTH_SIZE,  l = 0; k <= MAXIMAL_GRID_WIDTH_SIZE; k++, l++ )
            configList2.add(l, k);

        configHChoiceBox.setItems(FXCollections.observableArrayList(configList1));

        //Set default value
        configHChoiceBox.getSelectionModel().select(0);

        configLChoiceBox.setItems(FXCollections.observableArrayList(configList2));

        //Set default value
        configLChoiceBox.getSelectionModel().select(0);
    }

    @FXML
    private void initialize() {
        //Initialize all the choices boxes
        initializeThemeChoiceBox(manager.getThemes());
        initializeOccurrenceChoiceBox();
        initializeConfigChoicesBoxes();

        //Apply a style sheet to the scene
        Platform.runLater(() -> playButton.getScene().getStylesheets().add("/view/css/Style.css"));
    }
}
