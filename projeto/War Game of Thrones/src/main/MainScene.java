package main;

import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import util.Entity;
import util.Scene;

public class MainScene extends Scene{

    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("startingScreen");
            
        try {
            Image mapImage = new Image("resources/images/mapa-nomes.jpg");
            Entity map = new Entity();
            map.setScale(4);
            map.addComponent(new MainSceneAnimation("animation", mapImage));
            addEntity(map);
        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
