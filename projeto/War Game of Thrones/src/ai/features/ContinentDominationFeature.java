package ai.features;

import ai.Feature;
import models.AIPlayer;
import models.Board;
import models.Player;

/**
 * The Continent Domination Feature returns a measurement of the relative power of the
 * actual player (AP) on the target continent (TC) multiplied by the rating of the target
 * continent.
 *
 * This feature is not applied in the Placing new Armies and Fortifying the Position phases
 * of the game.
 *
 * @author rodrigo
 */
public class ContinentDominationFeature extends Feature {

    public ContinentDominationFeature() {
        importance = 3;
        scaleFactor = 3;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        return ((AIPlayer) player).getTargetRegion().getAdjustedRating(player);
    }
}