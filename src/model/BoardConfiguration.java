package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.security.InvalidParameterException;

import static model.Constants.*;

/**
 * Contains the dimensions of a board (height, width)
 * @see Board
 *
 * @author lturpinat
 */
public class BoardConfiguration {

    private final IntegerProperty height = new SimpleIntegerProperty();

    private final IntegerProperty width = new SimpleIntegerProperty();

    public BoardConfiguration(int height, int width) {
        setHeight(height);
        setWidth(width);
    }

    //<editor-fold desc="JavaFX Getters/Setters/Properties">
    public int getHeight() { return height.get(); }
    public void setHeight(int height) throws InvalidParameterException {
        if(height <= 0 | height > MAXIMAL_GRID_HEIGHT_SIZE)
            throw new InvalidParameterException("The height of the grid must be : 0 < height <= " + MAXIMAL_GRID_HEIGHT_SIZE +" !");
        this.height.set(height);
    }
    public IntegerProperty heightProperty() {
        return height;
    }

    public int getWidth() {
        return width.get();
    }
    public void setWidth(int width) throws InvalidParameterException {
        if(width <= 0 | width > MAXIMAL_GRID_WIDTH_SIZE)
            throw new InvalidParameterException("The width of the grid must be : 0 < width <= " + MAXIMAL_GRID_WIDTH_SIZE + " !");
        this.width.set(width);
    }
    public IntegerProperty widthProperty() { return width; }

    public int getRequiredCards(){
        return getHeight() * getWidth();
    }
    //</editor-fold>
}
