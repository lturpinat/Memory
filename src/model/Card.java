package model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.UUID;

/**
 * Card model object
 *
 * @author lturpinat
 */
public class Card {

    private final IntegerProperty id = new SimpleIntegerProperty();

    private final ObjectProperty<Image> imageFile = new SimpleObjectProperty<>();

    private final BooleanProperty isFound = new SimpleBooleanProperty();

    private final ObjectProperty<UUID> uuid = new SimpleObjectProperty<>();

    public Card(int id, Image imageFile) {
        setId(id);
        setImageFile(imageFile);
        setUuid(UUID.randomUUID());
    }

    //<editor-fold desc="JavaFX Getters/Setters/Properties">

    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public IntegerProperty idProperty() {
        return id;
    }


    public Image getImageFile() {
        return imageFile.get();
    }
    public void setImageFile(Image imageFile) {
        this.imageFile.set(imageFile);
    }
    public ObjectProperty<Image> imageFileProperty() {
        return imageFile;
    }


    public boolean isFound() {
        return isFound.get();
    }
    public BooleanProperty isFoundProperty() {
        return isFound;
    }
    public void setFound(boolean isFound) {
        this.isFound.set(isFound);
    }

    public UUID getUuid() {
        return uuid.get();
    }
    public ObjectProperty<UUID> uuidProperty() {
        return uuid;
    }
    private void setUuid(UUID uuid) {
        this.uuid.set(uuid);
    }
    //</editor-folder>
}
