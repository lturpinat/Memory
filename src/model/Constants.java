package model;

import data_initializers.binary.BinaryDataManager;
import data_initializers.xml.XMLDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Constants <br>
 *
 * Contains the key constants shared in the application
 *
 * @author lturpinat
 */
public final class Constants {
    private Constants() {}

    /**
     * Maximal height of the grid
     */
    public static final int MAXIMAL_GRID_HEIGHT_SIZE = 5;

    /**
     * Maximal width of the grid
     */
    public static final int MAXIMAL_GRID_WIDTH_SIZE = 5;

    /**
     * Minimal height of the grid
     */
    public static final int MINIMAL_GRID_HEIGHT_SIZE = 2;

    /**
     * Minimal width of the grid
     */
    public static final int MINIMAL_GRID_WIDTH_SIZE = 2;

    /**
     * Maximal number of cards in the "maximal" grid configuration
     */
    private static final int TOTAL_CARDS = MAXIMAL_GRID_HEIGHT_SIZE * MAXIMAL_GRID_WIDTH_SIZE;

    /**
     * Minimal number of occurrences of card
     */
    public static final int MINIMAL_CARD_OCCURRENCES = 2;

    /**
     * Maximal number of occurrences of card
     */
    public static final int MAX_CARD_OCCURRENCES = TOTAL_CARDS/2;

    /**
     * Default height dimension of the grid in the game configuration window
     */
    public static int DEFAULT_HEIGHT = 4;

    /**
     * Default width dimension of the grid in the game configuration window
     */
    public static final int DEFAULT_WIDTH = 4;

    /**
     * Minimal number of cards required to create the largest grid available
     */
    public static final int MINIMAL_DECK_SIZE = Math.floorMod(TOTAL_CARDS, 2) == 1 ? (TOTAL_CARDS+1)/2 : TOTAL_CARDS/2;

    /**
     * Default file name for saving/loading data
     */
    public static final String DEFAULT_FILE_NAME = "players";

    /**
     * Default local folder where to look for themes
     */
    public static final String DEFAULT_RESOURCES_FOLDER = "/resources";

    /**
     * Supported image file extensions for theme loading <br>
     * Note : each image type must be separated by a "|"
     */
    public static final String AVAILABLE_IMAGE_EXTENSIONS = "png|jpg";

    /**
     * Regex expression used to find any file which have one of the extensions of {@code AVAILABLE_IMAGE_EXTENSIONS}
     */
    public static final String IMAGE_REGEX_STRING = "([^\\s]+(\\.(?i)(" + AVAILABLE_IMAGE_EXTENSIONS + "))$)";

    /**
     * Name of the image file of the background in a theme folder
     */
    public static final String IMAGE_BACKGROUND_FILENAME = "-1";

    /**
     * Name of the hidden face image file of cards in a theme folder
     */
    public static final String HIDDEN_CARD_FILENAME = "0";

    /**
     * List all the available data managers
     */
    public static final List<DataManager> AVAILABLE_DATA_INITIALIZERS = new ArrayList<DataManager>(){{
        add(new BinaryDataManager());
        add(new XMLDataManager());
    }};
}
