package main;

import models.BackEndTerritory;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;

public class Territory extends Entity{
    
    private Map map;
    private TerritoryPositionSync syncer;
    private Image territoryImg;
    private Army army;
    private BackEndTerritory backEndTerr;
    private TerritoryHoverImage hoverRenderer;

    public TerritoryHoverImage getHoverRenderer() {
        return hoverRenderer;
    }
    
    public Territory(Map m, Vector2f relativePos, String imagePath, BackEndTerritory backEndTerr){
        super();
        this.backEndTerr = backEndTerr;
        backEndTerr.setNumArmies(1);
        map = m;
        syncer = new TerritoryPositionSync(map, relativePos);
        addComponent(syncer);
        try{
            territoryImg = new Image(imagePath);
            Image highlightedImg = new Image(imagePath.replace(".png", "")+"-h.png");
            Image notOwnedImg = new Image(imagePath.replace(".png", "")+"-a.png");
            Image ownedImg = territoryImg;
            hoverRenderer = new TerritoryHoverImage("hover", highlightedImg, notOwnedImg, ownedImg);
            addComponent(hoverRenderer);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
    public BackEndTerritory getBackEndTerritory(){
        return backEndTerr;
    }
    
    public float getScaledWidth(){
        return territoryImg.getWidth() * getScale();
    }
    
    public float getScaledHeight(){
        return territoryImg.getHeight() * getScale();
    }
    
    public Army getArmy() {
        return this.army;
    }
    
    public void setArmy(Army a) {
        this.army = a;
    }
    
}
