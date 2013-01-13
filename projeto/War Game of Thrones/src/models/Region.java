package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Region {

    private List<Territory> territories;
    
    public Region (){
        this.territories = new ArrayList<Territory>();
    }
    
    public boolean addTerritory(Territory territory){
        return territories.add(territory);
    }

}
