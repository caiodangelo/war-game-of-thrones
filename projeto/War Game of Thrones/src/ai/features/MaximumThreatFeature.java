package ai.features;

import ai.BattleComputer;
import ai.Feature;
import models.Board;
import models.Player;
import models.Territory;

/**
 * The Maximum Threat Feature returns a measurement of the probability that the actual
 * player is able to successfully occupy at least a single enemy territory during his next
 * Attacking game phase. Of all possible battles that the actual player is able to initiate
 * the feature computes the victory probability and the maximum of these probabilities is
 * returned.
 *
 * The feature result is always in the interval [0, 1]. This feature is not applied in the
 * Attacking phase of the game.
 *
 * @author rodrigo
 */
public class MaximumThreatFeature extends Feature {

    public MaximumThreatFeature() {
        importance = 3;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double maxProbability = 0;
        for (Territory territory : player.getTerritories()) {
            if (territory.getSurplusArmies() > 0) {
                for (Territory neighbour : territory.getNeighbours()) {
                    if (neighbour.getOwner() != player) {
                        maxProbability = Math.max(maxProbability, BattleComputer.calculateThreatToTerritory(territory, neighbour));
                    }
                }
            }
        }
        return maxProbability;
    }
}