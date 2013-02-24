package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Territory;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author rodrigo
 */
public class BattleComputer {

    private static final double[][] attackProbabilities = new double[][] {
            new double[] { 0.417, 0.106, 0.027 },
            new double[] { 0.754, 0.363, 0.206 },
            new double[] { 0.916, 0.656, 0.470 }
        };

    /**
     * Calcula as chances de o atacante conquistar o território defensor, e retorna um número
     * de 0 a 1 indicando essas chances.
     */
    public static double calculateAttackOdds(Territory attacker, Territory defender) {
        int numAttackers = Math.min(3, attacker.getNumArmies() - 1);
        int numDefenders = Math.min(3, defender.getNumArmies());
        return attackProbabilities[numAttackers - 1][numDefenders - 1];
    }
}