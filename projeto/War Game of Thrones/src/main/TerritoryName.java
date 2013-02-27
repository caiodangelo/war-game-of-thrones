/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import util.Entity;

/**
 *
 * @author CESA
 */
public class TerritoryName extends Entity{
    
    private TerritoryNameRenderer r;
    
    public TerritoryName(){
        super();
        setLayer(2);
        r = new TerritoryNameRenderer();
        addComponent(r);
    }

    void setHighlightedTerritory(Territory t) {
        r.setTerritory(t);
    }
    
}
