/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author rodrigo
 */
public abstract class Player {

    private String name;
    private int pendingArmies; // Número de exércitos que ele tem mas ainda não posicionou
    private House house;

    public Player(String name) {
        this.name = name;
        this.pendingArmies = 0;
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
}