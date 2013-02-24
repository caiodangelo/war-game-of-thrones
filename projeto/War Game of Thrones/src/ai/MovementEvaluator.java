/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai;

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
        // TODO: preencher a lista de features com as features aplicáveis para este evaluator.
    }

    @Override
    public Board simulateActionExecution() {
        // TODO: Copiar e gerar um novo tabuleiro, movimentando 1 exército de um dado território para outro dado território
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double evaluate(Board gameState) {
        throw new UnsupportedOperationException("Not supported yet.");
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
