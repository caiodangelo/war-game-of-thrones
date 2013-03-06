package main;

import gui.InGameGUIController;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class RegionNamesRenderer extends RenderComponent{

    private RegionNames parent;
    private AngelCodeFont fnt;
    private Vector2f mapSize;
    private Map map;
    
    public RegionNamesRenderer(RegionNames parent, Map m){
        super("");
        this.parent = parent;
        this.map = m;
        try {
            String font = "calibri_40_italic";
            fnt = new AngelCodeFont("resources/fonts/" + font + ".fnt", "resources/fonts/" + font + "_0.tga");
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
        mapSize = Main.getMapSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if (Main.isShowingTerritoriesNames()) {
            float mapWidth = map.getScaledWidth();
            float mapHeight = map.getScaledHeight();
            for(RegionNames.RegionData data : parent.regions){
                Vector2f relativePos = data.regionCenter;
                Vector2f mapPos = map.getPosition();
                String name = data.name;
                int fontWidth = fnt.getWidth(name);
                int fontHeight = fnt.getHeight(name);
                float x = mapPos.x + relativePos.x * mapWidth - fontWidth/2f;
                float y = mapPos.y + relativePos.y * mapHeight - fontHeight/2f;
                gr.setColor(Color.black);
                gr.setFont(fnt);
                gr.drawString(name, x, y);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
    }
    
}
