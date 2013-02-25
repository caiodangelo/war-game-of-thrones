package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.Region;

/**
 * The Own Occupied Continents Feature returns the number of continents which are
 * completely occupied by the actual player divided by the total number os continents.
 * 
 * This feature is not applied in the Placing new Armies and Fortifying the Position
 * phases of the game.

 *
 * @author rodrigo
 */
public class OwnOccupiedContinentsFeature extends Feature {

    public OwnOccupiedContinentsFeature() {
        importance = 4;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double numberPlayerRegions = 0.0;
        for (Region region : gameState.getRegions()) {
            if (region.conqueredByPlayer(player)) {
                numberPlayerRegions++;
            }
        }
        return numberPlayerRegions / gameState.getRegions().length;
    }
}