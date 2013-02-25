package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.Region;

/**
 * The Enemy Estimated Reinforcement Feature returns the negative estimation of the
 * total number of armies the enemy players will be able to reinforce in the course of the
 * next game round.
 * 
 * This feature is not applied in the Placing new Armies and Fortifying the Position phases
 * of the game.
 *
 * @author rodrigo
 */
public class EnemyEstimatedReinforcementsFeature extends Feature {

    public EnemyEstimatedReinforcementsFeature() {
        importance = 2;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        scaleFactor = 20 * (gameState.getPlayers().size() - 1);
        int estimatedReinforcements = 0;
        for (Player enemy : gameState.getPlayers()) {
            if (enemy != player) {
                int territoryCount = enemy.getTerritories().size();
                estimatedReinforcements += territoryCount / 2;
                for (Region region : gameState.getRegions()) {
                    if (region.conqueredByPlayer(enemy)) {
                        estimatedReinforcements += region.getBonus();
                    }
                }
            }
        }
        return estimatedReinforcements * -1;
    }
}