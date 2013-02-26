/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author anderson
 */
public class RepositoryCardsTerritory {

    protected LinkedList<CardTerritory> deck; //São as cartas que estão no jogo e q não foram escolhidas pelos jogadores
    protected LinkedList<CardTerritory> repository; // Cartas no limbo
    protected static RepositoryCardsTerritory instance;

    protected RepositoryCardsTerritory() {
        deck = new LinkedList<CardTerritory>();
            BackEndTerritory allTerritories[] = Board.getInstance().getTerritories();
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.BARROWLANDS]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.COSTA_BRAVOSIANA]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.COSTA_LARANJA]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.COSTA_PEDREGOSA]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.DESERTO_VERMELHO]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.DORNE]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.FLORESTA_ASSOMBRADA]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.ROCHEDO_CASTERLY]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.PORTO_REAL]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.SKAGOS]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.TERRAS_DA_TEMPESTADE]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.THE_FLATLANDS]));
            deck.add(new CardTerritory(CardTerritory.SQUARE,allTerritories[TerritoryID.VALE_DE_ARRYN]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.CAPE_KRAKENTT]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.COLINAS_DE_NORVOS]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.COSTA_GELADA]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.ILHA_DOS_URSOS]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.JARDIM_DE_CIMA]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.MAR_DOTHRAKI]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.MONTE_CHIFRE]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.OROS]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.TARTH]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.TERRAS_FLUVIAIS]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.THE_HILLS]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.VALIRIA]));
            deck.add(new CardTerritory(CardTerritory.CIRCLE,allTerritories[TerritoryID.WINTERFELL]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.ARVORE]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.A_DADIVA]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.CAMPOS_DOURADOS]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.FLORESTA_DE_QOHOR]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.FOOTPRINT]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.GARGALO]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.GHISCAR]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.MATA_DE_LOBOS]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.MATA_DO_REI]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.MONTANHAS_DA_LUA]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.SEMPRE_INVERNO]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.PENHASCO_SOMBRIO]));
            deck.add(new CardTerritory(CardTerritory.TRIANGLE,allTerritories[TerritoryID.TERRAS_DISPUTADAS]));
            deck.add(new CardTerritory(CardTerritory.JOKER,null));
            deck.add(new CardTerritory(CardTerritory.JOKER,null));
        repository = new LinkedList<CardTerritory>();
    }

    public static RepositoryCardsTerritory getInstance() {
        if (instance == null) {
            instance = new RepositoryCardsTerritory();
        }
        return instance;
    }

    public LinkedList<CardTerritory> getRepository() {
        return repository;
    }

    public void setRepository(LinkedList<CardTerritory> repository) {
        this.repository = repository;
    }

    public LinkedList<CardTerritory> getDeck() {
        return deck;
    }

    public void setDeck(LinkedList<CardTerritory> deck) {
        this.deck = deck;
    }

    public void addCardToDeck(CardTerritory card) {
        deck.add(card);
        shuffleCards();
    }

    public void addCardToRepository(CardTerritory card) {
        repository.add(card);
    }

    public void shuffleCards() {
        Collections.shuffle(deck);
    }

    public CardTerritory getFirstCardFromDeck() {
        CardTerritory card = deck.removeFirst();
        addCardToRepository(card);
        return card;
    }

    public void initialRaffle() {
        Board board = Board.getInstance();
        int size = board.getPlayers().size();
        removeJokers();
        shuffleCards();

        while (deck.size() != 0) {
            for (int i = size - 1; i >= 0; i--) {
                if (deck.size() != 0) {
                    Player p = board.getPlayer(i);
                    p.addCard(getFirstCardFromDeck());
                }
            }
        }
        deck = getRepository();
        setRepository(new LinkedList());
    }

    public void removeJokers() {
        CardTerritory cardJoker;
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getType() == CardTerritory.JOKER) {
                cardJoker = deck.remove(i);
                addCardToRepository(cardJoker);
                i--;
            }
        }
    }

    public boolean swapCards(List<CardTerritory> cardsToSwap, Player player) {
        int numberOfSwaps, numberOfArmies;
        if (player.isMaySwapCards()) {
            if ((isSameCards(cardsToSwap)) || (isDifferentCards(cardsToSwap))) {
                for (CardTerritory card : cardsToSwap) {
                    player.removeCard(card);
                    for (int i = 0; i < player.getTerritories().size(); i++) {
                        if (player.getTerritories().get(i).equals(card.getTerritory()))
                            player.getTerritories().get(i).increaseArmies(2);
                    }
                    this.addCardToDeck(card);
                }
                player.getStatisticPlayerManager().increaseNumberOfCardsSwapped();
                numberOfSwaps = player.getStatisticPlayerManager().getNumberOfCardsSwapped();
                numberOfArmies = consultSwapTable(numberOfSwaps);
                player.addPendingArmies(numberOfArmies);
                return true;
            }
        }
        return false;
    }

    public boolean isSameCards(List<CardTerritory> cards) {
        int typeCard = 0;
        List<CardTerritory> aux = cards;
        for (int i = 0; i < aux.size(); i++) {
            typeCard = aux.get(i).getType();
            if (typeCard != CardTerritory.JOKER) {
                aux.remove(i);
                break;
            }
        }

        for (CardTerritory card : aux) {
            if ((card.getType() != typeCard) && (card.getType() != CardTerritory.JOKER)) {
                return false;
            }
        }
        return true;
    }

    public boolean isDifferentCards(List<CardTerritory> cards) {
        int typeCard = 0;
        List<CardTerritory> aux = cards;
        for (int i = 0; i < aux.size(); i++) {
            typeCard = aux.get(i).getType();
            if (typeCard != CardTerritory.JOKER) {
                aux.remove(i);
                break;
            }
        }
     
        for (CardTerritory card : aux) {
            if ((card.getType() == typeCard) && (card.getType() != CardTerritory.JOKER)) {
                return false;
            }
            typeCard = card.getType();
        }
        return true;
    }

    public int consultSwapTable(int numberOfSwaps) {
        if ((numberOfSwaps >= 1) && (numberOfSwaps <= 5)) {
            return (numberOfSwaps * 2) + 2;
        } else if (numberOfSwaps == 6) {
            return 15;
        } else {
            return ((numberOfSwaps - 6) * 5) + 15;
        }
    }
}