package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class PlayerTurnMessageRenderer extends RenderComponent{

    private Color c;
    private String playerName;
    private float elapsed = 0;
    private static final float DURATION = 5f, H_SPEED = 100f;
    private Vector2f pos;
    private Vector2f mapSize;
    
    public PlayerTurnMessageRenderer(String id){
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
            String text = "Vez de " + playerName + "!";
            gr.drawString(text, pos.x + (mapSize.x - fnt.getWidth(text))/2.0f, pos.y);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (playerName != null) {
            if(delta < 0.5f){
                elapsed += delta;
                if(elapsed >= DURATION)
                    playerName = null;
            }
        }
    }

    public void activate(String playerName, de.lessvoid.nifty.tools.Color color) {
        this.playerName = playerName;
        this.c = new Color(color.getRed(), color.getGreen(), color.getBlue());
        elapsed = 0;
    }
}
