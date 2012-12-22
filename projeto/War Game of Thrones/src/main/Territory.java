package main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;
import util.ImageRenderComponent;

public class Territory extends Entity{
    
    private Map map;
    private TerritoryPositionSync syncer;
    
    public Territory(Map m, Vector2f relativePos, String imagePath){
        super();
        map = m;
        syncer = new TerritoryPositionSync(map, relativePos);
        addComponent(syncer);
        try{
            Image img = new Image(imagePath);
            addComponent(new ImageRenderComponent("renderer", img));
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
