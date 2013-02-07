package models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rodcastro
 *
 */
public class MediumAI extends Difficulty {

    @Override
    public void distributeArmies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean keepAttacking() {
        throw new UnsupportedOperationException("Not supported yet.");
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
                    for (int i = 0; i < chosenTypes.length; i++)
                        chosenTypes[i] = false;
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
    public TerritoryTransaction nextAttack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int moveAfterConquest(Territory origin, Territory conquered, int numberCanMove) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean keepMoving() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TerritoryTransaction nextMove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
