package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.BackEndTerritory;

/**
 * The Hinterland Feature returns the percentage of the territories of the actual player
 * (AP) which are hinterland territories, i.e. which are not adjacent to an enemy territory.
 * 
 * The feature result is always in the interval [0, 1]. This feature is not applied in the
 * Placing new Armies and Fortifying the Position phases of the game.
 *
 * This feature serves mostly to analise the size of the player's territory islands, meaning that
 * the bigger a single territory island is, the better is for the player (and easier to conquer a
 * full region as well).

 *
 * @author rodrigo
 */
public class HinterlandFeature extends Feature {

    public HinterlandFeature() {
        importance = 2;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double numberHinterlands = 0.0;
        for (BackEndTerritory territory : player.getTerritories()) {
            if (territory.isHinterland()) {
                numberHinterlands++;
            }
        }
        return numberHinterlands / player.getTerritories().size();
    }
}