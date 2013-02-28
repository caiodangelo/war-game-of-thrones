package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import models.Board;
import models.Player;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class PlayerTurnMessageRenderer extends RenderComponent{

    private enum FadeState { FADE_IN, FADE_OUT, CONSTANT, INACTIVE}
    
    private FadeState state = FadeState.INACTIVE;
    
    private Color c;
    private String playerName;
    private String bonus;
    private float elapsed = 0;
    private static final float FADE_DURATION = 1.0f, CONST_DURATION = 3.0f;
    private Vector2f pos;
    private Vector2f mapSize;
    private AngelCodeFont fnt = null;
    
    public PlayerTurnMessageRenderer(String id){
        super(id);
        Vector2f mapPos = main.Main.getMapPos();
        mapSize =  main.Main.getMapSize();
        pos = new Vector2f(mapPos.x, mapPos.y + mapSize.y/2);
        try {
            fnt = new AngelCodeFont("resources/fonts/calibri_80.fnt", "resources/fonts/calibri_80_0.tga");
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean fadingIn() {
        return getPcgt() < 0.5f;
    }
    
    private float getPcgt(){
        return elapsed / (state == FadeState.CONSTANT ? CONST_DURATION : FADE_DURATION);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if(state != FadeState.INACTIVE){
            gr.setColor(c);
            String turnText = "Vez de " + playerName + "!";
            String initialSetupText = "Distribuição inicial: "+bonus+" exércitos";
            String bonusText = "Exércitos recebidos: "+bonus;
            gr.setFont(fnt);
            gr.drawString(turnText, pos.x + (mapSize.x - fnt.getWidth(turnText))/2.0f, pos.y);
            //escolher fonte menor para o segundo texto
            if (Board.getInstance().isOnInitialSetup())
                gr.drawString(initialSetupText, pos.x + (mapSize.x - fnt.getWidth(initialSetupText))/2.0f, pos.y + (fnt.getHeight(turnText) * 1.5f));
            else if (!Board.getInstance().isOnFirstTurn())
                gr.drawString(bonusText, pos.x + (mapSize.x - fnt.getWidth(bonusText))/2.0f, pos.y + (fnt.getHeight(turnText) * 1.5f));
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if(delta < 0.5f) {
            switch(state){
                case FADE_IN:
                    c.a = getPcgt();
                    elapsed += delta;
                    if(elapsed >= FADE_DURATION)
                        setState(FadeState.CONSTANT);
                    break;
                case CONSTANT:
                    c.a = 1f;
                    elapsed += delta;
                    if(elapsed >= CONST_DURATION)
                        setState(FadeState.FADE_OUT);
                    break;
                case FADE_OUT:
                    c.a = 1f - getPcgt();
                    elapsed += delta;
                    if(elapsed >= FADE_DURATION)
                        setState(FadeState.INACTIVE);
                    break;
            }
        }
    }
    
    private void setState(FadeState s){
        this.state = s;
        elapsed = 0;
    }

    public void activate(Player player, de.lessvoid.nifty.tools.Color color) {
        playerName = player.getName();
        bonus = player.getPendingArmies()+"";
        c = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
        AudioManager.getInstance().playSound(AudioManager.SWORD_TURN_SOUND);
        setState(FadeState.FADE_IN);
    }
}
