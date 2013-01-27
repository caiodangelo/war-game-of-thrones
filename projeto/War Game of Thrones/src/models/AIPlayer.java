package models;

/**
 *
 * @author rodrigo
 */
public class AIPlayer extends Player {

    Difficulty difficulty;
    
    public AIPlayer(String name) {
        super(name);
    }

    public AIPlayer(String name, House house) {
        super(name, house);
    }

    @Override
    public boolean isAIPlayer() {
        return true;
    }
}