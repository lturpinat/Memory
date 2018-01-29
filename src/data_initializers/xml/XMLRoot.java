package data_initializers.xml;

import model.Player;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * XMLRoot is used for XML serialization by {@link data_initializers.binary.BinaryDataManager} <br>
 * This class create contains the {@link XMLPlayer} so that they can be serialized through one "parent" object
 *
 * @author lturpinat
 */

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
class XMLRoot {

    @XmlElement(name="player")
    private List<XMLPlayer> children;

    XMLRoot(){
        children = new ArrayList<>();
    }

    void addChildren(XMLPlayer children){
        this.children.add(children);
    }

    List<Player> getChildrenToModel(){
        List<Player> players = new ArrayList<>();

        children.forEach(children -> players.add(children.getModel()));

        return players;
    }

    List<XMLPlayer> getChildren() {
        return children;
    }

    void setChildren(List<XMLPlayer> children) {
        this.children = children;
    }
}
