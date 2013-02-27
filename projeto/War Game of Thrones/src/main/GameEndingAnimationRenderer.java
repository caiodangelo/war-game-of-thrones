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
    private Vector2f pos;
    private Vector2f mapSize;
    
    public GameEndingAnimationRenderer(String id){
        super(id);
        Vector2f mapPos = main.Main.getMapPos();
        mapSize =  main.Main.getMapSize();
        pos = new Vector2f(mapPos.x, mapPos.y + mapSize.y/2);
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
            gr.drawString(congratulations, pos.x + (mapSize.x - fnt.getWidth(congratulations))/2.0f, pos.y + fnt.getHeight(winner));
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (delta < 0.5f)
            elapsed += delta;
    }

    public void activate(Player player) {
        playerName = player.getName();
        //c = new Color(color.getRed(), color.getGreen(), color.getBlue());
        c = new Color(Color.black);
        elapsed = 0;
    }
    
}
