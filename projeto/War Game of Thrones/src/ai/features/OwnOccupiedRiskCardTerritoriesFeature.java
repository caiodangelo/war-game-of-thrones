package ai.features;

import ai.Feature;
import models.Board;
import models.CardTerritory;
import models.Player;

/**
 * The Own Occupied Risk Card Territories Feature returns the number of Risk Cards in
 * posession of the actual player whose corresponding territory is occupied by the actual
 * player.
 * This feature is not applied in the Placing new Armies and Fortifying the Position
 * phases of the game.

 *
 * @author rodrigo
 */
public class OwnOccupiedRiskCardTerritoriesFeature extends Feature {

    public OwnOccupiedRiskCardTerritoriesFeature() {
        importance = 1;
        scaleFactor = 5; // O número máximo de cartas que o jogador por ter ao mesmo tempo é 5.
    }

    @Override
    public double calculate(Board gameState, Player player) {
        int occupiedRiskCards = 0;
        for(CardTerritory card : player.getCards()) {
            if (player.getTerritories().contains(card.getTerritory())) {
                occupiedRiskCards++;
            }
        }
        return occupiedRiskCards;
    }

}