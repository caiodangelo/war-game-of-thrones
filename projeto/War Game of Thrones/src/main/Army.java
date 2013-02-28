package main;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Board;
import models.House;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;
import util.ImageRenderComponent;

public class Army extends Entity {
    
    private Territory territory;
    private ImageRenderComponent irc;
    
    public Army(Map m, Territory territory, Vector2f relativePos, int qty, Scroll s) {
        super();
        setLayer(1);
        this.territory = territory;
        addComponent(new ArmyPositionSync(m, relativePos));
        try {
            String armyImgPath = getHouseImagePath();
            setPosition(new Vector2f(500, 500));
            setScale(m.getScale());
            irc = new ArmyRenderComponent("army-renderer", new Image(armyImgPath));
            addComponent(irc);
        } catch (SlickException e) {
            Logger.getLogger(Army.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Territory getTerritory() {
        return this.territory;
    }
    
    public int getQuantity() {
        return territory.getBackEndTerritory().getNumArmies();
    }
    
    public void changeImage() {
        removeComponent(getComponent("army-renderer"));
        String imgPath = getHouseImagePath();
        try {
            irc = new ArmyRenderComponent("army-renderer", new Image(imgPath));
            addComponent(irc);
        } catch (SlickException ex) {
            Logger.getLogger(Army.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getHouseImagePath() {
        String imgPath = "";
        String house = territory.getBackEndTerritory().getOwner().getHouse().getName();
        if (house.equals(Board.BARATHEON))
            imgPath = "resources/images/pecas/baratheon.png";
        else if (house.equals(Board.FREE_FOLK))
            imgPath = "resources/images/pecas/free_folk.png";
        else if (house.equals(Board.GREYJOY))
            imgPath = "resources/images/pecas/greyjoy.png";
        else if (house.equals(Board.LANNISTER))
            imgPath = "resources/images/pecas/lannister.png";
        else if (house.equals(Board.STARK))
            imgPath = "resources/images/pecas/stark.png";
        else if (house.equals(Board.TARGARYEN))
            imgPath = "resources/images/pecas/targaryen.png";
        return imgPath;
    }
    
    public float getScaledWidth(){
        return irc.getImageWidth(getScale());
    }
    
    public float getScaledHeight(){
        return irc.getImageHeight(getScale());
    }
}
