package main;

import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;
import util.Scene;
import util.TerritoriesGraphStructure;

public class MainScene extends Scene{

    private static Nifty n;
    
    private MainSceneAnimation anim;

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        anim.onAnyKeyPressed();
    }
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        this.n = n;
        try {
            TerritoriesGraphStructure tgm = TerritoriesGraphStructure.getInstance();
            
            Image mapImage = new Image("resources/images/mapa-nomes.png");
            Image logoWarImage = new Image("resources/images/logo-war.png");
            Image logoOfImage = new Image("resources/images/logo-of.png");
            Image logoThronesImage = new Image("resources/images/logo-thrones.png");
            Image instructionImage = new Image("resources/images/instruction.png");
            AudioManager am = AudioManager.getInstance();
            am.playMusic(AudioManager.OPENING);
            Entity map = new Entity();
            map.setScale(3.5f);
            anim = new MainSceneAnimation("animation", mapImage, logoWarImage, logoOfImage, logoThronesImage, instructionImage);
            map.addComponent(anim);
            addEntity(map);
            

        } catch (SlickException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
//        n.gotoScreen("empty");
        super.setupNifty(n);
    }
    
    public static void showButtons() {
        n.gotoScreen("startingScreen");
    }
}
