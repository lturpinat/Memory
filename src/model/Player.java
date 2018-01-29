package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Player model object
 *
 * @author lturpinat
 */
public class Player {

    private final transient StringProperty name = new SimpleStringProperty();
    private final transient ObservableList<Score> scoresObs;
    private final transient ListProperty<Score> scores;

    public Player(String name, ObservableList<Score> observableList){
        setName(name);
        scoresObs = observableList;
        scores = new SimpleListProperty<>(scoresObs);
    }

    public Player(String name) {
        this(name, FXCollections.observableArrayList());
    }

    @Override
    public String toString() {
        return getName();
    }

    //<editor-fold desc="JavaFX Getters/Setters/Properties">
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public ObservableList<Score> getScores() { return scores.get(); }
    public void setScores(ObservableList<Score> scores) { this.scores.set(scores); }
    public ListProperty<Score> scoresProperty() { return scores; }
    //</editor-fold>
}
