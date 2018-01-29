package model;

import utils.ResourceFinder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Constants.DEFAULT_RESOURCES_FOLDER;

/**
 * {@link Manager} is meant to manage every main features of the game, which are : <br>
 * - Players' scores <br>
 * - Data's redundancy <br>
 * - Providing the {@link Theme}s produced and analysed by {@link ResourceFinder} <br>
 *
 * @author lturpinat
 */
public class Manager {

    private List<Player> players;

    private List<Theme> themes;

    private DataManager dataManager;

    private final static Logger LOGGER = Logger.getLogger(Manager.class.getName());

    public Manager() {
        //Initialize players with empty data
        players = new ArrayList<>();

        try {
            LOGGER.log(Level.INFO, "Loading themes from " + DEFAULT_RESOURCES_FOLDER + "...");

            //Load default themes
            themes = ResourceFinder.getAvailableThemes(getClass().getResource(DEFAULT_RESOURCES_FOLDER).toURI());

            LOGGER.log(Level.INFO, "Loaded !");

            assert themes != null : "The themes list shouldn't be empty!";
        } catch (URISyntaxException | NullPointerException e) {
            //As those themes are default, there shouldn't be any loading issue
            throw new RuntimeException("Should never go there!");
        }
    }

    /**
     * Load players using current data manager's system <br>
     * Note : if the data manager isn't set, empty data will be loaded
     */
    public void loadPlayers(){
        try {
            //If not data manager is set, do nothing
            if(dataManager == null)
                return;

            players = dataManager.loadPlayers();

        } catch (DataLoadingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save players using current data manager's system <br>
     * Note : if the data manager isn't set, no data will be save
     */
    public void savePlayers(){
        try {
            //If not data manager is set, do nothing
            if(dataManager == null)
                return;

            dataManager.savePlayers(getPlayers());
        } catch (DataLoadingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chose which data manager will be used to enable data's redundancy
     * @param dataManager the data manager to use
     */
    public void setDataManager(DataManager dataManager){
        this.dataManager = dataManager;
    }

    /**
     * Load additional themes in the Manager from a folder
     * @param resourceDirectoryPath the folder containing the themes
     * @return true if the themes could be loaded and added to the Manager
     */
    public boolean loadAdditionalThemes(URI resourceDirectoryPath) {
        assert resourceDirectoryPath != null : "resourceDirectoryPath cannot be null!";

        List<Theme> themes = ResourceFinder.getAvailableThemes(resourceDirectoryPath);

        return themes != null && this.themes.addAll(themes);
    }

    /**
     * Create a new player and record its new score or add it to an existing player
     * @param playerName player who made the score
     * @param score score of the game
     */
    public void createOrUpdatePlayer(String playerName, Score score){
        if(!isPlayerRegistered(playerName)){//The player doesn't exist
            players.add(new Player(playerName){{
                getScores().add(score);
            }});//Create him along with his score

            return;
        }

        //Find the existing player add this score to its prize list
        players.stream().filter(x -> x.getName().equals(playerName))
                .findFirst().orElse(null).getScores().add(score);
    }

    /**
     * Check if a player's name is already registered
     * @param playerName player's name
     * @return true if already registered
     */
    public boolean isPlayerRegistered(String playerName){
        return getPlayers().stream().anyMatch(x -> x.getName().equals(playerName));
    }

    /**
     * Get players
     * @return an unmodifiable list of players (cf. Collections' unmodifiable lists)
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Get loaded themes
     * @return an unmodifiable list of themes (cf. Collections' unmodifiable lists)
     */
    public List<Theme> getThemes() {
        return Collections.unmodifiableList(themes);
    }
}
