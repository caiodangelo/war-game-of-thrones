package ai.features;

import ai.Feature;
import java.util.List;
import models.BackEndTerritory;
import models.Board;
import models.Mission;
import models.Player;
import models.Region;

/**
 * Essa feature calcula o quanto o jogador está próximo de conquistar o seu objetivo.
 * 
 * Essa feature não é aplicada nas fases de distribuição e movimentação.
 *
 * @author rodrigo
 */
public class MissionCompletionFeature extends Feature {

    public MissionCompletionFeature() {
        scaleFactor = 1;
        importance = 5;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        try {
        Mission mission = player.getMission();
        int playerArmies = 0;
        for (Player enemy : gameState.getPlayers()) {
            playerArmies += enemy.numArmies();
        }
        switch (mission.getType()) {
            case Mission.TYPE_HOUSE:
                Player enemy = null;
                for (Player possibleEnemy : gameState.getPlayers()) {
                    if (possibleEnemy.getHouse().getName().equals(mission.getHouse()))
                        enemy = player;
                }
                return ((enemy.numArmies() / playerArmies) + (enemy.getTerritories().size() / gameState.getTerritories().length)) / -2.0;
            case Mission.TYPE_REGION:
                List<Region> regions = mission.getRegions();
                double rating = 0.0;
                for (Region region : regions) {
                    if (region.getName() != null)
                        rating += region.getAdjustedRating(player);
                }
                return rating / regions.size();
            case Mission.TYPE_TERRITORY:
                if (mission.getTerritories() == 17) {
                    int territoriesWithTwoOrMoreArmies = 0;
                    for (BackEndTerritory territory : player.getTerritories()) {
                        if (territory.getNumArmies() >= 2) {
                            territoriesWithTwoOrMoreArmies++;
                        }
                    }
                    return territoriesWithTwoOrMoreArmies / (player.getTerritories().size() * 1.0);
                }
                // Não há cálculo aqui pois missão de território basta conquistar X territórios quaisquer,
                // e as outras features já se encarregam de conquistar territórios.
                return 1.0;
        }
        return 1.0; // Se for outra missão desconhecida retorna 1 que não vai afetar em nada.
        } catch (Exception ex) {
            return 1.0;
        }
    }
}
