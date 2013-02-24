/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import models.Board;
import models.Player;
import models.Territory;

/**
 * Esse evaluator avaliará como vai ficar o jogo quando um jogador posicionar um exército
 * pendente em um certo território, e dará uma "nota" ao novo estado do jogo.
 *
 * @author rodrigo
 */
public class DistributionEvaluator extends Evaluator {

    // O território que será posicionado o próximo exército
    private Territory territory;

    public DistributionEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        features.add(new DistanceToFrontierFeature(getSimulatedGameState(), getSimulatedPlayer()));
        features.add(new MoreThanOneArmyFeature(getSimulatedGameState(), getSimulatedPlayer()));
        features.add(new ContinentSafetyFeature(getSimulatedGameState(), getSimulatedPlayer()));
        features.add(new MaximumThreatFeature(getSimulatedGameState(), getSimulatedPlayer()));
    }

    @Override
    public Board simulateActionExecution() {
        Board newState = currentGameState.getClone();
        Territory clonedTerritory = newState.getTerritories()[territory.getIndex()];
        Player clonedPlayer = getSimulatedPlayer();
        clonedPlayer.distributeArmies(clonedTerritory, 1);
        return newState;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
}