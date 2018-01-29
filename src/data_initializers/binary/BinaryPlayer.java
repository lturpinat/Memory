package data_initializers.binary;

import javafx.collections.FXCollections;
import model.Player;
import model.Score;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Binary "version" of the {@link Player} class for serialization
 *
 * @author lturpinat
 */
class BinaryPlayer implements Serializable {

    private String name;
    private List<BinaryScore> scores;

    BinaryPlayer(Player model){
        assert model != null : "The player to convert shouldn't be null";

        scores = new ArrayList<>();
        name = model.getName();
        model.getScores().forEach(score -> scores.add(new BinaryScore(score)));
    }

    void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        name = (String) ois.readObject();
        scores = (List<BinaryScore>) ois.readObject();
    }

    void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(name);
        oos.writeObject(scores);
    }

    /**
     * Convert the BinaryPlayer into a Player usable by the model
     * @return player
     */
    Player getModel(){
        List<Score> convertedScores = new ArrayList<>();
        scores.forEach(x -> convertedScores.add(x.getModel()));

        return new Player(name, FXCollections.observableArrayList(convertedScores));
    }
}
