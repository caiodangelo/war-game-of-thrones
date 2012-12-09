package main;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.ImageRenderComponent;
import util.Scene;
import util.Zoom;

public class MainScene extends Scene{

    private Nifty n;
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("startingScreen");
        Image map;
        try {
            map = new Image("resources/images/mapa.jpg");
            Map m = new Map();
            m.addComponent(new ImageRenderComponent("map", map));
            m.addComponent(new Zoom("zoom"));
            m.setPosition(new Vector2f(0, 0));
            addEntity(m);
        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
