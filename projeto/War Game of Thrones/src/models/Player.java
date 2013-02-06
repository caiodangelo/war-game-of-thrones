package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public abstract class Player {

    private String name;
    private int pendingArmies; // Número de exércitos que ele tem mas ainda não posicionou
    private House house;
    private Mission mission;
    private List<Territory> territories;
    private List<CardTerritory> cards;
    private StatisticPlayerManager statistic;

    public Player(String name) {
        this.name = name;
        this.pendingArmies = 0;
        this.mission = new Mission();
        this.territories = new ArrayList<Territory>();
        this.cards = new ArrayList<CardTerritory>();
        this.statistic = new StatisticPlayerManager();
    }

    public Player(String name, House house) {
        this(name);
        this.house = house;
    }
    
    public abstract boolean isAIPlayer ();

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPendingArmies() {
        return pendingArmies;
    }

    public void setPendingArmies(int pendingArmies) {
        this.pendingArmies = pendingArmies;
    }

    public void addPendingArmies(int amount) {
        this.pendingArmies += amount;
    }

    public void removePendingArmies(int amount) {
        this.pendingArmies -= amount;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public List<CardTerritory> getCards() {
        return cards;
    }
    
    public StatisticPlayerManager getStatisticPlayerManager () {
        return statistic;
    }
    
    public int numArmies() {
        int count = 0;
        for (Territory territory : this.getTerritories()) {
            count += territory.getNumArmies();
        }
        return count;
    }
    
    public int numCards() {
        return this.getCards().size();
    }
    
    public int numTerritories() {
        return this.getTerritories().size();
    }
            
    public void addTerritory(Territory territory) {
        territories.add(territory);
    }

    public void addCard(CardTerritory card) {
        cards.add(card);
    }

    public void removeTerritory(Territory territory) {
        territories.remove(territory);
    }

    public void removeCard(CardTerritory card) {
        cards.remove(card);
    }

    public boolean moveArmies(Territory origin, Territory target, int amount) {
        if (origin.getOwner() == this && target.getOwner() == this && origin.isNeighbour(target)) {
            return origin.transferArmies(target, amount);
        }
        return false;
    }

    /**
     * Distribui os exércitos pendentes para um território, desde que tenha exércitos suficientes
     * e que o território escolhido seja do jogador.
     */
    public boolean distributeArmies(Territory target, int amount) {
        if (pendingArmies >= amount && target.getOwner() == this) {
            target.increaseArmies(amount);
            pendingArmies -= amount;
            return true;
        }
        return false;
    }
}