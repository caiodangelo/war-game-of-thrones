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
        System.out.println("GET TARGET REGION RETURNING " + targetRegion + " on " + toString());
        return targetRegion;
    }

    public void setTargetRegion(Region targetRegion) {
        System.out.println("SET TARGET REGION TO " + targetRegion + " on " + toString());
        this.targetRegion = targetRegion;
    }
}