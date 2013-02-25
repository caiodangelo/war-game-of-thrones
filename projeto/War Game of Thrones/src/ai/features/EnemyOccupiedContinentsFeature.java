package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.Region;

/**
 * The Enemy Occupied Continents Feature returns the number of continents which are
 * completely occupied by enemy players divided by the total number of continents.
 * 
 * This feature is not applied in the Placing new Armies and Fortifying the Position
 * phases of the game.
 *
 * @author rodrigo
 */
public class EnemyOccupiedContinentsFeature extends Feature {

    public EnemyOccupiedContinentsFeature() {
        importance = 3;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double occupiedRegions = 0.0;
        for (Region region : gameState.getRegions()) {
            if (region.getOwner() != null && region.getOwner() != player) {
                occupiedRegions++;
            }
        }
        return occupiedRegions / gameState.getRegions().length;
    }
}