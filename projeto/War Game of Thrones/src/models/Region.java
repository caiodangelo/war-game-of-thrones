package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Region implements Serializable {

    private String name;
    private List<Territory> territories;

    public Region(String name) {
        this.name = name;
        this.territories = new ArrayList<Territory>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static double getRating() {
        return 0;
    }
}
