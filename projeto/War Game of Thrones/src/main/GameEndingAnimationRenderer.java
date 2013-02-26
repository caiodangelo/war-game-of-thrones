package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Player;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class GameEndingAnimationRenderer extends RenderComponent{

    private Color c;
    private String playerName;
    private float elapsed = 0;
    private static final float DURATION = 5f;
    private static final int FRAMES = 38;
    private static final int FRAME_DURATION = 80;
    private static final int HORIZONTAL_SUBREGIONS = 6;
    private static final int VERTICAL_SUBREGIONS = 6;
    private static final float MAX_DELAY_TO_DRAW = 7;
    private Vector2f pos;
    private Vector2f mapSize;
    private ArrayList<Animation> fireworksToBeDrawed;
    private HashMap<Integer, Vector2f> positionsToDraw;
    private HashMap<Integer, Float> timeToStartDrawing;
    
    public GameEndingAnimationRenderer(String id){
        super(id);
        Vector2f mapPos = main.Main.getMapPos();
        mapSize =  main.Main.getMapSize();
        pos = new Vector2f(mapPos.x, mapPos.y + mapSize.y/2);
        fireworksToBeDrawed = new ArrayList<Animation>();
        positionsToDraw = new HashMap<Integer, Vector2f>();
        timeToStartDrawing = new HashMap<Integer, Float>();
        int[] fireworkDurations = new int[FRAMES];
        for (int i = 0; i < fireworkDurations.length; i++) {
            fireworkDurations[i] = FRAME_DURATION;
        }
        int[] frames = new int[fireworkDurations.length*2];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = (i/2) % 7;
            i++;
            frames[i] = (i/2) / 7; 
        }
        try {
            SpriteSheet s1 = new SpriteSheet("resources/images/fireworks-sptsheet.png", 896/7, 896/7);
            SpriteSheet s2 = new SpriteSheet("resources/images/fireworks2-sptsheet.png", 896/7, 896/7);
            SpriteSheet s3 = new SpriteSheet("resources/images/fireworks3-sptsheet.png", 896/7, 896/7);
            int frameIndex;
            Animation a = null;
            for (int i = 0; i < HORIZONTAL_SUBREGIONS * VERTICAL_SUBREGIONS; i++) {
                frameIndex = i % 3;
                switch (frameIndex) {
                    case 0:
                        fireworksToBeDrawed.add(a = new Animation(s1, frames, fireworkDurations));
                        break;
                    case 1:
                        fireworksToBeDrawed.add(a = new Animation(s2, frames, fireworkDurations));
                        break;
                    case 2:
                        fireworksToBeDrawed.add(a = new Animation(s3, frames, fireworkDurations));
                        break;
                }
                a.stop();
                a.setPingPong(true);
                positionsToDraw.put(i, centerOfSubregion(i));
                float f = (float) (Math.random() * MAX_DELAY_TO_DRAW);
                timeToStartDrawing.put(i, f);
            }
        } catch (SlickException ex) {
            Logger.getLogger(GameEndingAnimationRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean fadingIn() {
        return getPcgt() < 0.5f;
    }
    
    private float getPcgt(){
        return elapsed / DURATION;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if(playerName != null){
            float pctg = getPcgt();
            if(fadingIn())
                c.a = pctg / 0.5f;
            else
                c.a = 1 - (pctg - 0.5f) / 0.5f;
            gr.setColor(c);
            AngelCodeFont fnt = null;
            try {
                fnt = new AngelCodeFont("resources/fonts/calibri_80.fnt", "resources/fonts/calibri_80_0.tga");
                gr.setFont(fnt);
            } catch (SlickException ex) {
                Logger.getLogger(PlayerTurnMessageRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
            String winner = playerName+" venceu!";
            String congratulations = "Parabéns!";
            gr.drawString(winner, pos.x + (mapSize.x - fnt.getWidth(winner))/2.0f, pos.y);
            gr.drawString(congratulations, pos.x + (mapSize.x - fnt.getWidth(congratulations))/2.0f, pos.y + fnt.getWidth(winner));
        }
        for (int i = 0; i < positionsToDraw.size(); i++) {
            Animation toBeDrawed = fireworksToBeDrawed.get(i);
            if (elapsed >= timeToStartDrawing.get(i))
                if (toBeDrawed.isStopped())
                    toBeDrawed.restart();
                toBeDrawed.draw(positionsToDraw.get(i).x, positionsToDraw.get(i).y);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (elapsed <= MAX_DELAY_TO_DRAW && delta < 0.5f)
            elapsed += delta;
    }

    public void activate(Player player) {
        playerName = player.getName();
        //c = new Color(color.getRed(), color.getGreen(), color.getBlue());
        c = new Color(Color.black);
        elapsed = 0;
    }
    
    private Vector2f centerOfSubregion(int i) {
        float xMapInitPos = main.Main.getMapPos().x;
        float yMapInitPos = main.Main.getMapPos().y;
        
        float xInterval = (mapSize.x / (float) HORIZONTAL_SUBREGIONS);
        float yInterval = (mapSize.y / (float) VERTICAL_SUBREGIONS);
        
        float xInitPos = xMapInitPos + xInterval * (i % HORIZONTAL_SUBREGIONS);
        float yInitPos = yMapInitPos + yInterval * (i / VERTICAL_SUBREGIONS);
        
        float xImageDisplacement = fireworksToBeDrawed.get(i).getWidth()/2f;
        float yImageDisplacement = fireworksToBeDrawed.get(i).getHeight()/2f;
        float xSubregionCenter = xInitPos + xInterval / 2f;
        float ySubregionCenter = yInitPos + yInterval / 2f;
        
        float xPositionToDrawOn = xSubregionCenter - xImageDisplacement;
        float yPositionToDrawOn = ySubregionCenter - yImageDisplacement;
        
        return new Vector2f(xPositionToDrawOn, yPositionToDrawOn);
    }
    
}
