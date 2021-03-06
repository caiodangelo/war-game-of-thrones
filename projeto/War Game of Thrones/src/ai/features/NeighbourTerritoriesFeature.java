package ai.features;

import ai.BattleComputer;
import ai.Feature;
import java.util.ArrayList;
import java.util.List;
import models.BackEndTerritory;
import models.Board;
import models.Player;

/**
 * Essa feature vai calcular a média da chance de conquistar cada território vizinho para cada território
 * pertencente ao jogador.
 *
 * @author rodrigo
 */
public class NeighbourTerritoriesFeature extends Feature {

    public NeighbourTerritoriesFeature() {
        importance = 5;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        try {
            double rating = 0.0;
            for (BackEndTerritory territory : player.getTerritoriesThatCanAttack()) {
                List<BackEndTerritory> enemyTerritories = new ArrayList<BackEndTerritory>();
                for (BackEndTerritory neighbour : territory.getNeighbours()) {
                    if (neighbour.getOwner() != player)
                        enemyTerritories.add(neighbour);
                }
                double partialRating = 0.0;
                for (BackEndTerritory enemyTerritory : enemyTerritories) {
                    partialRating += BattleComputer.calculateThreatToTerritory(territory, enemyTerritory);
                }
                if (enemyTerritories.size() > 0)
                    rating += (partialRating / enemyTerritories.size());
            }
            if (player.getTerritoriesThatCanAttack().size() > 0)
                return rating / player.getTerritoriesThatCanAttack().size();
            return 0.0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }
}
