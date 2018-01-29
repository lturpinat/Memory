package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Board model object <br>
 *
 * Manage a game
 *
 * @author lturpinat
 */
public class Board {

    private final BoardConfiguration boardConfiguration;
    private final Theme theme;
    private final IntegerProperty errorsCounter = new SimpleIntegerProperty();
    private final int cardsOccurrences;

    private final ObservableList<Card> cardsObs = FXCollections.observableArrayList();
    private final ListProperty<Card> cards = new SimpleListProperty<>(cardsObs);

    private final ObservableList<Card> selectedCardsObs = FXCollections.observableArrayList();
    private final ListProperty<Card> selectedCards = new SimpleListProperty<>(selectedCardsObs);

    private final static Logger LOGGER = Logger.getLogger(Board.class.getName());

    private final Image frontCardImage, backgroundImage;

    private boolean running;

    private long startTime = 0L, endTime = 0L;

    /**
     * Board constructor
     * @param sizeH high of the board
     * @param sizeW width of the board
     * @param theme theme for the cards
     * @param occurrence occurrences of cards
     */
    public Board(int sizeH, int sizeW, Theme theme, int occurrence) {
        boardConfiguration = new BoardConfiguration(sizeH, sizeW);
        this.theme = theme;

        frontCardImage = new Image(theme.getHiddenCard().toString());
        backgroundImage = new Image(theme.getBackground().toString());

        setErrorsCounter(0);
        cardsOccurrences = occurrence;
    }

    /**
     * Start the game
     */
    public void startGame(){
        if(isRunning())
            return;

        //If the generation hasn't been called before startGame()
        if(getCards().isEmpty())
            buildCards();

        running = true;

        startTime = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "Game started!");
    }


    /**
     * Stop the game
     */
    private void stopGame(){
        running = false;
        endTime = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "Game stopped!");
    }

    /**
     * Generate the cards randomly according to the number of occurrences and the theme selected <br/>
     * Note : buildCards() can manually be called in order to generate cards a little bit before starting the game
     */
    private void buildCards() {
        LOGGER.log(Level.INFO, "Building cards...");

        for (int i = 0 ; i < (getBoardConfiguration().getRequiredCards())/getCardsOccurrences() ; i++) {
            Image image = new Image(theme.getCards().get(i).toString());

            for(int l = 0 ; l < getCardsOccurrences() ; l++)
                getCards().add(new Card(i, image));
        }

        Collections.shuffle(getCards());

        LOGGER.log(Level.INFO, "Built!");
    }

    /**
     * Acknowledge that the player has selected a new card
     * @param card the card the player clicked on
     */
    public void pickCard(Card card){
        if(!getCards().contains(card) || card.isFound())
            return;

        //If the same card is selected
        if(getSelectedCards().stream().anyMatch(x -> x.getUuid().equals(card.getUuid())))
            return;

        LOGGER.log(Level.INFO, "Card picked : " + card.getId());

        getSelectedCards().add(card);

        //All the cards are selected but the combination is wrong
        if(getSelectedCards().size() == getCardsOccurrences() && !checkCardsCombination()) {
            increaseErrorsCounterByOne();
            clearSelection();
        }

        //All the cards are selected and the combination is right
        if(getSelectedCards().size() == getCardsOccurrences() && checkCardsCombination()) {
            clearSelection();
            card.setFound(true);

            //Set all cards that has the same id that the picked up card to found
            getCards().stream().filter(x -> x.getId() == card.getId()).forEach(x -> x.setFound(true));

            if (isGameCompleted())
                stopGame();
        }
    }

    /**
     * Check if the game is completed (ie. all the cards have been discovered)
     * @return true if game is completed
     */
    private boolean isGameCompleted(){
        return getCards().stream().allMatch(Card::isFound);
    }

    /**
     * Check if the combination of cards is correct <br/>
     * A combination is considered successful if all the cards selected are the same (ie. have the same id)
     * @see Card
     * @return true if the combination if successful
     */
    private boolean checkCardsCombination(){
        if(getSelectedCards().isEmpty())
            return false;

        int firstId = getSelectedCards().get(0).getId();

        return getSelectedCards().stream().allMatch(x -> x.getId() == firstId);
    }

    /**
     * Reset the cards the player has clicked on
     */
    private void clearSelection(){
        selectedCards.clear();
    }


    //<editor-fold desc="JavaFX Getters/Setters/Properties">

    /**
     * Get the time elapsed since the game started
     * @return time elapsed
     */
    public Duration getTimeLasted() {
        return Duration.ofMillis((isRunning() ? System.currentTimeMillis() : endTime) - startTime);
    }

    /**
     * Return the score the player made during the game
     * @return the score or null if the party hasn't started/ended yet
     */
    public Score getScore(){
        if(isRunning())
            return null;

        //Calculation used : nbCards/nbErrors * 1/timeLasted * 1/cardOccurrences
        //double errorRatio = ((getCards().size() / (getErrorsCounter()+1)) / getTimeLasted().getSeconds()) /getCardsOccurrences();
        double errorRatio = (getCards().size()*getCardsOccurrences()*getErrorsCounter())/getTimeLasted().getSeconds();
        Score score = new Score(LocalDate.now(), getTimeLasted(), getBoardConfiguration(), errorRatio);

        return score;
    }

    /**
     * Get the board configuration of the board (height and width of the grid)
     * @return the board configuration of grid
     */
    public BoardConfiguration getBoardConfiguration() {
        return boardConfiguration;
    }

    /**
     * Get the number of errors the player made during the game
     * @return number of errors
     */
    public int getErrorsCounter() { return errorsCounter.get(); }
    private void setErrorsCounter(int errorsCounter) {
        this.errorsCounter.set(errorsCounter);
    }
    private void increaseErrorsCounterByOne() { setErrorsCounter(getErrorsCounter() + 1); }
    public IntegerProperty errorsCounterProperty() {
        return errorsCounter;
    }

    /**
     * Get the occurrences of cards there will be in the grid
     * @return cards occurrences
     */
    public int getCardsOccurrences() {
        return cardsOccurrences;
    }

    /**
     * Get the cards that are currently selected by the user
     * @return cards selected by the user
     */
    public ObservableList<Card> getSelectedCards() {
        return selectedCards.get();
    }
    private void setSelectedCards(ObservableList<Card> selectedCards) {
        this.selectedCards.set(selectedCards);
    }
    public ListProperty<Card> selectedCardsProperty() {
        return selectedCards;
    }

    /**
     * Get the cards used in the grid
     * @return cards of the board
     */
    public ObservableList<Card> getCards() {
        return cards.get();
    }
    private void setCards(ObservableList<Card> cards) {
        this.cards.set(cards);
    }
    public ListProperty<Card> cardsProperty() {
        return cards;
    }

    /**
     * Get the hidden face of the cards that is proper to the selected {@link Theme}
     * @return hidden face card image
     */
    public Image getFrontCardImage(){
        return frontCardImage;
    }

    /**
     * Get the background image of the grid that is proper to the selected {@link Theme}
     * @return background image
     */
    public Image getBackgroundImage(){
        return backgroundImage;
    }

    /**
     * Whether or not the game is running
     * @return true if running
     */
    public boolean isRunning() {
        return running;
    }
    //</editor-fold>
}
