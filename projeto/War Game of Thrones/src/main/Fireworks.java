package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;

public class Fireworks extends Entity {
    
    private Vector2f mapPos;
    private Vector2f mapSize;
    
    public Fireworks(Animation a) {
        mapPos = main.Main.getMapPos();
        mapSize =  main.Main.getMapSize();
        float randomXPos = mapPos.x + (float) (Math.random() * mapSize.x);
        float randomYPos = mapPos.y + (float) (Math.random() * mapSize.y);
        setPosition(new Vector2f(randomXPos, randomYPos));
        addComponent(new FireworksRenderer("fireworksRenderer", a));
    }
    
}
