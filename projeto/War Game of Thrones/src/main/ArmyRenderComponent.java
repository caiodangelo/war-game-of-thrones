package main;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageRenderComponent;

public class ArmyRenderComponent extends ImageRenderComponent {
    
    private int movingQty = 0;
    private Image imageCopy;
    private Vector2f origin;
    private Vector2f destiny;
    private float xSpeed = 0;
    private float ySpeed = 0;
    private Vector2f movingPos;
    
    public ArmyRenderComponent(String id, Image img) {
        super(id, img);
        try {
            this.imageCopy = new Image("resources/images/army-temp.png");
        } catch (SlickException ex) {
            Logger.getLogger(ArmyRenderComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.getPosition();
        float scale = ((Army) owner).getTerritory().getScale();
        image.draw(pos.x, pos.y, scale);
        gr.drawString(((Army) owner).getQuantity()+"", pos.x, pos.y);
        if (movingQty > 0) {
            gr.setColor(Color.white);
            imageCopy.draw(movingPos.x, movingPos.y, scale);
            gr.drawString(movingQty+"", movingPos.x, movingPos.y);
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (movingQty > 0) {
            if (xSpeed == 0 && ySpeed == 0) {
                movingPos = origin;
                xSpeed = (destiny.x - origin.x)/80f;
                ySpeed = (destiny.y - origin.y)/80f;
            }
            if ((xSpeed < 0 && movingPos.x >= destiny.x) || (xSpeed > 0 && movingPos.x <= destiny.x))
                move();
            else {
                movingQty = 0;
                destiny = null;
                xSpeed = 0;
                ySpeed = 0;
            }
        }
    }
    
    public void setMovingQuantity(int q) {
        this.movingQty = q;
    }
    
    public void setOrigin(Vector2f o) {
        this.origin = o;
    }
    
    public void setDestiny(Vector2f d) {
        this.destiny = d;
    }
    
    private void move() {
        movingPos.x += xSpeed;
        movingPos.y += ySpeed;
    }
    
}
