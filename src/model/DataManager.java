package model;

import java.util.List;

/**
 * DataManager model interface
 *
 * A data manager is meant to load and save data. <br>
 * It can either load or save players. <br>
 * Each data manager do those operations in its own way (eg. XML, Binary...)
 *
 * @author lturpinat
 */
public interface DataManager {

    /**
     * Load the players according to the data manager's own process
     * @return players loaded
     * @throws DataLoadingException if anything goes wrong while loading data
     */
    List<Player> loadPlayers() throws DataLoadingException;

    /**
     * Save the players according to the data manager's own process
     * @param players players to save
     * @throws DataLoadingException if anything goes wrong while saving data
     */
    void savePlayers(List<Player> players) throws DataLoadingException;
}
