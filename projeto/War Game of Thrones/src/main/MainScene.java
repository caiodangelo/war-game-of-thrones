package main;

import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import util.Entity;
import util.Scene;

public class MainScene extends Scene{

    private static Nifty n;
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        this.n = n;
        try {
            Image mapImage = new Image("resources/images/mapa-nomes.jpg");
            Image logoWarImage = new Image("resources/images/logo-war.png");
            Image logoOfThronesImage = new Image("resources/images/logo-of-thrones.png");
            Image instructionImage = new Image("resources/images/instruction.png");
            Entity map = new Entity();
            map.setScale(4);
            map.addComponent(new MainSceneAnimation("animation", mapImage, logoWarImage, logoOfThronesImage, instructionImage));
            addEntity(map);
        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void showButtons() {
        n.gotoScreen("startingScreen");
    }
}
