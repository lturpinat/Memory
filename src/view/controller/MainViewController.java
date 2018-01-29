package view.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Board;
import model.Card;
import model.DialogService;
import model.Manager;
import utils.TimeUtils;

import java.util.*;

/**
 * Controller to manage the interactions between the model and the MainView <br>
 *     (the one with the cards)
 *
 * @author vareversat
 */
public class MainViewController implements Controller {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Label errorLabel;
    @FXML
    private Label timeLabel;

    private GridPane playGridPane;

    private Map<Card, ImageView> cardImageViewMap = new HashMap<>();

    private Timer timer;

    private Board board;
    private Manager manager;

    private List<Effect> effects;


    public MainViewController(Manager manager, Board board){
        this.manager = manager;
        this.board = board;

        //Create a new set of effects
        effects = new ArrayList<>();

        //Load them
        loadEffects();
    }

    /**
     * Build the grid fitted with the correct specification </br>
     * wanted on the set up view. Each card is load and display thank to </br>
     * it's own ImageView.
     *
     * @param cardList The list of cards contains into the board
     */
    private void gridCreation(List<Card> cardList, ReadOnlyDoubleProperty heightProperty, ReadOnlyDoubleProperty widthProperty){
        //Incremented each time a card is set properly into the grid
        int cardNumber = 0;

        //Creation of the grid
        playGridPane = new GridPane();

        //Setting of a padding for a more friendly user interface
        playGridPane.setPadding(new Insets(10));

        //Iterate threw the cardList
        for (int i = 0; i < board.getBoardConfiguration().getHeight(); i++) {

            for (int k = 0; k < board.getBoardConfiguration().getWidth(); k++) {
                //Create an ImageView with the front image
                ImageView imageView = new ImageView(board.getFrontCardImage());

                //Bin the width and the height property to adjust the size of card when the window size is modified
                imageView.fitWidthProperty().bind(heightProperty.divide(1*(board.getBoardConfiguration().getHeight()+board.getBoardConfiguration().getWidth())));
                imageView.fitHeightProperty().bind(heightProperty.divide((board.getBoardConfiguration().getHeight()+board.getBoardConfiguration().getWidth())*0.7));

                //Associate the card with his own ImageView
                cardImageViewMap.put(cardList.get(cardNumber), imageView);
                //Associate the ImageView with an event triggered on click
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, clickOnCardEventHandler(cardList.get(cardNumber), imageView));

                //Good layout
                GridPane.setMargin(imageView, new Insets(10));

                //Put the ImageView into the grid
                playGridPane.add(imageView, k, i);

                //Increment the card number
                cardNumber += 1;
            }
        }

        //Center the gridpane in the view
        playGridPane.setAlignment(Pos.CENTER);

        //Attach the gridpane to his parent
        mainBorderPane.setCenter(playGridPane);

        //Set the background image of the board
        mainBorderPane.setBackground(new Background(new BackgroundImage(
                board.getBackgroundImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true,true, false, true))));
    }

    /**
     * Event Handler to manage the click a card. Each card is associated with </br>
     * an ImageView. The process set the correct visible face of each card.
     *
     * @param card clicked card
     * @param imageView image associated to the card
     */
    private EventHandler clickOnCardEventHandler(Card card, ImageView imageView) {
        return event -> {
            //When you click on a card, it's picked by the board
            board.pickCard(card);

            //Call every effects
            effects.forEach(x -> x.trigger(card));

            //Launch a new Thread to not affect the UI
            new Thread(()->
            {
                //The ImageView display the correct face of the card
                imageView.setImage(card.getImageFile());

                //If the array filled of selected cards is empty
                if(board.getSelectedCards().isEmpty()){

                    //The grid can't be click until the process is not finished
                    playGridPane.setDisable(true);

                    //Sleep to let the player see the cards
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Go threw each Card->ImageView Map
                    for (Card cardFromMap : cardImageViewMap.keySet())
                        //If the card is found
                        if(!cardFromMap.isFound())
                            //The image view stay on the correct face of the card
                            cardImageViewMap.get(cardFromMap).setImage(board.getFrontCardImage());

                    //The grid is clickable again
                    playGridPane.setDisable(false);
                }
            }).start();
        };
    }

    /**
     * Update the time elapsed label every second with board's lasted time
     */
    private void updateTimer(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    long seconds = board.getTimeLasted().toMillis();
                    timeLabel.setText(TimeUtils.millisToShortDHMS(seconds));

                    if(!board.isRunning())
                        endTheGame();
                });
            }
        }, 0, 1000);
    }

    /**
     * When the game is ended, timer is stopped and the winning window is displayed
     */
    private void endTheGame(){
        timer.cancel();

        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();

        DialogService.openWinView(manager, board);
    }

    /**
     * Load and initialize effects behaviour into available effects
     */
    private void loadEffects(){
        Effect printCardID = card -> {
            System.out.println("Card id : " + card.getId());
        };

        //effects.add(printCardID);
    }

    @FXML
    private void initialize(){
        //Get the cards from the manager
        List<Card> cardList = board.getCards();

        //Start the game
        board.startGame();

        //Create the timer
        timer = new Timer();

        //Launch the timer
        updateTimer();

        //Bind the label to the errorsCounterProperty with a specified format
        errorLabel.textProperty().bind(javafx.beans.binding.Bindings.format("Error Counter : %d", board.errorsCounterProperty()));

        /*
          Whenever the Stage is brutally closed, cancel the timer so its thread won't block the full stop of the
          application.
         */
        Platform.runLater(() -> {
            Stage currentStage = (Stage) errorLabel.getScene().getWindow();
            currentStage.setOnCloseRequest((event) -> timer.cancel());

            //Create the grid where the cards are displayed with the size properties to adjust their size
            gridCreation(cardList, errorLabel.getScene().heightProperty(), errorLabel.getScene().widthProperty());
        });
    }
}
