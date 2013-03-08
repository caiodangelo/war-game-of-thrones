package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import main.Territory;

/**
 *
 * @author rodrigo
 */
public abstract class Player implements Serializable {

    private String name;
    private int totalPendingArmies; // Número total de exércitos que ele tem mas ainda não posicionou
    private int generalPendingArmies; // Número de exércitos que ele pode colocar em qualquer territrio mas ainda não posicionou
    private HashMap<Region, Integer> pendingArmiesForRegion; // Número de exércitos que ele só pode colocar em cada região mas ainda não posicionou
    private House house;
    private Mission mission;
    private List<BackEndTerritory> territories;
    private List<CardTerritory> cards;
    private StatisticPlayerManager statistic;
    private boolean mayReceiveCard;

    public Player(String name) {
        this.name = name;
        this.totalPendingArmies = 0;
        this.generalPendingArmies = 0;
        this.pendingArmiesForRegion = new HashMap<Region, Integer>();
        Board board = Board.getInstance();
        for (int i = 0; i < board.getRegions().length; i++) {
            this.pendingArmiesForRegion.put(board.getRegions()[i], 0);
        }
        this.territories = new ArrayList<BackEndTerritory>();
        this.cards = new ArrayList<CardTerritory>();
        this.statistic = new StatisticPlayerManager(this);
        this.mayReceiveCard = false;
    }

    public Player(String name, House house) {
        this(name);
        this.house = house;
        house.setPlayer(this);
    }

    public abstract boolean isAIPlayer();

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

    public int getTotalPendingArmies() {
        return totalPendingArmies;
    }

    public int getGeneralPendingArmies() {
        return generalPendingArmies;
    }

    public HashMap<Region, Integer> getPendingArmiesForRegion() {
        return pendingArmiesForRegion;
    }

    public void setPendingArmiesForRegion(Region region, int armies) {
        this.pendingArmiesForRegion.put(region, armies);
    }

    public void addTotalPendingArmies(int amount) {
        this.totalPendingArmies += amount;
        this.statistic.increaseReceivedArmies(amount);
    }

    public void addGeneralPendingArmies(int amount) {
        this.generalPendingArmies += amount;
    }

    public void removePendingArmies(BackEndTerritory territory, int amount) {
        Region r = territory.getRegion();
        int numArmies = this.pendingArmiesForRegion.get(r);
        if (numArmies > 0) {
            this.pendingArmiesForRegion.put(r, numArmies - amount);
        } else
            this.generalPendingArmies -= amount;
        this.totalPendingArmies -= amount;
    }

    public List<BackEndTerritory> getTerritories() {
        return territories;
    }

    public List<BackEndTerritory> getTerritoriesThatCanAttack() {
        List<BackEndTerritory> attackables = new ArrayList<BackEndTerritory>();
        for (BackEndTerritory territory : territories) {
            if (territory.getSurplusArmies() >= 1) {
                attackables.add(territory);
            }
        }
        return attackables;
    }

    public List<CardTerritory> getCards() {
        return cards;
    }

    public StatisticPlayerManager getStatisticPlayerManager() {
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

    public void addArmiesInTerritory(BackEndTerritory territory) {
        territory.increaseArmies(totalPendingArmies);
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
        if (totalPendingArmies >= amount && target.getOwner() == this) {
            target.increaseArmies(amount);
            totalPendingArmies -= amount;
            return true;
        }
        return false;
    }

    public List<Region> getMoreSubduedRegion() {
        List<Region> answer = new ArrayList<Region>();
        int current = 0;
        int more = 0;
        Region[] regions = Board.getInstance().getRegions();
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
