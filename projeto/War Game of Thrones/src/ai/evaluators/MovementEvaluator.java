/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.evaluators;

import ai.Evaluator;
import ai.features.ContinentThreatFeature;
import ai.features.MoreThanOneArmyFeature;
import ai.features.DistanceToFrontierFeature;
import ai.features.MaximumThreatFeature;
import models.Board;
import models.Player;
import models.Territory;

/**
 *
 * @author rodrigo
 */
public class MovementEvaluator extends Evaluator {

    private Territory originTerritory;
    private Territory destinyTerritory;

    public MovementEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        features.add(new DistanceToFrontierFeature());
        features.add(new MoreThanOneArmyFeature());
        features.add(new ContinentThreatFeature());
        features.add(new MaximumThreatFeature());
    }

    @Override
    public Board simulateActionExecution() {
        Board newState = currentGameState.getClone();
        Territory clonedOriginTerritory = newState.getTerritories()[originTerritory.getIndex()];
        Territory clonedDestinyTerritory = newState.getTerritories()[destinyTerritory.getIndex()];
        Player clonedPlayer = getSimulatedPlayer();
        clonedPlayer.moveArmies(clonedOriginTerritory, clonedDestinyTerritory, 1);
        return newState;
    }

    public Territory getDestinyTerritory() {
        return destinyTerritory;
    }

    public void setDestinyTerritory(Territory destinyTerritory) {
        this.destinyTerritory = destinyTerritory;
    }

    public Territory getOriginTerritory() {
        return originTerritory;
    }

    public void setOriginTerritory(Territory originTerritory) {
        this.originTerritory = originTerritory;
    }
}