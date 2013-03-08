package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.BackEndTerritory;

/**
 * The Distance to Frontier Feature returns a measurement of the army distribution
 * throughout the actual player’s territories. Armies positioned far away from territories
 * occupied by enemy players result in a lower feature value than armies positioned on
 * border territories. The function distance(territory) is implemented in the class Useful-
 * Functions and computes the distance of a given friendly territory to the nearest enemy
 * territory.
 * 
 * The feature result is always in the interval (0, 1]. This feature is not applied in the
 * Attacking phase of the game.
 *
 * @author rodrigo
 */
public class DistanceToFrontierFeature extends Feature {

    public DistanceToFrontierFeature() {
        importance = 6;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double sumArmiesDistances = 0.0;
        for (BackEndTerritory t : player.getTerritories()) {
            sumArmiesDistances += t.getNumArmies() * distanceToNearestEnemyTerritory(t, 1);
        }
        return (player.numArmies() * -1.0) / sumArmiesDistances;
    }

    private int distanceToNearestEnemyTerritory(BackEndTerritory territory, int totalDistance) {
        int minDistance = Integer.MAX_VALUE;
        if (totalDistance >= 6) {
            // Se caiu aqui é pq entrou em recursão infinita, então vamos retornar logo algo
            // pra não dar StackOverflow.
            return totalDistance;
        }
        for (BackEndTerritory neighbour : territory.getNeighbours()) {
            if (neighbour.getOwner() != territory.getOwner()) {
                return totalDistance;
            }
        }
        for (BackEndTerritory neighbour : territory.getNeighbours()) {
            minDistance = Math.min(minDistance, distanceToNearestEnemyTerritory(neighbour, totalDistance + 1));
        }
        return -1;
    }
}