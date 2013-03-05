package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public abstract class Player implements Serializable {

    private String name;
    private int pendingArmies; // Número de exércitos que ele tem mas ainda não posicionou
    private House house;
    private Mission mission;
    private List<BackEndTerritory> territories;
    private List<CardTerritory> cards;
    private StatisticPlayerManager statistic;
    private boolean mayReceiveCard;

    public Player(String name) {
        this.name = name;
        this.pendingArmies = 0;
        this.territories = new ArrayList<BackEndTerritory>();
        this.cards = new ArrayList<CardTerritory>();
        this.statistic = new StatisticPlayerManager(this);
        this.mayReceiveCard = false;
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

    public boolean mayReceiveCard() {
        return mayReceiveCard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPendingArmies() {
        return pendingArmies;
    }

    public void addPendingArmies(int amount) {
        this.pendingArmies += amount;
        this.statistic.increaseReceivedArmies(amount);
    }

    public void removePendingArmies(int amount) {
        this.pendingArmies -= amount;
    }

    public List<BackEndTerritory> getTerritories() {
        return territories;
    }

    public List<CardTerritory> getCards() {
        return cards;
    }
    
    public StatisticPlayerManager getStatisticPlayerManager () {
        return statistic;
    }

    public void setMayReceiveCard(boolean mayReceiveCard) {
        this.mayReceiveCard = mayReceiveCard;
    }
    
    public int numArmies() {
        int count = 0;
        for (BackEndTerritory territory : this.getTerritories()) {
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
            
    public void addTerritory(BackEndTerritory territory) {
        territories.add(territory);
        territory.setOwner(this);
    }
    
    public void addArmiesInTerritory (BackEndTerritory territory) {
        territory.increaseArmies(pendingArmies);
    }

    public void addCard(CardTerritory card) {
        cards.add(card);
    }

    public void removeTerritory(BackEndTerritory territory) {
        territories.remove(territory);
    }

    public void removeCard(CardTerritory card) {
        cards.remove(card);
    }

    public boolean moveArmies(BackEndTerritory origin, BackEndTerritory target, int amount) {
        if (origin.getOwner() == this && target.getOwner() == this && origin.isNeighbour(target)) {
            return origin.transferArmies(target, amount);
        }
        return false;
    }

    /**
     * Distribui os exércitos pendentes para um território, desde que tenha exércitos suficientes
     * e que o território escolhido seja do jogador.
     */
    public boolean distributeArmies(BackEndTerritory target, int amount) {
        if (pendingArmies >= amount && target.getOwner() == this) {
            target.increaseArmies(amount);
            pendingArmies -= amount;
            return true;
        }
        return false;
    }
    
    public List<Region> getMoreSubduedRegion() {
        List<Region> answer = new ArrayList<Region>();
        int current = 0;
        int more = 0;
        Region [] regions = Board.getInstance().getRegions();
        for (int i = 0; i < regions.length; i++) {
            for (BackEndTerritory territory : regions[i].getTerritories()) {
                if (territory.getOwner() == this)
                    current++;
            }
            if (more < current) {
                answer.add(regions[i]);
                more = current;
            }
        }
        return answer;
    }
}