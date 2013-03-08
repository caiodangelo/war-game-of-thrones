package main;

import gui.InGameGUIController;
import models.Player;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class GameEndingAnimationRenderer extends RenderComponent{

    private static final float FADING_IN_DURATION = 7f;
    private static final float DELAY_TO_SHOW_MESSAGE = -1f;
    
    private Color cFirstMessage;
    private Color cSecondMessage;
    private String playerName;
    private float elapsed = DELAY_TO_SHOW_MESSAGE;
    private Vector2f pos;
    private Vector2f mapSize;
    
    public GameEndingAnimationRenderer(String id){
        super(id);
        Vector2f mapPos = main.Main.getMapPos();
        mapSize =  main.Main.getMapSize();
        pos = new Vector2f(mapPos.x, mapPos.y + mapSize.y/2);
    }
    
    private float getPcgt(){
        return elapsed / FADING_IN_DURATION;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if(playerName != null && elapsed >= 0){
            float pctg = getPcgt();
            if(elapsed <= FADING_IN_DURATION)
                cFirstMessage.a = pctg / 0.5f;
            gr.setColor(cFirstMessage);
            AngelCodeFont fnt = null;
            try {
                fnt = new AngelCodeFont("resources/fonts/freckle_face_80.fnt", "resources/fonts/freckle_face_80_0.tga");
                gr.setFont(fnt);
            } catch (SlickException ex) {
                ex.printStackTrace();
            }
            String winner = playerName+" venceu!";
            String congratulations = "Parabéns!";
            gr.drawString(congratulations, pos.x + (mapSize.x - fnt.getWidth(congratulations))/2.0f, pos.y);
            gr.drawString(winner, pos.x + (mapSize.x - fnt.getWidth(winner))/2.0f, pos.y - fnt.getHeight(winner));
            String clickToStatistics = "Clique em qualquer lugar para ver as estatísticas";
            elapsed -= 1f;
            pctg = getPcgt();
            if (elapsed >= 0 && elapsed <= FADING_IN_DURATION)
                cSecondMessage.a = pctg / 0.5f;
            if (cSecondMessage.a >= 1f)
                InGameGUIController.getInstance().mayGoToStatistics();
            gr.setColor(cSecondMessage);
            gr.drawString(clickToStatistics, pos.x + (mapSize.x - fnt.getWidth(clickToStatistics))/2.0f, pos.y + fnt.getHeight(winner));
            elapsed += 1f;
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (delta < 0.5f)
            elapsed += delta;
    }

    public void activate(Player player) {
        playerName = player.getName();
        AudioManager.getInstance().playMusic(AudioManager.FIREWORKS);
        cFirstMessage = new Color(Color.black);
        cFirstMessage.a = 0;
        cSecondMessage = new Color(Color.black);
        cSecondMessage.a = 0;
        elapsed = DELAY_TO_SHOW_MESSAGE;
    }
    
}
