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

    public void increaseArmies(int amount) {
        numArmies += amount;
    }

    public void decreaseArmies(int amount) {
        numArmies -= amount;
    }

    public void increaseNumConquests() {
        numConquests++;
    }

    public void increaseNumAttacks() {
        numAttacks++;
    }

    /**
     * Pega o número de exércitos - 1. Serve para indicar quantos exércitos o jogador tem
     * de sobra para poder atacar.
     */
    public int getSurplusArmies() {
        return numArmies - 1;
    }

    /**
     * Diz que X exércitos já foram movidos naquela jogada. Retorna false se o
     * número resultante for menor que zero, e não permite a movimentação.
     *
     * @param amount O número de exércitos que foi movido deste território
     */
    protected boolean setMovedArmies(int amount) {
        int remaining = numArmiesCanMoveThisRound - amount;
        if (remaining >= 0) {
            numArmiesCanMoveThisRound = remaining;
            return true;
        }
        return false;
    }

    /**
     * Reseta o número de exércitos que podem se movimentar deste território para outro.
     * Deve ser chamado no início da rodada do jogador.
     */
    protected void resetMovedArmies() {
        numArmiesCanMoveThisRound = numArmies - 1;
    }

    /**
     * Retorna o número de exércitos que podem se movimentar para outro território
     * nesta rodada. Este número é decrementado para cada exército movido deste
     * território, de modo que um exército que seja movido para este território
     * não possa ser movido novamente na mesma rodada
     */
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

    /**
     * Retorna uma lista com todos os territórios que fazem fronteira ou tem
     * conexão por rotas com este.
     */
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

    /**
     * Retorna true se este território não conter nenhum vizinho de outro jogador, ou seja,
     * se ele for um território interno do jogador.
     */
    public boolean isHinterland() {
        for (Territory neighbour : getNeighbours()) {
            if (neighbour.getOwner() != this.getOwner()) {
                return false;
            }
        }
        return true;
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

    /**
     * Retorna o ID deste território, de acordo com a classe {@link TerritoryID}
     */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public boolean belongToRegion (Region region) {
        for (int i = 0; i < region.getTerritories().size(); i++) {                           
            if (this.equals(region.getTerritories().get(i))) {
                return true;
            }               
        }
        return false;
    }
}