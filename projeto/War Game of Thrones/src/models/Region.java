package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Region {
    
    protected static final int ALEM_DA_MURALHA = 2;
    protected static final int O_MAR_DOTHRAKI = 3;
    protected static final int O_NORTE = 4;
    protected static final int O_SUL  = 5;
    protected static final int TRIDENTE = 6;
    protected static final int CIDADES_LIVRES = 7;
            
    private String name;
    private List<Territory> territories;
    private int bonus;

    public Region(String name, int bonus) {
        this.name = name;
        this.territories = new ArrayList<Territory>();
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

    public boolean addTerritory(Territory territory) {
        territory.setRegion(this);
        return territories.add(territory);
    }

    public List<Territory> getTerritories() {
        return territories;
    }
    
    @Override
    public String toString(){
        return "Region " + name;
    }
}
