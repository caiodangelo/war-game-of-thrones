/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

/**
 *
 * @author CESA
 */
public class TerritoryNameRenderer extends RenderComponent{

    private Territory territory;
    private AngelCodeFont drawFont;

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
    
    public TerritoryNameRenderer(){
        super("");
        try {
            drawFont = new AngelCodeFont("resources/fonts/calibri_18.fnt", "calibri_18_0.tga");
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if(territory != null && territory.getHoverRenderer().isHighlightedImage() && main.Main.isShowingTerritoriesNames()){
            drawTerritoryName(gr);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
    }
    
    private void drawTerritoryName(Graphics gr/*, Army a, String name*/) {
        Army a = territory.getArmy();
        String name = territory.getBackEndTerritory().getName();
        gr.setFont(drawFont);
        
        float armyXCenter = a.getPosition().x + a.getScaledWidth()/2f;
        float fontHeight = gr.getFont().getHeight(name);
        float fontXPos = armyXCenter - gr.getFont().getWidth(name)/2f;
        float fontYPos;
        if (a.getPosition().y > main.Main.getMapPos().y + main.Main.getMapSize().y/2f)
            fontYPos = a.getPosition().y - fontHeight;
        else
            fontYPos = a.getPosition().y + a.getScaledHeight();
        
        gr.setColor(Color.black);
        gr.drawString(name, fontXPos, fontYPos);
    }
    
}
