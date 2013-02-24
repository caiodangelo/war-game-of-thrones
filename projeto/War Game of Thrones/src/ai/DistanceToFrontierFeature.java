/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import models.Board;
import models.Player;
import models.Territory;

/**
 * Calcula um número no intervalo (0, 1], que indica se os exércitos do jogador estão
 * mais pertos ou distantes das suas fronteiras (1 é melhor, significa que os exércitos
 * estão todos nas fronteiras, que precisam ser defendidas).
 *
 * @author rodrigo
 */
public class DistanceToFrontierFeature extends Feature {

    public DistanceToFrontierFeature(Board gameState, Player player) {
        super(gameState, player);
        importance = 3;
        scaleFactor = 1;
    }

    @Override
    public double calculate() {
        double sumArmiesDistances = 0.0;
        for (Territory t : player.getTerritories()) {
            sumArmiesDistances += t.getNumArmies() * distanceToNearestEnemyTerritory(t, 1);
        }
        return (player.numArmies() * 1.0) / sumArmiesDistances;
    }

    private int distanceToNearestEnemyTerritory(Territory territory, int totalDistance) {
        int minDistance = Integer.MAX_VALUE;
        for (Territory neighbour : territory.getNeighbours()) {
            if (neighbour.getOwner() != territory.getOwner()) {
                return totalDistance;
            }
        }
        for (Territory neighbour : territory.getNeighbours()) {
            minDistance = Math.min(minDistance, distanceToNearestEnemyTerritory(neighbour, totalDistance + 1));
        }
        return -1;
    }
}