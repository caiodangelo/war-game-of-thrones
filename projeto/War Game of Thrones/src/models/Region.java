package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Region implements Serializable {
    
    protected static final int ALEM_DA_MURALHA = 2;
    protected static final int O_MAR_DOTHRAKI = 3;
    protected static final int O_NORTE = 4;
    protected static final int O_SUL  = 5;
    protected static final int TRIDENTE = 6;
    protected static final int CIDADES_LIVRES = 7;
            
    private String name;
    private List<BackEndTerritory> territories;
    private int bonus;

    public Region(String name, int bonus) {
        this.name = name;
        this.territories = new ArrayList<BackEndTerritory>();
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public boolean addTerritory(BackEndTerritory territory) {
        territory.setRegion(this);
        return territories.add(territory);
    }

    public List<BackEndTerritory> getTerritories() {
        return territories;
    }

    /**
     * Retorna os territórios desta região que fazem fronteiras com outras regiões
     */
    public List<BackEndTerritory> getBorderTerritories() {
        List<BackEndTerritory> borders = new ArrayList<BackEndTerritory>();
        for (BackEndTerritory territory : territories) {
            List<BackEndTerritory> neighbours = territory.getNeighbours();
            for (BackEndTerritory neighbour : neighbours) {
                if (neighbour.getRegion() != this && !borders.contains(neighbour)) {
                    borders.add(territory);
                }
            }
        }
        return borders;
    }

    /**
     * Se um único {@link Player} possuir todos os {@link BackEndTerritory} desta região, ele
     * será retornado. Caso contrário, o método retornará null.
     */
    public Player getOwner() {
        Player owner = null;
        for (BackEndTerritory territory : territories) {
            if (owner != null && owner != territory.getOwner()) {
                return null;
            }
            owner = territory.getOwner();
        }
        return owner;
    }
    
    @Override
    public String toString(){
        return "Region " + name;
    }

    /**
     * Retorna uma nota para esta region, avaliando o número de territórios que ela
     * possui, número de bordas com outras regiões e o bônus de exércitos que o
     * jogador recebe caso conquiste a região por completo. Este método é utilizado
     * pelo {@link AIPlayer} para avaliar se vale a pena tentar conquistar dada região.
     */
    public double getRating() {
        return (15.0 + (bonus - 4.0) * getBorderTerritories().size()) / (getTerritories().size() * 1.0);
    }

    /**
     * Retorna uma nota ajustada para esta Region, que é a nota da Region dada pelo método getRating,
     * e ajustada com os números de exércitos e territórios do jogador na Region.
     *
     * Quanto mais exércitos e territórios, ou seja, quanto mais próximo o jogador estiver de conquistar esta
     * Region, maior será sua nota ajustada.
     *
     * @param player O {@link Player} que quer calcular sua noa na Region
     * @return A nota ajustada da Region para o {@link Player} fornecido
     */
    public double getAdjustedRating(Player player) {
        int armiesInRegion = 0;
        int playerArmiesInRegion = 0;
        List<BackEndTerritory> playerTerritoriesHere = new ArrayList<BackEndTerritory>();
        for (BackEndTerritory territory : getTerritories()) {
            armiesInRegion += territory.getNumArmies();
            if (territory.getOwner() == player) {
                playerTerritoriesHere.add(territory);
                playerArmiesInRegion += territory.getNumArmies();
            }
        }
        double armiesFactor = playerArmiesInRegion / armiesInRegion;
        double territoryFactor = playerTerritoriesHere.size() / territories.size();
        return getRating() * (armiesFactor * territoryFactor);
    }

    /**
     * Retorna true se todos os {@link BackEndTerritory} desta região pertencerem a um
     * único {@link Player}, e false se não.
     * @param p O {@link Player} que se deseja verificar se é dono da região toda.
     */
    public boolean conqueredByPlayer(Player p){
        for(BackEndTerritory playerTerr : territories){
            if(!playerTerr.getOwner().equals(p))
                return false;
        }
        return true;
    }
}