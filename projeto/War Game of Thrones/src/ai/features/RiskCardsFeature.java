/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.features;

import ai.Feature;
import models.Board;
import models.Player;

/**
 * The Risk Cards Feature returns the number of Risk Cards which are in posession of the
 * actual player.
 * 
 * This feature is not applied in the Placing new Armies and Fortifying the Position
 * phases of the game.
 *
 * @author rodrigo
 */
public class RiskCardsFeature extends Feature {

    public RiskCardsFeature() {
        importance = 1;
        scaleFactor = 5;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        return player.numCards();
    }

}
