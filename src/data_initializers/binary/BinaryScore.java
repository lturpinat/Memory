package data_initializers.binary;

import model.BoardConfiguration;
import model.Score;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Binary "version" of the {@link Score} class for serialization
 *
 * @author lturpinat
 */
class BinaryScore implements Serializable {

    private LocalDate date;
    private Duration duration;
    private int height, width;
    private double errorRatio;

    BinaryScore(Score model){
        assert model != null : "The score to convert shouldn't be null";

        date = model.getDate();
        duration = model.getDuration();
        height = model.getBoardConfiguration().getHeight();
        width = model.getBoardConfiguration().getWidth();
        errorRatio = model.getErrorRation();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        date = (LocalDate) ois.readObject();
        duration = (Duration) ois.readObject();
        height = (int) ois.readObject();
        width = (int) ois.readObject();
        errorRatio = (double) ois.readObject();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(date);
        oos.writeObject(duration);
        oos.writeObject(height);
        oos.writeObject(width);
        oos.writeObject(errorRatio);
    }

    /**
     * Convert the BinaryScore into a Score usable by the model
     * @return score
     */
    Score getModel(){
        return new Score(date, duration, new BoardConfiguration(height, width), errorRatio);
    }
}
