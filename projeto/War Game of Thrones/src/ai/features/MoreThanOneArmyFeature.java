/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.features;

import ai.Feature;
import models.Board;
import models.Player;
import models.Territory;

/**
 *
 * @author rodrigo
 */
public class MoreThanOneArmyFeature extends Feature {

    public MoreThanOneArmyFeature() {
        importance = 2;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double fortifiedTerritories = 0.0;
        for (Territory territory : player.getTerritories()) {
            if (territory.getSurplusArmies() > 0) {
                fortifiedTerritories++;
            }
        }
        return fortifiedTerritories / (player.getTerritories().size() * 1.0);
    }

}
