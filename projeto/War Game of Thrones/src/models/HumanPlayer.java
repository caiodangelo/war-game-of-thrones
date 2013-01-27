package models;

/**
 *
 * @author rodrigo
 */
public class HumanPlayer extends Player {

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