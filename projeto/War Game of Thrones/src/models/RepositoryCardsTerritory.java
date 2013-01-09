/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author anderson
 */
public class RepositoryCardsTerritory {

    protected LinkedList<CardTerritory> deck;
    protected LinkedList<CardTerritory> repository;
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
}