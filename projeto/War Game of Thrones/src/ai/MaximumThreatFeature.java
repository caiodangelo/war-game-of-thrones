package ai;

import models.Board;
import models.Player;
import models.Territory;

/**
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