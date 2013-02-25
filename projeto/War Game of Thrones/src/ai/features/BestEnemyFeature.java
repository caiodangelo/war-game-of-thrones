package ai.features;

import ai.Feature;
import models.Board;
import models.Player;

/**
 * The Best Enemy Feature returns a negative measure of the power of the best enemy
 * player. The power of the enemy players is measured by their averaged relative army
 * strength and relative territory strength.
 * 
 * The feature result is always in the interval [−1, 0]. This feature is not applied in the
 * Placing new Armies and Fortifying the Position phases of the game.
 *
 * @author rodrigo
 */
public class BestEnemyFeature extends Feature {

    public BestEnemyFeature() {
        importance = 3;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double bestEnemyPower = 0.0;
        int totalArmies = 0;
        for (Player enemy : gameState.getPlayers()) {
            totalArmies += enemy.numArmies();
        }
        for (Player enemy : gameState.getPlayers()) {
            if (enemy != player) {
                double enemyPower = ((enemy.numArmies() / totalArmies) + (enemy.getTerritories().size() / gameState.getTerritories().length)) / 2.0;
                bestEnemyPower = Math.max(bestEnemyPower, enemyPower);
            }
        }
        return bestEnemyPower * -1;
    }
}