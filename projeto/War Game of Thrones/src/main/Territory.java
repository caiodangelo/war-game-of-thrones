package main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;
import util.ImageRenderComponent;

public class Territory extends Entity{
    
    private Map map;
    private TerritoryPositionSync syncer;
    private ImageRenderComponent irc;
    private Army army;
    private models.Territory backEndTerr;
    
    public Territory(Map m, Vector2f relativePos, String imagePath, models.Territory backEndTerr){
        super();
        this.backEndTerr = backEndTerr;
        map = m;
        syncer = new TerritoryPositionSync(map, relativePos);
        addComponent(syncer);
        try{
            Image territoryImg = new Image(imagePath);
            Image highlightedImg = new Image(imagePath.replace(".png", "")+"-h.png");
            irc = new ImageRenderComponent("territory-renderer", territoryImg);
            addComponent(irc);
            addComponent(new TerritoryHoverImage("hover", highlightedImg));
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
    public models.Territory getBackEndTerritory(){
        return backEndTerr;
    }
    
    public float getScaledWidth(){
        return irc.getImageWidth(getScale());
    }
    
    public float getScaledHeight(){
        return irc.getImageHeight(getScale());
    }
    
    public Army getArmy() {
        return this.army;
    }
    
    public void setArmy(Army a) {
        this.army = a;
    }
    
}
