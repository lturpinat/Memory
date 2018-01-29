package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.TimeUtils;

import java.time.LocalDate;

/**
 * ScoresOfPlayer is used to create the data view in the LeaderBoardView
 */
public class ScoresOfPlayer {

    private final StringProperty playerName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> scoreDate = new SimpleObjectProperty<>();
    private final StringProperty scoreDuration = new SimpleStringProperty();
    private final ObjectProperty<Double> scoreErrorRatio = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> boardConfigHeight = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> boardConfigWidth = new SimpleObjectProperty<>();

    public ScoresOfPlayer(Score score, Player player) {
        setPlayerName(player.getName());
        setScoreDate(score.getDate());
        setScoreDuration(TimeUtils.millisToShortDHMS(score.getDuration().toMillis()));
        setScoreErrorRatio(score.getErrorRation());
        setBoardConfigHeight(score.getBoardConfiguration().getHeight());
        setBoardConfigWidth(score.getBoardConfiguration().getWidth());
    }

    //<editor-fold desc="JavaFX Getters/Setters/Properties">

    public StringProperty playerNameProperty(){return playerName;}
    public String getPlayerName(){return playerName.get();}
    public void setPlayerName(String name){this.playerName.set(name);}

    public ObjectProperty<LocalDate> scoreDateProperty() { return scoreDate; }
    public LocalDate getScoreDate() { return scoreDate.get();}
    public void setScoreDate(LocalDate date){this.scoreDate.set(date);}

    public StringProperty scoreDurationProperty() { return scoreDuration; }
    public String getScoreDuration() { return scoreDuration.get();}
    public void setScoreDuration(String duration){this.scoreDuration.set(duration);}

    public ObjectProperty<Double> scoreErrorRatioProperty() { return scoreErrorRatio; }
    public Double getScoreErrorRatio() { return scoreErrorRatio.get();}
    public void setScoreErrorRatio(Double errorRatio){this.scoreErrorRatio.set(errorRatio);}

    public ObjectProperty<Integer> boardConfigHeightProperty() { return boardConfigHeight; }
    public Integer getBoardConfigHeight() { return boardConfigHeight.get();}
    public void setBoardConfigHeight(Integer height){this.boardConfigHeight.set(height);}

    public ObjectProperty<Integer> boardConfigWidthProperty() { return boardConfigWidth; }
    public Integer getBoardConfigWidth() { return boardConfigWidth.get();}
    public void setBoardConfigWidth(Integer width){this.boardConfigWidth.set(width);}
    //</editor-fold>
}
