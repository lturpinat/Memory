package data_initializers.xml;

import javafx.collections.FXCollections;
import model.Player;
import model.Score;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * XMLPlayer is used for XML serialization by {@link data_initializers.binary.BinaryDataManager} <br>
 * This class is a XML "version" of the {@link Player} class for serialization
 *
 * @author lturpinat
 */

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
class XMLPlayer {

    private String name;

    @XmlElementWrapper(name ="scores")
    @XmlElement(name = "score")
    private List<XMLScore> scores;

    XMLPlayer(){}

    XMLPlayer(Player model){
        assert model != null : "The player to convert shouldn't be null";

        scores = new ArrayList<>();
        name = model.getName();
        model.getScores().forEach(score -> scores.add(new XMLScore(score)));
    }

    /**
     * Convert the XMLPlayer into a Player usable by the model
     * @return player
     */
    Player getModel(){
        List<Score> convertedScores = new ArrayList<>();
        scores.forEach(x -> convertedScores.add(x.getModel()));

        return new Player(name, FXCollections.observableArrayList(convertedScores));
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    List<XMLScore> getScores() {
        return scores;
    }

    void setScores(List<XMLScore> scores) {
        this.scores = scores;
    }
}
