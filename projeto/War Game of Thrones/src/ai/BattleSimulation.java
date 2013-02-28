package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class BattleSimulation {

    private int numberAttackers;
    private int numberDefenders;
    private int attackerLosses;
    private int defenderLosses;
    private int resultIndex;
    private Attack attack;
    private BattleSimulation parent;
    private List<BattleSimulation> simulations;
    private double probability; // Probabilidade sempre se referindo à chance do ataque vencer
    private boolean battleOver;
    private HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> knownProbabilities;

    public static void main(String[] args) {
        BattleSimulation teste = new BattleSimulation(5, 5);
        System.out.println(teste.simulate());
    }

    private BattleSimulation() {
    }

    public BattleSimulation(int numberAttackers, int numberDefenders) {
        this.numberAttackers = numberAttackers;
        this.numberDefenders = numberDefenders;
        this.attackerLosses = 0;
        this.defenderLosses = 0;
        simulations = new ArrayList<BattleSimulation>();
        battleOver = (numberAttackers == 0 || numberDefenders == 0);
        probability = 1.0;
        knownProbabilities = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
        parent = null;
        resultIndex = -1;
        generateAttack();
    }

    public BattleSimulation(int numberAttackers, int numberDefenders, double probability, BattleSimulation parent) {
        this(numberAttackers, numberDefenders);
        this.probability = probability;
        this.parent = parent;
        this.attackerLosses = parent.getNumberAttackers() - numberAttackers;
        this.defenderLosses = parent.getNumberDefenders() - numberDefenders;
        this.resultIndex = getResultIndex(numberAttackers, numberDefenders, attackerLosses, defenderLosses);
    }

    public boolean isProbabilityKnown(int attackers, int defenders, int resultIndex) {
        BattleSimulation root = getRootSimulation();
        return root.knownProbabilities.containsKey(attackers) && root.knownProbabilities.get(attackers).containsKey(defenders) && root.knownProbabilities.get(attackers).get(defenders).containsKey(resultIndex);
    }

    public double getKnownProbability(int attackers, int defenders, int resultIndex) {
        BattleSimulation root = getRootSimulation();
        if (isProbabilityKnown(attackers, defenders, resultIndex))
            return root.knownProbabilities.get(attackers).get(defenders).get(resultIndex);
        return -1.0;
    }

    private void addKnownProbability(BattleSimulation simulation, double prob) {
        double originalProbability = prob;
        BattleSimulation root = simulation;
        if (root != null) {
            while (root.parent != null) {
                originalProbability /= (getProbabilityOfResult(root.parent.attack.numberAttackers, root.parent.attack.numberDefenders, root.attackerLosses, root.defenderLosses));
                root = root.parent;
            }
            if (!root.knownProbabilities.containsKey(simulation.numberAttackers))
                root.knownProbabilities.put(simulation.numberAttackers, new HashMap<Integer, HashMap<Integer, Double>>());
            if (!root.knownProbabilities.get(simulation.numberAttackers).containsKey(simulation.numberDefenders))
                root.knownProbabilities.get(simulation.numberAttackers).put(simulation.numberDefenders, new HashMap<Integer, Double>());
            root.knownProbabilities.get(simulation.numberAttackers).get(simulation.numberDefenders).put(simulation.resultIndex, originalProbability);
        }
    }

    public double simulate() {
        if (isBattleOver())
            return numberAttackers > 0 ? probability : 0.0;
        double winningProbability = 0.0;
        generateSimulations();
        for (BattleSimulation simulation : simulations) {
            if (isProbabilityKnown(simulation.getNumberAttackers(), simulation.getNumberDefenders(), simulation.resultIndex)) {
                winningProbability += getKnownProbability(simulation.getNumberAttackers(), simulation.getNumberDefenders(), simulation.resultIndex) * simulation.probability;
            } else {
                double prob = simulation.simulate();
                winningProbability += prob;
                addKnownProbability(simulation, prob);
            }
        }
        return winningProbability;
    }

    private void generateSimulations() {
        int[][] attackOutcomes = attack.calculateAttack();
        for (int i = 0; i
                < attackOutcomes.length; i++) {
            int[] outcome = attackOutcomes[i];
            double scenarioProbability = BattleComputer.getAttackProbabilities(attack.getNumberAttackers(), attack.getNumberDefenders())[i];
            simulations.add(generateSimulation(outcome[0], outcome[1], scenarioProbability));
        }
    }

    /**
     * Gera a próxima BattleSimulation, aplicando as perdas de exércitos de cada lado e as probabilidades.
     * 
     * @param attackerLosses O número de exércitos perdido pelo ataque
     * @param defenderLosses O número de exércitos perdido pela defesa
     * @param scenarioProbability A probabilidade desse cenário acontecer
     * @return O novo estado do BattleSimulation
     */
    private BattleSimulation generateSimulation(int attackerLosses, int defenderLosses, double scenarioProbability) {
        return new BattleSimulation(numberAttackers - attackerLosses, numberDefenders - defenderLosses, scenarioProbability * probability, this);
    }

    /**
     * Cria o próximo ataque, tentando pegar o maior número possível de exércitos de cada lado.
     * O simulador de batalha vai ignorar os cenários em que o atacante ou defensor resolver
     * usar menos exércitos do que pode, já que isso é uma desvantagem.
     */
    private void generateAttack() {
        int attackers = Math.min(numberAttackers, 3);
//        int defenders = Math.min(numberDefenders, 2);
        int defenders = Math.min(numberDefenders, 3); // Na regra original só podia defender com 2, e pelas contas que eu fiz era bem mais justo do que poder usar 3
        attack = new Attack(attackers, defenders);
    }

    private double getProbabilityOfResult(int attackers, int defenders, int atkLosses, int defLosses) {
        double[] probabilities = BattleComputer.getAttackProbabilities(attackers, defenders);
        int index = getResultIndex(attackers, defenders, atkLosses, defLosses);
        if (index >= 0)
            return probabilities[index];
        return -1.0;
    }

    private int getResultIndex(int attackers, int defenders, int atkLosses, int defLosses) {
        Attack mockAttack = new Attack(attackers, defenders);
        int[][] outcomes = mockAttack.calculateAttack();
        for (int i = 0; i < outcomes.length; i++) {
            int[] outcome = outcomes[i];
            if (outcome[0] == atkLosses && outcome[1] == defLosses)
                return i;
        }
        return -1;
    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public boolean isBattleOver() {
        return battleOver;
    }

    public void setBattleOver(boolean battleOver) {
        this.battleOver = battleOver;
    }

    public int getNumberAttackers() {
        return numberAttackers;
    }

    public void setNumberAttackers(int numberAttackers) {
        this.numberAttackers = numberAttackers;
    }

    public int getNumberDefenders() {
        return numberDefenders;
    }

    public void setNumberDefenders(int numberDefenders) {
        this.numberDefenders = numberDefenders;
    }

    public List<BattleSimulation> getSimulations() {
        return simulations;
    }

    public void setSimulations(List<BattleSimulation> simulations) {
        this.simulations = simulations;
    }

    public BattleSimulation getRootSimulation() {
        BattleSimulation root = this;
        while (root.parent != null)
            root = root.parent;
        return root;
    }

    @Override
    public String toString() {
        return "BattleSimulation {atk: " + numberAttackers + ", def: " + numberDefenders + ", prob: " + probability + "}";
    }

    /**
     * Classe auxiliar criada para calcular os cenários resultantes de uma batalha.
     */
    public class Attack {

        int numberAttackers;
        int numberDefenders;
        int[][] outcomes;

        public Attack(int attackers, int defenders) {
            this.numberAttackers = attackers;
            this.numberDefenders = defenders;
            int numPossibleScenarios = Math.min(attackers, defenders) + 1;
            this.outcomes = new int[numPossibleScenarios][2];
        }

        private Attack() {
        }

        /**
         * Calcula todas os cenários possíveis de ataque, retornando um array com as perdas de exércitos
         * de cada lado. Ex: Para um ataque com 3 atacantes e 2 defensores, os cenários são:
         * - atk ganha 2 ou 3 dados e elimina 2 def
         * - atk ganha 1 dado e cada lado perde 1
         * - atk perde em todos os dados e perde 2 exércitos
         */
        public int[][] calculateAttack() {
            int numLosses = Math.min(numberAttackers, numberDefenders);
            for (int i = 0; i <= numLosses; i++) {
                int attackerLosses = i;
                int defenderLosses = numLosses - i;
                outcomes[i] = new int[]{attackerLosses, defenderLosses};
            }
            return outcomes;
        }

        public int getNumberAttackers() {
            return numberAttackers;
        }

        public int getNumberDefenders() {
            return numberDefenders;
        }

        public int[][] getOutcomes() {
            return outcomes;
        }
    }
}
