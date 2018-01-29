package data_initializers.xml;

import model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.Constants.DEFAULT_FILE_NAME;


/**
 * DataManager for loading and saving using XML serialization
 *
 * @author lturpinat
 */
public class XMLDataManager implements DataManager {

    /**
     * Default file extension for saving and loading
     */
    private final String FILE_EXTENSION = "xml";
    private final File file;

    private final static Logger LOGGER = Logger.getLogger(XMLDataManager.class.getName());

    /**
     * Construct a XMLDataManager using a default file for saving binary data <br>
     * The file corresponds to "DEFAULT_FILE_NAME.FILE_EXTENSION" @see {@link Constants}
     */
    public XMLDataManager(){
        file = new File(String.join(".", DEFAULT_FILE_NAME, FILE_EXTENSION)) ;
    }

    /**
     * Construct a XMLDataManager which will load and save using {@code file}
     * @param file file where to load and save binary data
     */
    public XMLDataManager(File file){
        this.file = file;
    }

    /**
     * Load the players using XML deserialization
     * @return players loaded
     * @throws DataLoadingException if anything goes wrong while loading data
     */
    @Override
    public List<Player> loadPlayers() throws DataLoadingException {
        try {

            JAXBContext context = JAXBContext.newInstance(XMLRoot.class);
            Unmarshaller um = context.createUnmarshaller();

            XMLRoot extractedXMLRoot = (XMLRoot) um.unmarshal(file);

            return extractedXMLRoot.getChildrenToModel();

        }
        catch (JAXBException e) {
            if(!(e.getLinkedException() instanceof FileNotFoundException))
                throw new DataLoadingException("Cannot load data from xml data file!", e);

            //In case the file doesn't exist, an empty list of players is returned
            LOGGER.log(Level.WARNING, "Couldn't find " + DEFAULT_FILE_NAME + "." + FILE_EXTENSION + " file. Abort file loading! Database will be populated with empty data.");
            return new ArrayList<>();
        }
    }

    /**
     * Save the players using XML serialization
     * @param players players to save
     * @throws DataLoadingException if anything goes wrong while saving data
     */
    @Override
    public void savePlayers(List<Player> players) throws DataLoadingException {
        try {
            JAXBContext context = JAXBContext.newInstance(XMLRoot.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            XMLRoot root = new XMLRoot();

            players.forEach(player -> root.addChildren(new XMLPlayer(player)));

            m.marshal(root, file);

        } catch (JAXBException e) {
            throw new DataLoadingException("Cannot save data with xml data file!", e);
        }
    }

    @Override
    public String toString(){
        return "XML";
    }
}
