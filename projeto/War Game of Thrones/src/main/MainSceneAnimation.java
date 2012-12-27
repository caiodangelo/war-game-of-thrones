package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageMovementsComponent;

public class MainSceneAnimation extends ImageMovementsComponent {
    
    float viewX = 0.5f, viewY = 0.5f;
    private int zoomSpeedRegulator = 2;
    private int zoomCount = 0;

    public MainSceneAnimation(String id, Image img) {
        super(id, img);
        try {
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(Scroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float scale = owner.getScale();
        scale -= delta/zoomSpeedRegulator;
        if (scale > 2) {
            zoomSpeedRegulator += 0.5;
            owner.setScale(scale);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainSceneAnimation.class.getName()).log(Level.SEVERE, null, ex);
            }
            zoomSpeedRegulator = 3;
            owner.setScale(4);
            if (zoomCount == 0) {
                viewX = 0.25f;
                viewY = 0.21f;
                zoomCount++;
            } else if (zoomCount == 1) {
                viewX = 0.7f;
                viewY = 0.7f;
                zoomCount++;
            } else if (zoomCount == 2) {
                viewX = 0.25f;
                viewY = 0.71f;
                zoomCount++;
            } else {
                viewX = 0.5f;
                viewY = 0.5f;
                zoomCount = 0;
            }
        }
        Vector2f position = new Vector2f();
        position.x = (main.Main.windowW / 2f) - viewX * getImageWidth(owner.getScale());
        position.y = (main.Main.windowH / 2f) - viewY * getImageHeight(owner.getScale());
        owner.setPosition(position);
    }

}