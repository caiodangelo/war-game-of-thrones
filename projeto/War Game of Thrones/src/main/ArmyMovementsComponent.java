package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageRenderComponent;
import util.ImageRenderComponent;
import util.ImageRenderComponent;

public class ArmyMovementsComponent extends ImageRenderComponent {
    
    private int movingQty = 0;
    private Vector2f armiesPosition = new Vector2f(0, 0);
    
    public ArmyMovementsComponent(String id, Image img) {
        super(id, img);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if (movingQty == 0) {
            Vector2f pos = owner.position;
            float scale = owner.getScale();
            image.draw(pos.x, pos.y, scale);
        }
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        //armiesPosition = owner.position;
        //armiesPosition.x++;
        //armiesPosition.y++;
    }
    
}
