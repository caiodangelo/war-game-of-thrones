package models;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Territory {

    private String name;
    private Region region;
    private Player owner;
    private int numArmies;

    public Territory(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public Territory(String name, Region region, Player player) {
        this.name = name;
        this.region = region;
        this.owner = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getNumArmies() {
        return numArmies;
    }

    public void setNumArmies(int numArmies) {
        this.numArmies = numArmies;
    }

    protected void increaseArmies(int amount) {
        numArmies += amount;
    }

    protected void decreaseArmies(int amount) {
        numArmies -= amount;
    }

    /**
     * Transfere exércitos deste território para outro, apenas se este
     * território ficar com pelo menos um exército após o movimento.
     */
    public boolean transferArmies(Territory target, int amount) {
        if (this.numArmies > amount) {
            target.increaseArmies(amount);
            this.decreaseArmies(amount);
            return true;
        }
        return false;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Verifica se um território é vizinho deste
     */
    public boolean isNeighbour(Territory another) {
        return true;
    }
}
