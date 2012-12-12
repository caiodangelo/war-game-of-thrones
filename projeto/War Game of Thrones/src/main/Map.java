package main;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;

public class Map extends Entity{
    
    public Map(Image img) {
        setScale((Main.windowW * Main.windowH) / (1280 * 768));
        setPosition(new Vector2f(0, 0));
        image = img;
    }
    
}
