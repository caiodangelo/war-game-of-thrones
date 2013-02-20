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

    @Override
    public double calculate(Board gameState, Player player) {
        double sumArmiesDistances = 0.0;
        for (Territory t : player.getTerritories()) {
            sumArmiesDistances += t.getNumArmies() * distanceToNearestEnemyTerritory(t);
        }
        return (player.numArmies() * 1.0) / sumArmiesDistances;
    }

    private int distanceToNearestEnemyTerritory(Territory territory) {
        return 1;
    }
}