package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class FireworksRenderer extends RenderComponent {
    
    Animation fireworks;
    
    public FireworksRenderer(String id, Animation anim) {
        super(id);
        anim.setLooping(false);
        fireworks = anim;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.getPosition();
        fireworks.draw(pos.x, pos.y);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (fireworks.isStopped())
            owner.getScene().removeEntity(owner);
    }
    
}
