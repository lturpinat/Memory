package data_initializers.binary;

import model.Constants;
import model.DataLoadingException;
import model.DataManager;
import model.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.Constants.DEFAULT_FILE_NAME;

/**
 * DataManager for loading and saving using binary serialization
 *
 * @author lturpinat
 */
public class BinaryDataManager implements DataManager {

    /**
     * Default file extension for saving and loading
     */
    private final String FILE_EXTENSION = "bin";
    private final File file;

    private final static Logger LOGGER = Logger.getLogger(BinaryDataManager.class.getName());

    /**
     * Construct a BinaryDataManager using a default file for saving binary data <br>
     * The file corresponds to "DEFAULT_FILE_NAME.FILE_EXTENSION" @see {@link Constants}
     */
    public BinaryDataManager(){
        file = new File(String.join(".", DEFAULT_FILE_NAME, FILE_EXTENSION)) ;
    }

    /**
     * Construct a BinaryDataManager which will load and save using {@code file}
     * @param file file where to load and save binary data
     */
    public BinaryDataManager(File file){
        this.file = file;
    }

    /**
     * Load the players using binary deserialization
     * @return players loaded
     * @throws DataLoadingException if anything goes wrong while loading data
     */
    @Override
    public List<Player> loadPlayers() throws DataLoadingException {
        List<Player> players = new ArrayList<>();

        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))){

            BinaryPlayer binaryPlayer;

            do{
                binaryPlayer = (BinaryPlayer) oos.readObject();

                if(binaryPlayer != null)
                    players.add(binaryPlayer.getModel());
            } while(binaryPlayer != null);

        }
        catch(EOFException ignored){
            //EOFException is ignored as it will be thrown all time : we don't know how much BinaryPlayers there
            //are to read and need to cycle until we hit a null or EOFException.
        }
        catch(FileNotFoundException e){
            LOGGER.log(Level.WARNING, "Couldn't find " + DEFAULT_FILE_NAME + "." + FILE_EXTENSION + " file. Abort file loading! Database will be populated with empty data.");
        }
        catch (IOException | ClassNotFoundException e) {
            throw new DataLoadingException("Cannot load data from binary data file!", e);
        }

        return players;
    }

    /**
     * Save the players using binary serialization
     * @param players players to save
     * @throws DataLoadingException if anything goes wrong while saving data
     */
    @Override
    public void savePlayers(List<Player> players) throws DataLoadingException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){

            for(Player player : players)
                oos.writeObject(new BinaryPlayer(player));

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataLoadingException("Cannot save data with binary data file!", e);
        }
    }

    @Override
    public String toString(){
        return "Binary";
    }
}

