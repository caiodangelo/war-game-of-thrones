package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;
import util.ImageRenderComponent;

public class Army extends Entity {
    
    public static final int FAMILY_BARATHEON = 1;
    public static final int FAMILY_FREE_FOLK = 2;
    public static final int FAMILY_GREYJOY = 3;
    public static final int FAMILY_LANNISTER = 4;
    public static final int FAMILY_STARK = 5;
    public static final int FAMILY_TARGARYEN = 6;
    
    private int qty;
    private int family;
    private Territory territory;
    
    public Army(Territory territory, int qty, int family) {
        super();
        this.qty = qty;
        this.family = family;
        this.territory = territory;
        try {
            Image armyImg = new Image("resources/images/army-temp.png");
            addComponent(new ArmyRenderComponent("army-renderer", armyImg));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public Territory getTerritory() {
        return this.territory;
    }
    
    public int getQuantity() {
        return this.qty;
    }
    
    public void addArmies(int q) {
        this.qty += q;
    }
    
    public void decreaseArmies(int q) {
        this.qty -= q;
    }
    
}
