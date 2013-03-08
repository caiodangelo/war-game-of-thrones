package models;

import ai.BattleComputer;
import ai.evaluators.AttackEvaluator;
import ai.evaluators.DistributionEvaluator;
import ai.evaluators.MovementEvaluator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class ExtremeAI extends Difficulty implements Serializable {

    @Override
    public HashMap<BackEndTerritory, Integer> distributeArmies() {
        // First of all, set the new targetRegion for the Player.
        // The targetRegion is the best (in general, the easiest to conquer) region
        // that's not currently owned by the player.
        HashMap<BackEndTerritory, Integer> distribution = new HashMap<BackEndTerritory, Integer>();
        List<Region> regionsNotOwnedByPlayer = new ArrayList<Region>();
        for (Region region : Board.getInstance().getRegions()) {
            if (!region.conqueredByPlayer(player))
                regionsNotOwnedByPlayer.add(region);
        }
        double maxRegionRating = Double.NEGATIVE_INFINITY;
        for (Region region : regionsNotOwnedByPlayer) {
            double newRating = region.getAdjustedRating(player);
            maxRegionRating = Math.max(maxRegionRating, newRating);
            if (maxRegionRating == newRating) {
                player.setTargetRegion(region);
            }
        }
        // Now we can distribute our armies
        while (player.getTotalPendingArmies() > 0) {
            BackEndTerritory chosen = null;
            double maxRating = 0.0;
            for (BackEndTerritory territory : player.getTerritories()) {
                DistributionEvaluator evaluator = new DistributionEvaluator(Board.getInstance(), player);
                evaluator.setTerritory(territory);
                double rating = evaluator.evaluate();
                maxRating = Math.max(maxRating, rating);
                if (maxRating == rating) {
                    chosen = territory;
                }
            }
            if (chosen != null) {
                player.distributeArmies(chosen, 1);
                increaseTerritoryDistribution(distribution, chosen, 1);
            }
        }
        return distribution;
    }

    @Override
    protected boolean keepAttacking() {
        // Essa verificação é muito custosa, e deve ser feita 2 vezes (aqui e no nextAttack), então
        // é mais fácil dizer que a IA quer sempre atacar, e caso ela não queira, retornar null no
        // nextAttack.
        return true;
    }

    @Override
    public List<CardTerritory> tradeCards() {
        if (player.numCards() >= 3) {
            List<CardTerritory> cards = new ArrayList<CardTerritory>();
            int triangleCards = 0, circleCards = 0, squareCards = 0, jokerCards = 0;
            for (CardTerritory card : player.getCards()) {
                switch (card.getType()) {
                    case CardTerritory.CIRCLE:
                        circleCards++;
                        break;
                    case CardTerritory.TRIANGLE:
                        triangleCards++;
                        break;
                    case CardTerritory.SQUARE:
                        squareCards++;
                        break;
                    case CardTerritory.JOKER:
                        jokerCards++;
                        break;
                }
            }
            int selectedType = getCardSelectedType(triangleCards, circleCards, squareCards, jokerCards);
            switch (selectedType) {
                case 1:
                case 2:
                case 3:
                case 4:
                    for (CardTerritory card : player.getCards()) {
                        if (card.getType() == selectedType) {
                            cards.add(card);
                        }
                    }
                    if (cards.size() >= 3)
                        return cards;
                    break;
                case 5:
                    boolean[] chosenTypes = new boolean[4];
                    for (int i = 0; i < chosenTypes.length; i++) {
                        chosenTypes[i] = false;
                    }
                    for (CardTerritory card : player.getCards()) {
                        if (!chosenTypes[card.getType() - 1]) {
                            cards.add(card);
                            chosenTypes[card.getType() - 1] = true;
                        }
                    }
                    if (cards.size() >= 3)
                        return cards;
                    break;
            }
        }
        return null; // Não conseguiu arranjar nenhum esquema de troca
    }

    @Override
    public int moveAfterConquest(BackEndTerritory origin, BackEndTerritory conquered, int numberCanMove) {
        int numberToMove = 1;
        numberCanMove--;
        while (numberCanMove > 0) {
            MovementEvaluator evaluator = new MovementEvaluator(Board.getInstance(), player);
            evaluator.setOriginTerritory(origin);
            evaluator.setDestinyTerritory(conquered);
            double rating = evaluator.evaluate();
            if (rating > evaluator.evaluateCurrentGameState()) {
                numberToMove++;
            }
            numberCanMove--;
        }
        return numberToMove;
    }

    @Override
    protected boolean keepMoving() {
        // Essa verificação é muito custosa, e deve ser feita 2 vezes (aqui e no nextMove), então
        // é mais fácil dizer que a IA quer sempre mover, e caso ela não queira, retornar null no
        // nextMove.
        return true;
    }

    @Override
    public TerritoryTransaction nextMove() {
        BackEndTerritory origin = null;
        BackEndTerritory destiny = null;
        double maxRating = Double.NEGATIVE_INFINITY;
        double currentRating = Double.NEGATIVE_INFINITY;
        for (BackEndTerritory territory : player.getTerritories()) {
            if (territory.getNumArmiesCanMoveThisRound() >= 1) {
                for (BackEndTerritory neighbour : territory.getNeighbours()) {
                    if (neighbour.getOwner() == player) {
                        MovementEvaluator evaluator = new MovementEvaluator(Board.getInstance(), player);
                        evaluator.setOriginTerritory(territory);
                        evaluator.setDestinyTerritory(neighbour);
                        double rating = evaluator.evaluate();
                        maxRating = Math.max(maxRating, rating);
                        if (maxRating == rating) {
                            origin = territory;
                            destiny = neighbour;
                        }
                        if (currentRating == 0.0) {
                            currentRating = evaluator.evaluateCurrentGameState();
                        }
                    }
                }
            }
        }
        if (maxRating > currentRating) {
            return new TerritoryTransaction(origin, destiny, 1);
        } else {
            return null; // Se não for melhor mover, retorna null e deixa tudo como está.
        }
    }

    @Override
    public TerritoryTransaction nextAttack() {
        List<TerritoryTransaction> attacks = new ArrayList<TerritoryTransaction>();
        for (BackEndTerritory territory : player.getTerritories()) {
            if (territory.getSurplusArmies() > 0) {
                for (BackEndTerritory neighbour : territory.getNeighbours()) {
                    if (neighbour.getOwner() != player) {
                        TerritoryTransaction attack = new TerritoryTransaction(territory, neighbour, Math.min(3, territory.getSurplusArmies()));
                        if (attack.isValid())
                            attacks.add(attack);
                    }
                }
            }
        }
        System.out.println("AI has " + attacks.size() + " possible attacks to make");
        double maxRating = Double.NEGATIVE_INFINITY;
        double currentRating = Double.NEGATIVE_INFINITY;
        TerritoryTransaction chosenAttack = null;
        for (TerritoryTransaction attack : attacks) {
            AttackEvaluator evaluator = new AttackEvaluator(Board.getInstance(), player);
            evaluator.setAttack(attack);
            double rating = evaluator.evaluate();
            maxRating = Math.max(maxRating, rating);
            if (maxRating == rating) {
                currentRating = evaluator.evaluateCurrentGameState();
                chosenAttack = attack;
            }
        }
        if (chosenAttack != null) {
            double battleChanceBonus = 1 + (BattleComputer.calculateThreatToTerritory(chosenAttack.attacker, chosenAttack.defender) - 0.500);
            if (battleChanceBonus > 1)
                if (maxRating < 0)
                    maxRating /= battleChanceBonus;
                else
                    maxRating *= battleChanceBonus;
            if (maxRating > currentRating) {
                return chosenAttack;
            }
        }
        return null;
    }
}
