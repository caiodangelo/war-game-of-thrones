package ai.features;

import ai.Feature;
import models.Board;
import models.Player;

/**
 * The Occupied Territories Feature returns the number of territories which are occupied
 * by the actual player in relation to the total number of territories on the map. In the case
 * of the original map, which I used throughout my work, the total number of territories
 * is 42.
 * 
 * The feature result is always in the interval (0, 1]. This feature is not applied in the
 * Placing new Armies and Fortifying the Position phases of the game.
 *
 * @author rodrigo
 */
public class OccupiedTerritoriesFeature extends Feature {

    public OccupiedTerritoriesFeature() {
        importance = 3;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        return player.getTerritories().size() / (gameState.getTerritories().length * 1.0);
    }
}