package ai.features;

import ai.Feature;
import java.util.ArrayList;
import java.util.List;
import models.BackEndTerritory;
import models.Board;
import models.Mission;
import models.Player;
import models.Region;

/**
 * Essa feature analisa todos os territórios que o jogador deve conquistar,
 * baseado na missão dele, e calcula quantos exércitos estão em territórios
 * vizinhos aos que ele deve conquistar.
 * 
 * @author rodrigo
 */
public class MissionArmiesCompletionFeature extends Feature {

    public MissionArmiesCompletionFeature() {
        importance = 10;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        List<BackEndTerritory> missionTerritories = new ArrayList<BackEndTerritory>();
        Mission mission = player.getMission();
        switch (mission.getType()) {
            case Mission.TYPE_HOUSE:
                Player enemy = mission.getHouse().getPlayer();
                for (BackEndTerritory enemyTerritory : enemy.getTerritories()) {
                    missionTerritories.add(enemyTerritory);
                }
                break;
            case Mission.TYPE_REGION:
                List<Region> regions = mission.getRegions();
                for (Region region : regions) {
                    missionTerritories.addAll(region.getTerritories());
                }
                break;
            case Mission.TYPE_TERRITORY:
                for (BackEndTerritory territory : gameState.getTerritories()) {
                    if (territory.getOwner() != player) {
                        missionTerritories.add(territory);
                    }
                }
                break;
        }
        double armiesNeighbourToMissionTerritories = 0.0;
        List<BackEndTerritory> territoriesNeighbourToMissionTerritories = new ArrayList<BackEndTerritory>();
        for (BackEndTerritory territory : missionTerritories) {
            for (BackEndTerritory neighbour : territory.getNeighbours()) {
                if (neighbour.getOwner() == player && !territoriesNeighbourToMissionTerritories.contains(neighbour)) {
                    territoriesNeighbourToMissionTerritories.add(neighbour);
                    armiesNeighbourToMissionTerritories += neighbour.getNumArmies();
                }
            }
        }
        return armiesNeighbourToMissionTerritories / (player.numArmies() * 1.0);
    }
}
