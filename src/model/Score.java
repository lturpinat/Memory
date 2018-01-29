package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Score model object <br>
 *
 * Describe a score made during a game
 *
 * @author lturpinat
 */
public class Score {

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    private final ObjectProperty<Duration> duration = new SimpleObjectProperty<>();

    private final ObjectProperty<BoardConfiguration> boardConfiguration = new SimpleObjectProperty<>();

    private final DoubleProperty errorRatio = new SimpleDoubleProperty();

    public Score(LocalDate date, Duration duration, BoardConfiguration boardConfiguration, double errorRatio) {
        setDate(date);
        setDuration(duration);
        setBoardConfiguration(boardConfiguration);
        setErrorRatio(errorRatio);
    }

    //<editor-fold desc="JavaFX Getters/Setters/Properties">
    public LocalDate getDate() { return date.get(); }
    public void setDate(LocalDate date) { this.date.set(date); }
    public ObjectProperty<LocalDate> dateProperty() { return date; }


    public BoardConfiguration getBoardConfiguration() { return boardConfiguration.get(); }
    public void setBoardConfiguration(BoardConfiguration boardConfiguration) {
        this.boardConfiguration.set(boardConfiguration);
    }

    public Duration getDuration() { return duration.get(); }
    public void setDuration(Duration duration) { this.duration.set(duration); }
    public ObjectProperty<Duration> durationProperty() { return duration; }


    public double getErrorRation() { return errorRatio.get(); }
    public void setErrorRatio(double errorRatio) { this.errorRatio.set(errorRatio); }
    public DoubleProperty errorRatioProperty() { return errorRatio; }
    //</editor-fold>
}
