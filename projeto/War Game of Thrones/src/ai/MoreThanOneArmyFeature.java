/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai;

import models.Board;
import models.Player;

/**
 *
 * @author rodrigo
 */
public class MoreThanOneArmyFeature extends Feature {

    public MoreThanOneArmyFeature(Board gameState, Player player) {
        super(gameState, player);
        importance = 2;
        scaleFactor = 1;
    }

    @Override
    public double calculate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
