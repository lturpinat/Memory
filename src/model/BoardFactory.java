package model;

/**
 * BoardFactory <br>
 * Create a Board from scratch
 *
 * @author lturpinat
 */
public class BoardFactory {

    /**
     * Create a new Board to play with
     * @param sizeH high of the board
     * @param sizeW width of the board
     * @param theme theme for the cards
     * @param occurrence occurrences of cards
     * @return the created board
     */
    public static Board createBoard(int sizeH, int sizeW, Theme theme, int occurrence){
        return new Board(sizeH, sizeW, theme, occurrence);
    }
}
