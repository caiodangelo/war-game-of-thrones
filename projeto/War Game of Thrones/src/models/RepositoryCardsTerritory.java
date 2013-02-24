/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import sun.awt.resources.awt;

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

    public void initialRaffle(Board board) {
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
                Board.getInstance().increaseNumberOfSwaps();
                numberOfSwaps = Board.getInstance().getNumberOfSwaps();
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