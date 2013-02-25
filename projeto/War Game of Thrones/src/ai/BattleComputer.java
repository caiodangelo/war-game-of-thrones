package ai;

import java.util.ArrayList;
import java.util.List;
import models.Board;
import models.Territory;
import org.apache.commons.lang.SerializationUtils;

/**
 * Essa classe serve para avaliar e gerar probabilidades de cenários de ataques
 * e batalhas do jogo, para ser utilizados pela IA para avaliar se um ataque
 * vale a pena ser efetuado.
 *
 * @author rodrigo
 */
public class BattleComputer {

    /**
     * Solução temporária, listando as probabilidades reais (calculadas utilizando uma Absorbing Markov chain)
     * de um único ataque, envolvendo de 1 a 3 exércitos de cada lado.
     *
     * Dimensões: attacker X defender
     *
     * TODO: Implementar uma função para calcular esses valores dinamicamente.
     */
    private static final double[][] attackProbabilities = new double[][]{
        new double[]{0.417, 0.106, 0.027},
        new double[]{0.754, 0.363, 0.206},
        new double[]{0.916, 0.656, 0.470}
    };

    /**
     * Solução temporária, listando as probabilidades de cada resultado de uma batalha.
     *
     * Dimensões: defender X attacker X results
     */
    private static final double[][][] attackResultProbabilities = new double[][][]{
        new double[][]{new double[] {0.42, 0.58}, new double[] {0.58, 0.42}, new double[] {0.66, 0.34}},
        new double[][]{new double[] {0.25, 0.75}, new double[] {0.23, 0.45, 0.32}, new double[] {0.37, 0.29, 0.34}},
        new double[][]{new double[] {0.17, 0.83}, new double[] {0.13, 0.25, 0.62}, new double[] {0.14, 0.22, 0.26, 0.38}}
    };

    /**
     * Calcula as chances de o atacante conquistar o território defensor, e retorna um número
     * de 0 a 1 indicando essas chances.
     *
     * TODO: remover este método e utilizar apenas o método abaixo, que será refatorado (juro).
     */
    public static double calculateAttackOdds(Territory attacker, Territory defender) {
        int numAttackers = Math.min(3, attacker.getNumArmies() - 1);
        int numDefenders = Math.min(3, defender.getNumArmies());
        return attackProbabilities[numAttackers - 1][numDefenders - 1];
    }

    /**
     * Calcula a probabilidade de o atacante conseguir conquistar o defensor,
     * utilizando um ou vários ataques em sequência.
     *
     * TODO: excluir esta merda e implementar a forma correta de calcular a probabilidade (utilizando uma Absorbing Markov chain)
     * 
     * @param attacker O {@link models.Territory} atacante
     * @param defender O {@link models.Territory} defensor
     * @return A probabilidade, no intervalo de [0,1]
     */
    public static double calculateThreatToTerritory(Territory attacker, Territory defender) {
        int attackers = attacker.getNumArmies() - 1;
        int defenders = defender.getNumArmies();
        int maxArmies = Math.max(attackers, defenders);
        return Math.max(0.0, Math.min(10.0, 0.500 + (Math.pow((attackers - defenders) / maxArmies, 3) - (0.05 + ((attackers + defenders) / 2) / 100))));
    }

    /**
     * Gera todas as possibilidades de resultados de um ataque de um território para outro, utilizando
     * entre 1 e 3 exércitos em cada lado, e para cada resultado possível, gera um novo GameState para
     * ser avaliado pela IA.
     * 
     * @param currentGameState O GameState atual do jogo
     * @param attacker O {@link models.Territory} atacante
     * @param defender O {@link models.Territory} defensor
     * @param numAttackers O número de exércitos participantes do ataque
     * @param numDefenders O número de exércitos participantes da defesa
     * @return
     */
    public static List<Board> generateAttackOutcomes(Board currentGameState, Territory attacker, Territory defender, int numAttackers) {
        List<Board> outcomes = new ArrayList<Board>();
        int numDefenders = Math.min(3, defender.getNumArmies());
        int numLosses = Math.min(numAttackers, numDefenders);
        for (int i = 0; i <= numLosses; i++) {
            Board newGameState = (Board) SerializationUtils.clone(currentGameState);
            Territory clonedAttacker = newGameState.getTerritories()[attacker.getIndex()];
            Territory clonedDefender = newGameState.getTerritories()[defender.getIndex()];
            int attackerLosses = i;
            int defenderLosses = numLosses - i;
            clonedAttacker.increaseArmies(-attackerLosses);
            if (clonedDefender.getNumArmies() - defenderLosses == 0) {
                clonedDefender.setOwner(clonedAttacker.getOwner());
                clonedDefender.setNumArmies(1);
                clonedAttacker.increaseArmies(-1);
            }
            else {
                clonedDefender.increaseArmies(-defenderLosses);
            }
            outcomes.add(newGameState);
        }
        return outcomes;
    }

    public static double[] generateAttackOutcomeProbabilities(Board currentGameState, Territory attacker, Territory defender, int numAttackers) {
        int numDefenders = Math.min(3, defender.getNumArmies());
        return attackResultProbabilities[numDefenders - 1][numAttackers - 1];
    }
}
