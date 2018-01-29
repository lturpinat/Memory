package data_initializers.xml;

import model.BoardConfiguration;
import model.Score;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDate;

/**
 * XMLScore is used for XML serialization by {@link data_initializers.binary.BinaryDataManager} <br>
 * This class is a XML "version" of the {@link Score} class for serialization
 *
 * @author lturpinat
 */

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
class XMLScore {

    private long date;
    private long duration;
    private int height, width;
    private double errorRatio;

    XMLScore(){}

    XMLScore(Score model){
        assert model != null : "The score to convert shouldn't be null";

        date = model.getDate().toEpochDay();
        duration = model.getDuration().toMillis();
        height = model.getBoardConfiguration().getHeight();
        width = model.getBoardConfiguration().getWidth();
        errorRatio = model.getErrorRation();
    }

    /**
     * Convert the XMLScore into a Score usable by the model
     * @return score
     */
    Score getModel(){
        LocalDate date = LocalDate.ofEpochDay(this.date);
        Duration duration = Duration.ofMillis(this.duration);
        return new Score(date, duration, new BoardConfiguration(height, width), errorRatio);
    }

    long getDate() {
        return date;
    }

    void setDate(long date) {
        this.date = date;
    }

    long getDuration() {
        return duration;
    }

    void setDuration(long duration) {
        this.duration = duration;
    }

    int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    double getErrorRatio() {
        return errorRatio;
    }

    void setErrorRatio(double errorRatio) {
        this.errorRatio = errorRatio;
    }
}
