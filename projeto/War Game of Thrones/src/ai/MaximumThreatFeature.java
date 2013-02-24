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
public class MaximumThreatFeature extends Feature {

    public MaximumThreatFeature(Board gameState, Player player) {
        super(gameState, player);
        importance = 3;
        scaleFactor = 1;
    }

    @Override
    public double calculate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
