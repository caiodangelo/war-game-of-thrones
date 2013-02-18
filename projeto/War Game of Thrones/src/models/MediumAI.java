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
                    if (cards.size() >= 3) {
                        return cards;
                    }
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
                    if (cards.size() >= 3) {
                        return cards;
                    }
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
        if (origin.getNumArmies() >= 6) {
            return numberCanMove;
        }
        if (origin.getNumArmies() <= 3) {
            return 1;
        }
        if ((origin.getNumArmies() == 4) || (origin.getNumArmies() == 5)) {
            if (2 > numberCanMove) {
                return numberCanMove;
            }
        }
        return 2;
    }

    @Override
    protected boolean keepMoving() {
        /*O jogador continuará a movimentação se tiver um territorio com 1 exercito e esse territorio
         * tiver algum vizinho cujo proprietario seja o proprio jogador, desde que esse territorio vizinho
         * tenha exercitos para movimentar, para que a movimentação seja util
         */
        for (Territory myTerritory : player.getTerritories()) {
            if (myTerritory.getNumArmies() == 1) {
                List<Territory> neighbours = myTerritory.getNeighbours();
                for (Territory territory : neighbours) {
                    if ((territory.getOwner().equals(player)) && (territory.getNumArmiesCanMoveThisRound() >= 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TerritoryTransaction nextMove() {
        int numberFriendNeighbours = 0;
        int dangerousEnemyArmies = 0;
        int numArmies;
        if (keepMoving()) {
            Territory origin;
            Territory destiny;
            for (Territory territory : player.getTerritories()) {
                if (keepMoving() && territory.getNumArmiesCanMoveThisRound() >= 1) {
                    for (Territory neighbour : territory.getNeighbours()) {
                        if (neighbour.getOwner().equals(player)) {
                            numberFriendNeighbours++;
                        } else {
                            if (dangerousEnemyArmies < neighbour.getNumArmies()) {
                                dangerousEnemyArmies = neighbour.getNumArmies();
                            }
                        }
                    }
                    //Se houver algum inimigo com um num de exercitos muito grande (razao > 1,5), AI player não fará a movimentação
                    if ((territory.getNumArmies() / dangerousEnemyArmies) <= 1.5) {
                        for (Territory neighbour : territory.getNeighbours()) {
                            if (neighbour.getOwner().equals(player) && neighbour.getNumArmies() <= territory.getNumArmies()) {
                                origin = territory;
                                destiny = neighbour;

                                if (numberFriendNeighbours == territory.getNeighbours().size()) {
                                    //Se o territorio estiver cercado de vizinhos amigos, ele distribui exercitos igualmente entre eles
                                    //dexando-o com o min possivel de exercitos
                                    numArmies = (int) Math.ceil(origin.getNumArmiesCanMoveThisRound() / numberFriendNeighbours);
                                    return new TerritoryTransaction(origin, destiny, numArmies);
                                } else {
                                    //Caso contrario, ele tenta dividir os exercitos em igualdade entre eles
                                    numArmies = (int) origin.getNumArmies() / numberFriendNeighbours + 1;
                                    if (numArmies > origin.getNumArmiesCanMoveThisRound()) {
                                        numArmies = origin.getNumArmiesCanMoveThisRound();
                                    }
                                    return new TerritoryTransaction(origin, destiny, numArmies);
                                }

                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
