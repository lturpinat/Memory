package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.URI;
import java.util.List;

/**
 * Theme model object <br>
 *
 * Describe the visual theme of the cards
 *
 * @author lturpinat
 */
public class Theme {

    private final StringProperty name = new SimpleStringProperty();

    private final ObjectProperty<List<URI>> cards = new SimpleObjectProperty<>();

    private final ObjectProperty<URI> hiddenCard = new SimpleObjectProperty<>();

    private final ObjectProperty<URI> background = new SimpleObjectProperty<>();

    public Theme(String name, List<URI> cards, URI hiddenCard, URI background) {
        setName(name);
        setCards(cards);
        setHiddenCard(hiddenCard);
        setBackground(background);
    }

    @Override
    public String toString() { return getName(); }

    //<editor-fold desc="JavaFX Getters/Setters/Properties">
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return name;
    }

    public List<URI> getCards() { return cards.get(); }
    public void setCards(List<URI> cards) {
        this.cards.set(cards);
    }
    public ObjectProperty<List<URI>> cardsProperty() {
        return cards;
    }

    public URI getHiddenCard() {
        return hiddenCard.get();
    }
    public void setHiddenCard(URI hiddenCard) {
        this.hiddenCard.set(hiddenCard);
    }
    public ObjectProperty<URI> hiddenCardProperty() {
        return hiddenCard;
    }

    public URI getBackground() {
        return background.get();
    }
    public void setBackground(URI background) {
        this.background.set(background);
    }
    public ObjectProperty<URI> backgroundProperty() {
        return background;
    }
    //</editor-fold>
}
