package models;

import java.io.Serializable;

/**
 *
 * @author rodrigo
 */
public class AIPlayer extends Player implements Serializable {

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
        difficulty.player = this;
    }

    public Region getTargetRegion() {
        return targetRegion;
    }

    public void setTargetRegion(Region targetRegion) {
        this.targetRegion = targetRegion;
    }
}