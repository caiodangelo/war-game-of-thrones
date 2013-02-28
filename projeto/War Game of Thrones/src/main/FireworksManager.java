package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import util.Entity;

public class FireworksManager extends Entity {
    
    private static final String[] ANIM_PATHS = {
                                                "resources/images/fireworks-sptsheet.png",
                                                "resources/images/fireworks2-sptsheet.png",
                                                "resources/images/fireworks3-sptsheet.png",
                                                };
    private static final int FRAMES = 38;
    private static final int FRAME_DURATION = 80;
    
    private int[] fireworkDurations;
    private int[] frames;
    
    public FireworksManager() {
        fireworkDurations = new int[FRAMES];
        for (int i = 0; i < fireworkDurations.length; i++) {
            fireworkDurations[i] = FRAME_DURATION;
        }
        frames = new int[fireworkDurations.length*2];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = (i/2) % 7;
            i++;
            frames[i] = (i/2) / 7; 
        }
        addComponent(new FireworksManagerComponent("fireworksManagerComponent"));
    }
    
    public void drawNewAnimation() {
        SpriteSheet s;
        try {
            s = new SpriteSheet(ANIM_PATHS[(int) (Math.random() * 3)], 896/7, 896/7);
            Fireworks f = new Fireworks(new Animation(s, frames, fireworkDurations));
            getScene().addEntity(f);
        } catch (SlickException ex) {
            Logger.getLogger(FireworksManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
