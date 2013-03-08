package ai.features;

import ai.Feature;
import models.BackEndTerritory;
import models.Board;
import models.Player;

/**
 * Calcula os territórios que mais tem territórios vizinhos atacáveis comparado com
 * o total de territórios vizinhos, e depois faz a média entre todos os territórios.
 *
 * @author rodrigo
 */
public class MostAttackableEnemyFeature extends Feature {

    public MostAttackableEnemyFeature() {
        importance = 6;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        double rating = 0.0;
        for (BackEndTerritory territory : player.getTerritoriesThatCanAttack()) {
            int enemyTerritories = 0;
            for (BackEndTerritory neighbour : territory.getNeighbours()) {
                if (neighbour.getOwner() != player)
                    enemyTerritories++;
            }
            if (territory.getNeighbours().size() > 0)
                rating += enemyTerritories / territory.getNeighbours().size();
        }
        if (player.getTerritoriesThatCanAttack().size() > 0)
            return rating / player.getTerritoriesThatCanAttack().size();
        return 0.0;
    }
}
