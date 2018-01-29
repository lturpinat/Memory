package view.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to manage the interactions between the model and the LeaderBoardView <br>
 *     (the one which display all scores)
 *
 * @author vareversat
 */
public class LeaderBoardViewController implements Controller {

    @FXML
    private Button backButton;
    @FXML
    private TableView<ScoresOfPlayer> leaderBoardTableView;
    @FXML
    private TableColumn<ScoresOfPlayer, String> pseudoColumn;
    @FXML
    private TableColumn<ScoresOfPlayer, LocalDate> dateColumn;
    @FXML
    private TableColumn<ScoresOfPlayer, String> durationColumn;
    @FXML
    private TableColumn<ScoresOfPlayer, Double> errorRatioColumn;
    @FXML
    private TableColumn<ScoresOfPlayer, ?> boardConfigurationColumn;
    @FXML
    private ChoiceBox<DataManager> dataManagerChoiceBox;
    @FXML
    private TableColumn<ScoresOfPlayer, Integer> heightColumn = new TableColumn<>("Height");
    @FXML
    private TableColumn<ScoresOfPlayer, Integer> widthColumn = new TableColumn<>("Width");

    private Manager manager;

    public LeaderBoardViewController(Manager manager){
        this.manager = manager;
    }

    /**
     * @param event Not used in this case
     * Action when the play button is pressed. Close current </br>
     * then open the first view
     */
    @FXML
    private void handleReturnButtonAction(ActionEvent event) {
        //Open the first view of the application
        DialogService.openFirstView(manager);

        //Close the current stage
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Create and set all value factory to properly display all scores and players
     */
    private void setCellValueFactory(){
        pseudoColumn.setCellValueFactory( cellData -> cellData.getValue().playerNameProperty());
        dateColumn.setCellValueFactory( cellData -> cellData.getValue().scoreDateProperty());
        durationColumn.setCellValueFactory( cellData -> cellData.getValue().scoreDurationProperty());
        errorRatioColumn.setCellValueFactory( cellData -> cellData.getValue().scoreErrorRatioProperty());
        heightColumn.setCellValueFactory( cellData -> cellData.getValue().boardConfigHeightProperty());
        widthColumn.setCellValueFactory( cellData -> cellData.getValue().boardConfigWidthProperty());
    }

    /**
     * @param dataManager Chosen data type
     * Get the values from the specified type of file
     * @return An observable List of the data
     */
    private List<ScoresOfPlayer> getFromDataManager(DataManager dataManager){
        //Create an empty List. It will contains all score of players created
        List<ScoresOfPlayer> scoresOfPlayers = new ArrayList<>();

        //Set the datamanager withe the datamanger pass as argument
        manager.setDataManager(dataManager);

        //Load players
        manager.loadPlayers();

        //Get players from the specified file
        List<Player> playerList = manager.getPlayers();

        //A player can have multiple scores. So to correctly display them, for each score for each
        //player, we create an unique "ScoreOfPlayer"
        playerList.forEach(p -> p.getScores().forEach(s -> scoresOfPlayers.add(new ScoresOfPlayer(s, p))));

        return scoresOfPlayers;
    }

    /**
     * @param scoresOfPlayerList List with the elements to display
     * Clear the tableview then set the elements with those pass as argument
     */
    private void displayScoresOfPlayer(List<ScoresOfPlayer> scoresOfPlayerList){
        //Clear the table view
        leaderBoardTableView.getItems().clear();

        //Add the elements into the table view
        leaderBoardTableView.setItems(FXCollections.observableList(scoresOfPlayerList));
    }

    @FXML
    private void initialize() {
        //Create two columns within a the board configuration column
        boardConfigurationColumn.getColumns().setAll(heightColumn, widthColumn);

        //Set the cell factory
        setCellValueFactory();

        //Get the available way of save and load data and put them into the choice box
        dataManagerChoiceBox.setItems(FXCollections.observableArrayList(Constants.AVAILABLE_DATA_INITIALIZERS));

        //Set default value
        dataManagerChoiceBox.getSelectionModel().select(0);
        displayScoresOfPlayer(getFromDataManager(dataManagerChoiceBox.getItems().get(0)));

        //Create a tooltip for more fun
        dataManagerChoiceBox.setTooltip(new Tooltip("Select a data type"));

        //Add a listener to display values when we click on a data type
        dataManagerChoiceBox
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener((observableValue, oldIndexValue, newIndexValue) -> {
                    //Get the correct DataManager with the new index selected
                    displayScoresOfPlayer(getFromDataManager(dataManagerChoiceBox.getItems().get((Integer) newIndexValue)));
                });
    }

}