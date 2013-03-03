/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.evaluators;

import ai.Evaluator;
import ai.features.ContinentArmyDominationFeature;
import ai.features.ContinentThreatFeature;
import ai.features.MoreThanOneArmyFeature;
import ai.features.DistanceToFrontierFeature;
import ai.features.MaximumThreatFeature;
import models.Board;
import models.Player;
import models.BackEndTerritory;

/**
 * Esse evaluator avaliará como vai ficar o jogo quando um jogador posicionar um exército
 * pendente em um certo território, e dará uma "nota" ao novo estado do jogo.
 *
 * @author rodrigo
 */
public class DistributionEvaluator extends Evaluator {

    // O território que será posicionado o próximo exército
    private BackEndTerritory territory;

    public DistributionEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        features.add(new DistanceToFrontierFeature());
        features.add(new MoreThanOneArmyFeature());
        features.add(new ContinentThreatFeature());
        features.add(new MaximumThreatFeature());
        features.add(new ContinentArmyDominationFeature());
    }

    @Override
    public Board simulateActionExecution() {
        Board newState = currentGameState.getClone();
        BackEndTerritory clonedTerritory = newState.getTerritories()[territory.getIndex()];
        Player clonedPlayer = getSimulatedPlayer(newState);
        clonedPlayer.distributeArmies(clonedTerritory, 1);
        return newState;
    }

    public BackEndTerritory getTerritory() {
        return territory;
    }

    public void setTerritory(BackEndTerritory territory) {
        this.territory = territory;
    }
}