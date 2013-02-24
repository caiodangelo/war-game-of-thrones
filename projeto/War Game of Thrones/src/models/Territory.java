package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import prefuse.data.Node;
import util.TerritoriesGraphStructure;

/**
 *
 * @author rodrigo
 */
public class Territory implements Serializable {

    private int index;
    private String name;
    private Region region;
    private Player owner;
    private Board board;
    private TerritoriesGraphStructure graph;
    protected int numArmies;
    protected int numArmiesCanMoveThisRound;
    protected int numAttacks;  //Num de vezes que o territorio foi atacado
    protected int numConquests;  //Num de vezes que o territorio foi conquistado

    public Territory(int index, TerritoriesGraphStructure strct) {
        this.index = index;
        this.graph = strct;
        this.board = Board.getInstance();
    }

    public Territory(String name, Region region) {
        this.name = name;
        this.region = region;
        this.board = Board.getInstance();
    }

    public Territory(String name, Region region, Player player) {
        this.name = name;
        this.region = region;
        this.owner = player;
        this.board = Board.getInstance();
    }

    public String getName() {
        return graph.getNode(index).getString("name");
    }

//    public void setName(String name) {
//        this.name = name;
//    }
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

    public int getNumAttacks() {
        return numAttacks;
    }

    public int getNumConquests() {
        return numConquests;
    }

    protected void increaseArmies(int amount) {
        numArmies += amount;
    }

    protected void decreaseArmies(int amount) {
        numArmies -= amount;
    }

    public void increaseNumConquests() {
        numConquests++;
    }

    public void increaseNumAttacks() {
        numAttacks++;
    }

    /**
     * Pega o número de exércitos - 1
     */
    public int getSurplusArmies() {
        return numArmies - 1;
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
        String otherName = another.getName();
        Iterator neighbours = graph.getNeighborsIterator(index);
        while (neighbours.hasNext()) {
            Node next = (Node) neighbours.next();
            if (next.getString("name").equals(otherName))
                return true;
        }
        return false;
    }

    public List<Territory> getNeighbours() {
        Territory[] allTerritories = board.getTerritories();
        List<Territory> resp = new ArrayList<Territory>();
        System.out.println("running for in all territories");
        for (Territory t : allTerritories) {
            if (!t.equals(this) && t.isNeighbour(this))
                resp.add(t);
        }
        return resp;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Territory) {
            Territory tOther = (Territory) other;
            return tOther.index == this.index;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Territory: " + getName() + " from " + region;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}