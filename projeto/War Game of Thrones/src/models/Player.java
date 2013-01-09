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
    private List<Army> armies;
    private List<CardTerritory> cards;

    public Player(String name) {
        this.name = name;
        this.pendingArmies = 0;
        this.territories = new ArrayList<Territory>();
        this.armies = new ArrayList<Army>();
        this.cards = new ArrayList<CardTerritory>();
    }

    public Player(String name, House house) {
        this(name);
        this.house = house;
    }
    
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

    public List<Army> getArmies() {
        return armies;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public List<CardTerritory> getCards() {
        return cards;
    }

    public void addArmy(Army army) {
        armies.add(army);
    }

    public void addTerritory(Territory territory) {
        territories.add(territory);
    }

    public void addCard(CardTerritory card) {
        cards.add(card);
    }

    public void removeArmy(Army army) {
        armies.remove(army);
    }

    public void removeTerritory(Territory territory) {
        territories.remove(territory);
    }

    public void removeCard(CardTerritory card) {
        cards.remove(card);
    }

}