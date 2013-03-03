package ai.features;

import ai.Feature;
import models.AIPlayer;
import models.BackEndTerritory;
import models.Board;
import models.Player;
import models.Region;

/**
 * The Continent Army Domination feature returns the number of armies the actual player
 * (AP) has on the target continent (TC) divided by the total number of armies on the
 * target continent.
 * 
 * The feature result is always in the interval [0, 1]. This feature is not applied in the
 * Attacking phase of the game.
 * 
 * @author rodrigo
 */
public class ContinentArmyDominationFeature extends Feature {

    public ContinentArmyDominationFeature() {
        scaleFactor = 1;
        importance = 2;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        Region targetRegion = ((AIPlayer) player).getTargetRegion();
        double armiesInRegion = 0.0;
        double playerArmiesInRegion = 0.0;
        for (BackEndTerritory territory : targetRegion.getTerritories()) {
            armiesInRegion += territory.getNumArmies();
            if (territory.getOwner() == player) {
                playerArmiesInRegion += territory.getNumArmies();
            }
        }
        return (playerArmiesInRegion / (armiesInRegion * 1.0));
    }
}