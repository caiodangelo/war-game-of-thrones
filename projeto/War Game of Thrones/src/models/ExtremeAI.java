package models;

import ai.DistributionEvaluator;
import ai.MovementEvaluator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class ExtremeAI extends Difficulty {

    @Override
    public void distributeArmies() {
        while (player.getPendingArmies() > 0) {
            Territory chosen = null;
            double maxRating = 0.0;
            for (Territory territory : player.getTerritories()) {
                DistributionEvaluator evaluator = new DistributionEvaluator(Board.getInstance(), player);
                evaluator.setTerritory(territory);
                double rating = evaluator.evaluate();
                maxRating = Math.max(maxRating, rating);
                if (maxRating == rating) {
                    chosen = territory;
                }
            }
            player.distributeArmies(chosen, 1);
        }
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
    public int moveAfterConquest(Territory origin, Territory conquered, int numberCanMove) {
        int numberToMove = 0;
        while (numberCanMove > 0) {
            MovementEvaluator evaluator = new MovementEvaluator(Board.getInstance(), player);
            evaluator.setOriginTerritory(origin);
            evaluator.setDestinyTerritory(conquered);
            double rating = evaluator.evaluate();
            if (rating > evaluator.evaluateCurrentGameState()) {
                numberToMove++;
                numberCanMove--;
            }
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
        Territory origin = null;
        Territory destiny = null;
        double maxRating = 0.0;
        double currentRating = 0.0;
        for (Territory territory : player.getTerritories()) {
            if (territory.getNumArmiesCanMoveThisRound() >= 1) {
                for (Territory neighbour : territory.getNeighbours()) {
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
        }
        else {
            return null; // Se não for melhor mover, retorna null e deixa tudo como está.
        }
    }

    @Override
    public TerritoryTransaction nextAttack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}