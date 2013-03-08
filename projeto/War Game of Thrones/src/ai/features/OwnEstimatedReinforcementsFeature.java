package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.Region;

/**
 * The Own Estimated Reinforcement Feature returns the expectation of the total number
 * of armies the actual player (AP) will be able to reinforce in his next Placing new Armies
 * game phase.
 *
 * This feature is not applied in the Placing new Armies and Fortifying the Position phases
 * of the game.
 * 
 * @author rodrigo
 */
public class OwnEstimatedReinforcementsFeature extends Feature {

    public OwnEstimatedReinforcementsFeature() {
        importance = 5;
        scaleFactor = 20; // Imaginando que o jogador possua todos os territórios, ele receberá no máximo 20 exércitos, sem contar as bonificações.
    }

    @Override
    public double calculate(Board gameState, Player player) {
        int territoryCount = player.getTerritories().size();
        int pendingArmies = 0;
        pendingArmies += territoryCount / 2;
        for (Region region : gameState.getRegions()) {
            if (region.conqueredByPlayer(player)) {
                pendingArmies += region.getBonus();
            }
        }
        return pendingArmies;
    }
}