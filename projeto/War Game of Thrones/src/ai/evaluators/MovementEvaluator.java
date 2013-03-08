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
import ai.features.NeighbourTerritoriesFeature;
import models.Board;
import models.Player;
import models.BackEndTerritory;

/**
 *
 * @author rodrigo
 */
public class MovementEvaluator extends Evaluator {

    private BackEndTerritory originTerritory;
    private BackEndTerritory destinyTerritory;

    public MovementEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        features.add(new DistanceToFrontierFeature());
        features.add(new MoreThanOneArmyFeature());
        features.add(new ContinentThreatFeature());
        features.add(new MaximumThreatFeature());
        features.add(new ContinentArmyDominationFeature());
//        features.add(new NeighbourTerritoriesFeature());
    }

    @Override
    public Board simulateActionExecution() {
        Board newState = currentGameState.getClone();
        BackEndTerritory clonedOriginTerritory = newState.getTerritories()[originTerritory.getIndex()];
        BackEndTerritory clonedDestinyTerritory = newState.getTerritories()[destinyTerritory.getIndex()];
        Player clonedPlayer = getSimulatedPlayer(newState);
        clonedPlayer.moveArmies(clonedOriginTerritory, clonedDestinyTerritory, 1);
        return newState;
    }

    public BackEndTerritory getDestinyTerritory() {
        return destinyTerritory;
    }

    public void setDestinyTerritory(BackEndTerritory destinyTerritory) {
        this.destinyTerritory = destinyTerritory;
    }

    public BackEndTerritory getOriginTerritory() {
        return originTerritory;
    }

    public void setOriginTerritory(BackEndTerritory originTerritory) {
        this.originTerritory = originTerritory;
    }
}