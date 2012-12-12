package main;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.MainSceneAnimation;
import util.Scene;

public class MainScene extends Scene{

    private Nifty n;
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("startingScreen");
        Image mapImage;
        try {
            mapImage = new Image("resources/images/mapa.jpg");
            Map map = new Map(mapImage);
            map.addComponent(new MainSceneAnimation("animation", map));
            map.setPosition(new Vector2f(0, 0));
            map.setScale(2);
            addEntity(map);
        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
