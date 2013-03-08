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
    private static final String CONGRATULATIONS = "Parabéns!";
    private static final String CLICK_TO_STATISTICS = "Clique em qualquer lugar para ver as estatísticas";
    
    private Color cFirstMessage;
    private Color cSecondMessage;
    private Player winner;
    private float elapsed = DELAY_TO_SHOW_MESSAGE;
    private Vector2f pos;
    private Vector2f mapSize;
    private AngelCodeFont freckle80, freckle50, freckle30;
    private String objectiveStr1;
    private String objectiveStr2;
    
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
            gr.drawString(winnerName, pos.x + (mapSize.x - freckle80.getWidth(winnerName))/2f, pos.y - freckle80.getHeight(winnerName));
            gr.drawString(CONGRATULATIONS, pos.x + (mapSize.x - freckle80.getWidth(CONGRATULATIONS))/2f, pos.y);
            gr.setFont(freckle30);
            gr.drawString(objectiveStr1, pos.x + (mapSize.x - freckle30.getWidth(objectiveStr1))/2f, pos.y + freckle80.getHeight(CONGRATULATIONS));
            gr.drawString(objectiveStr2, pos.x + (mapSize.x - freckle30.getWidth(objectiveStr2))/2f, pos.y + freckle80.getHeight(CONGRATULATIONS) + freckle30.getHeight(objectiveStr1));
            elapsed -= 1f;
            pctg = getPcgt();
            if (elapsed >= 0 && elapsed <= FADING_IN_DURATION)
                cSecondMessage.a = pctg / 0.5f;
            if (cSecondMessage.a >= 1f)
                InGameGUIController.getInstance().mayGoToStatistics();
            gr.setColor(cSecondMessage);
            gr.setFont(freckle50);
            gr.drawString(CLICK_TO_STATISTICS, pos.x + (mapSize.x - freckle50.getWidth(CLICK_TO_STATISTICS))/2f, pos.y + freckle80.getHeight(CONGRATULATIONS) + freckle30.getHeight(objectiveStr1) + 2*freckle30.getHeight(objectiveStr2));
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
        AudioManager.getInstance().playMusic(AudioManager.FIREWORKS);
        cFirstMessage = new Color(Color.black);
        cFirstMessage.a = 0;
        cSecondMessage = new Color(Color.black);
        cSecondMessage.a = 0;
        elapsed = DELAY_TO_SHOW_MESSAGE;
        String objectiveStr = "Objetivo: " + winner.getMission().getDescription();
        objectiveStr1 = "";
        objectiveStr2 = "";
        String[] words = objectiveStr.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (i <= words.length/2)
                objectiveStr1 += words[i]+" ";
            else
                objectiveStr2 += words[i]+" ";
        }
        objectiveStr1 = objectiveStr1.trim();
        objectiveStr2 = objectiveStr2.trim();
    }
    
}
