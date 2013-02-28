package models;

/**
 *
 * @author rodrigo
 */
public class AIPlayer extends Player {

    protected Difficulty difficulty;
    protected Region targetRegion;

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Region getTargetRegion() {
        return targetRegion;
    }

    public void setTargetRegion(Region targetRegion) {
        this.targetRegion = targetRegion;
    }
}