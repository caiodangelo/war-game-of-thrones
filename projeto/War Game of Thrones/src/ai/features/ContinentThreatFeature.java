package ai.features;

import ai.BattleComputer;
import ai.Feature;
import models.Board;
import models.Player;
import models.Region;
import models.BackEndTerritory;

/**
 * The Continent Threat Feature returns a measurement of the threat from the actual
 * player against continents which are completely occupied by enemy players.
 *
 * The threat is also weighted by the rating of the continents.
 *
 * @author rodrigo
 */
public class ContinentThreatFeature extends Feature {


    public ContinentThreatFeature() {
        importance = 2;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double totalThreat = 0.0;
        double regionThreat = 0.0;
        int totalThreats = 0;
        for (Region region : gameState.getRegions()) {
            if (region.getOwner() != null && region.getOwner() != player) {
                for (BackEndTerritory border : region.getBorderTerritories()) {
                    for (BackEndTerritory neighbour : border.getNeighbours()) {
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