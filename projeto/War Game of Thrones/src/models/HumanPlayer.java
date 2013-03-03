package models;

import java.io.Serializable;

/**
 *
 * @author rodrigo
 */
public class HumanPlayer extends Player implements Serializable {

    public HumanPlayer(String name) {
        super(name);
    }

    public HumanPlayer(String name, House house) {
        super(name, house);
    }

    @Override
    public boolean isAIPlayer() {
        return false;
    }

}