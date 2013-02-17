package util;

import main.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class MapAreaRenderer extends RenderComponent{
    
    Vector2f start, size;
    
    public MapAreaRenderer(){
        super("");
        start = Main.getMapPos();
        size = Main.getMapSize();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        gr.setColor(Color.yellow);
        gr.fillRect(start.x, start.y, size.x, size.y);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
