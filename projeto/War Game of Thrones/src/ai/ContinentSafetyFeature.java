/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai;

import models.Board;
import models.Player;
import models.Region;
import models.Territory;

/**
 *
 * @author rodrigo
 */
public class ContinentSafetyFeature extends Feature {


    public ContinentSafetyFeature(Board gameState, Player player) {
        super(gameState, player);
        importance = 2;
        scaleFactor = 1;
    }

    @Override
    public double calculate() {
        double totalThreat = 0.0;
        double regionThreat = 0.0;
        int totalThreats = 0;
        for (Region region : gameState.getRegions()) {
            if (region.getOwner() != null && region.getOwner() != player) {
                for (Territory border : region.getBorderTerritories()) {
                    for (Territory neighbour : border.getNeighbours()) {
                        regionThreat += Math.pow(BattleComputer.calculateThreatToTerritory(neighbour, border), 2);
                        totalThreats++;
                    }
                }
            }
            regionThreat *= region.getRating();
            totalThreat += regionThreat;
        }
        scaleFactor = 5 * totalThreats;
        return totalThreat;
    }
}