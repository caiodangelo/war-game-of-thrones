package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageRenderComponent;

public class TerritoryHoverImage extends ImageRenderComponent {
    
    private boolean drawImage;
    
    public TerritoryHoverImage(String id, Image img) {
        super(id, img);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if (drawImage) {
            Vector2f pos = owner.position;
            float scale = owner.getScale();
            image.draw(pos.x, pos.y, scale);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        Input input = gc.getInput();
        float x = input.getAbsoluteMouseX();
        float y = input.getAbsoluteMouseY();
        if (x >= owner.position.x && x <= (owner.position.x + getImageWidth(owner.getScale())) && y >= owner.position.y && y <= (owner.position.y + getImageHeight(owner.getScale()))) {
            drawImage = true;
        }
        else
            drawImage = false;
    }
    
}
