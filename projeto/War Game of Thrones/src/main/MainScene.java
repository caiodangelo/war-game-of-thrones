package main;

import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Dragger;
import util.Entity;
import util.ImageMovementsComponent;
import util.ImageRenderComponent;
import util.MainSceneAnimation;
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
            Image mapImage = new Image("resources/images/mapa.jpg");
            Entity map = new Entity();
            map.addComponent(new MainSceneAnimation("animation", mapImage));
            addEntity(map);
        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
