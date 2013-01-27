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
    protected int numArmies;
    protected int numArmiesCanMoveThisRound;

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
     * Diz que X exércitos já foram movidos naquela jogada. Retorna false se o
     * número resultante for menor que zero, e não permite a movimentação.
     *
     * @param amount
     */
    protected boolean setMovedArmies(int amount) {
        int remaining = numArmiesCanMoveThisRound - amount;
        if (remaining >= 0) {
            numArmiesCanMoveThisRound = remaining;
            return true;
        }
        return false;
    }
    
    protected void resetMovedArmies() {
        numArmiesCanMoveThisRound = numArmies - 1;
    }
    
    public int getNumArmiesCanMoveThisRound() {
        return numArmiesCanMoveThisRound;
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
