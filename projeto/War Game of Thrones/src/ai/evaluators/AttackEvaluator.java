/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.evaluators;

import ai.Evaluator;
import models.Board;
import models.Player;
import models.TerritoryTransaction;

/**
 *
 * @author rodrigo
 */
public class AttackEvaluator extends Evaluator {

    private TerritoryTransaction attack;

    public AttackEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        // TODO: add as features
    }

    @Override
    protected Board simulateActionExecution() {
        Board newState = currentGameState.getClone();

        Player clonedPlayer = getSimulatedPlayer();
//        clonedPlayer.moveArmies(clonedOriginTerritory, clonedDestinyTerritory, 1);
        return newState;
    }

}
