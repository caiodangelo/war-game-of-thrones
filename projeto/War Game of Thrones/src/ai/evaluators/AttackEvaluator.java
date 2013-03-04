/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.evaluators;

import ai.BattleComputer;
import ai.Evaluator;
import ai.Feature;
import ai.features.ArmiesFeature;
import ai.features.BestEnemyFeature;
import ai.features.ContinentDominationFeature;
import ai.features.ContinentThreatFeature;
import ai.features.EnemyEstimatedReinforcementsFeature;
import ai.features.EnemyOccupiedContinentsFeature;
import ai.features.HinterlandFeature;
import ai.features.MissionCompletionFeature;
import ai.features.MoreThanOneArmyFeature;
import ai.features.OccupiedTerritoriesFeature;
import ai.features.OwnEstimatedReinforcementsFeature;
import ai.features.OwnOccupiedContinentsFeature;
import ai.features.OwnOccupiedRiskCardTerritoriesFeature;
import ai.features.RiskCardsFeature;
import java.util.ArrayList;
import java.util.List;
import models.Board;
import models.Player;
import models.TerritoryTransaction;

/**
 *
 * @author rodrigo
 */
public class AttackEvaluator extends Evaluator {

    private TerritoryTransaction attack;
    private List<Board> outcomes;
    private double[] probabilities;

    public AttackEvaluator(Board currentGameState, Player player) {
        super(currentGameState, player);
        outcomes = new ArrayList<Board>();
        features.add(new ArmiesFeature());
        features.add(new BestEnemyFeature());
        features.add(new ContinentThreatFeature());
        features.add(new EnemyEstimatedReinforcementsFeature());
        features.add(new EnemyOccupiedContinentsFeature());
        features.add(new HinterlandFeature());
        features.add(new MoreThanOneArmyFeature());
        features.add(new OccupiedTerritoriesFeature());
        features.add(new OwnEstimatedReinforcementsFeature());
        features.add(new OwnOccupiedContinentsFeature());
        features.add(new OwnOccupiedRiskCardTerritoriesFeature());
        features.add(new RiskCardsFeature());
        features.add(new ContinentDominationFeature());
        features.add(new MissionCompletionFeature());
    }

    @Override
    protected Board simulateActionExecution() {
        // Este método não será utilizado, pois no ataque será simulado
        // vários GameStates ao mesmo tempo.
        return null;
    }

    private void simulateMultiActionExecution() {
        outcomes = BattleComputer.generateAttackOutcomes(currentGameState, attack.attacker, attack.defender, attack.numberOfAttackers);
        probabilities = BattleComputer.generateAttackOutcomeProbabilities(attack.numberOfAttackers, Math.min(3, attack.defender.getNumArmies()));
    }

    /**
     * O evaluate do ataque vai simular o resultado de todas as possibilidades que podem sair de um ataque
     * e vai fazer uma média ponderada com a probabilidade de cada resultado, para avaliar se vale a pena
     * atacar ou não, ou seja, se a probabilidade média é melhor do que o gamestate atual.
     */
    @Override
    public double evaluate() {
        try {
        double rating = 0.0;
        simulateMultiActionExecution();
        for (int i = 0; i < outcomes.size(); i++) {
            Board gameState = outcomes.get(i);
            double probability = probabilities[i];
            for (Feature feature : features) {
                double grade = 0;
                double importanceSum = 0;
                Player simulatedPlayer = null;
                for (Player newPlayer : gameState.getPlayers()) {
                    if (newPlayer.getHouse().getName().equals(player.getHouse().getName())) {
                        simulatedPlayer = newPlayer;
                    }
                }
                grade += feature.calculateScaledGrade(gameState, simulatedPlayer);
                importanceSum += feature.getImportance();
                rating += (grade / importanceSum) * probability;
            }
        }
        simulatedRating = rating;
        return simulatedRating;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Deu merda");
            return 0.0;
        }
    }

    public TerritoryTransaction getAttack() {
        return attack;
    }

    public void setAttack(TerritoryTransaction attack) {
        this.attack = attack;
    }
}