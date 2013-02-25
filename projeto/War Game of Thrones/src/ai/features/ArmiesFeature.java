package ai.features;

import ai.Feature;
import models.Board;
import models.Player;

/**
 * The Armies Feature returns the number of armies of the actual player (AP) in relation
 * to the total number of armies on the gameboard.
 *
 * The feature result is always in the interval [0, 1]. This feature is not applied in the
 * Placing new Armies and Fortifying the Position phases of the game.
 *
 * @author rodrigo
 */
public class ArmiesFeature extends Feature {

    public ArmiesFeature() {
        importance = 2;
        scaleFactor = 1;
    }
    
    @Override
    public double calculate(Board gameState, Player player) {
        int totalArmies = 0;
        for (Player otherPlayer : gameState.getPlayers()) {
            totalArmies += otherPlayer.numArmies();
        }
        return player.numArmies() / (totalArmies * 1.0);
    }

}
