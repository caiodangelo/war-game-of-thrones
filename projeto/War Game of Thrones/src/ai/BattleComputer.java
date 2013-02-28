package ai;

import java.util.ArrayList;
import java.util.List;
import models.Board;
import models.BackEndTerritory;
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
     * Matriz tridimenstional que lista as probabilidade dos resultados de um lançamento de dados.
     *
     * Para pegar a probabilidade de o atacante eliminar 2 exércitos da defesa em um lançamento 2x1,
     * por exemplo, ou seja, 2 dados de ataque e 1 de defesa, acessamos a matriz da seguinte forma:
     *
     * attackResultProbabilities[0][1][0]
     *
     * Onde: o primeiro 0 indica o número de dados da defesa - 1, o 1 indica o número de dados do ataque - 1,
     * e o último zero indica a posição do array final de resultados para aquele ataque 2x1.
     *
     * Esse array está ordenado sempre na ordem que favorece o atacante, ou seja, no ataque exemplo, 2x1,
     * existem 3 possibilidades de resultados: atacante ganha 2, cada um ganha 1, atacante perde 2. Essa ordem
     * é sempre mantida, sempre com o melhor resultado para o atacante primeiro.
     *
     * De forma geral, o número de resultados possíveis para um ataque é igual a min(dadosAtaque, dadosDefesa) + 1.
     *
     * O ataque mais complexo é o 3x3, que tem como resultados: ataque ganha 3, ataque ganha 2 e perde 1,
     * ataque ganha 1 e perde 2, ataque perde 3. As probabilidades para estes casos são: 0.14, 0.22, 0.26 e 0.38.
     *
     * Obs: os cenários de ataque envolvendo 3 dados de defesa estão mais arredondados pois na regra original de Risk
     * só é possível defender com no máximo 2 dados, e a única tabela que eu achei dessas probabilidades do War
     * estava arredondado assim. As tabelas de Risk estão muito mais detalhadas.
     *
     * Dimensões: numDadosDefesa, numDadosAtaque, resultadoPossivel
     */
    private static final double[][][] attackResultProbabilities = new double[][][]{
        new double[][]{new double[]{0.4167, 0.5833}, new double[]{0.5787, 0.4213}, new double[]{0.6597, 0.3403}},
        new double[][]{new double[]{0.2546, 0.7454}, new double[]{0.2276, 0.4483, 0.3241}, new double[]{0.3717, 0.2926, 0.3358}},
        new double[][]{new double[]{0.17, 0.83}, new double[]{0.13, 0.25, 0.62}, new double[]{0.14, 0.22, 0.26, 0.38}}
    };

    /**
     * Calcula a probabilidade de o atacante conseguir conquistar o defensor,
     * utilizando quantos ataques em sequência forem necessários.
     * 
     * @param attacker O {@link models.BackEndTerritory} atacante
     * @param defender O {@link models.BackEndTerritory} defensor
     * @return A probabilidade, no intervalo de [0,1]
     */
    public static double calculateThreatToTerritory(BackEndTerritory attacker, BackEndTerritory defender) {
        return new BattleSimulation(attacker.getSurplusArmies(), defender.getNumArmies()).simulate();
    }

    /**
     * Gera todas as possibilidades de resultados de um ataque de um território para outro, utilizando
     * entre 1 e 3 exércitos em cada lado, e para cada resultado possível, gera um novo GameState para
     * ser avaliado pela IA.
     * 
     * @param currentGameState O GameState atual do jogo
     * @param attacker O {@link models.BackEndTerritory} atacante
     * @param defender O {@link models.BackEndTerritory} defensor
     * @param numAttackers O número de exércitos participantes do ataque
     * @param numDefenders O número de exércitos participantes da defesa
     * @return
     */
    public static List<Board> generateAttackOutcomes(Board currentGameState, BackEndTerritory attacker, BackEndTerritory defender, int numAttackers) {
        List<Board> outcomes = new ArrayList<Board>();
        int numDefenders = Math.min(3, defender.getNumArmies());
        int numLosses = Math.min(numAttackers, numDefenders);
        for (int i = 0; i <= numLosses; i++) {
            Board newGameState = (Board) SerializationUtils.clone(currentGameState);
            BackEndTerritory clonedAttacker = newGameState.getTerritories()[attacker.getIndex()];
            BackEndTerritory clonedDefender = newGameState.getTerritories()[defender.getIndex()];
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

    public static double[] generateAttackOutcomeProbabilities(Board currentGameState, BackEndTerritory attacker, BackEndTerritory defender, int numAttackers) {
        int numDefenders = Math.min(3, defender.getNumArmies());
        return attackResultProbabilities[numDefenders - 1][numAttackers - 1];
    }

    public class AbsorbingMarkovChainNode {

        public int attackers;
        public int defenders;
        public double probability;
        public boolean finalState;
        public List<AbsorbingMarkovChainNode> nextStates;

        public void calculateNextStates() {
            int singleAttackAttackers = Math.min(3, attackers);
            int singleAttackDefenders = Math.min(3, defenders);
            int numLosses = Math.min(singleAttackAttackers, singleAttackDefenders);
            for (int i = 0; i <= numLosses; i++) {
                int attackerLosses = i;
                int defenderLosses = numLosses - i;

                // pegar todos os resultados e multiplicar a probabilidade desta folha com a do resultado.

                // p/ calcular chances do ataque no dado:
                // 1 atk, 1 def: sum(0,1,2,3,4,5) / 6^numDados=2
                // 2 atk, 1 def: sum(0,1,4,9,16,25) / 6^3
                // 3 atk, 1 def: summ(0,1,8,27,64,125) / 6^4

                // p/ calcular chances da defesa no dado:
                // 1 atk, 1 def: sum(1,2,3,4,5,6) / 6^2
                // 2 atk, 1 def: sum(1,4,9,16,25,36) / 6^3
                // 3 atk, 1 def: sum(1,8,27,64,125,216) / 6^4

                // p/calcular chances com 2 dados de cada ou mais:
                // 2 atk, 2 def:
            }
        }
    }

    public static double[] getAttackProbabilities(int attackers, int defenders) {
        return attackResultProbabilities[defenders - 1][attackers - 1];
    }
}
