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
 * pendente em um certo território.
 *
 * @author rodrigo
 */
public class DistributionEvaluator extends Evaluator {

    // O território que será posicionado o próximo exército
    private Territory territory;

    public DistributionEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        // TODO: preencher a lista de features com as features aplicáveis para este evaluator.
    }

    @Override
    public Board simulateActionExecution() {
        // TODO: Copiar e gerar um novo tabuleiro, posicionando 1 exército no território especificado.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double evaluate(Board gameState) {
        // TODO: Gerar uma nota para esse evaluator, aplicando as features certas.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
}