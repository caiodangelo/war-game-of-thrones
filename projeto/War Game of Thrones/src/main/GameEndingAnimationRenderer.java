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
    private Player winner;
    private float elapsed = DELAY_TO_SHOW_MESSAGE;
    private Vector2f pos;
    private Vector2f mapSize;
    private AngelCodeFont freckle80, freckle50, freckle30;
    
    public GameEndingAnimationRenderer(String id){
        super(id);
        Vector2f mapPos = main.Main.getMapPos();
        mapSize =  main.Main.getMapSize();
        pos = new Vector2f(mapPos.x, mapPos.y + mapSize.y/2);
        try {
            freckle80 = new AngelCodeFont("resources/fonts/freckle_face_80.fnt", "resources/fonts/freckle_face_80_0.tga");
            freckle50 = new AngelCodeFont("resources/fonts/freckle_face_50.fnt", "resources/fonts/freckle_face_50_0.tga");
            freckle30 = new AngelCodeFont("resources/fonts/freckle_face_30.fnt", "resources/fonts/freckle_face_30_0.tga");
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
    }
    
    private float getPcgt(){
        return elapsed / FADING_IN_DURATION;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if(winner != null && elapsed >= 0){
            float pctg = getPcgt();
            if(elapsed <= FADING_IN_DURATION)
                cFirstMessage.a = pctg / 0.5f;
            gr.setColor(cFirstMessage);
            gr.setFont(freckle80);
            String winnerName = this.winner.getName() +" venceu!";
            String congratulations = "Parabéns!";
            gr.drawString(congratulations, pos.x + (mapSize.x - freckle80.getWidth(congratulations))/2.0f, pos.y);
            gr.drawString(winnerName, pos.x + (mapSize.x - freckle80.getWidth(winnerName))/2.0f, pos.y - freckle80.getHeight(winnerName));
            String clickToStatistics = "Clique em qualquer lugar para ver as estatísticas";
            elapsed -= 1f;
            pctg = getPcgt();
            if (elapsed >= 0 && elapsed <= FADING_IN_DURATION)
                cSecondMessage.a = pctg / 0.5f;
            if (cSecondMessage.a >= 1f)
                InGameGUIController.getInstance().mayGoToStatistics();
            gr.setColor(cSecondMessage);
            gr.setFont(freckle50);
            gr.drawString(clickToStatistics, pos.x + (mapSize.x - freckle50.getWidth(clickToStatistics))/2.0f, pos.y + freckle80.getHeight(winnerName));
            String objectiveStr = "Objetivo: " + winner.getMission().getDescription();
            gr.setFont(freckle30);
            gr.drawString(objectiveStr, pos.x + (mapSize.x - freckle30.getWidth(clickToStatistics))/2.0f, pos.y + 2*freckle80.getHeight(winnerName));
            elapsed += 1f;
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (delta < 0.5f)
            elapsed += delta;
    }

    public void activate(Player player) {
        this.winner = player;
        AudioManager.getInstance().stopMusic(AudioManager.GAME_RUNNING);
        cFirstMessage = new Color(Color.black);
        cFirstMessage.a = 0;
        cSecondMessage = new Color(Color.black);
        cSecondMessage.a = 0;
        elapsed = DELAY_TO_SHOW_MESSAGE;
    }
    
}
