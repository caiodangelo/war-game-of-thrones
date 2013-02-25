package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageRenderComponent;

public class ArmyRenderComponent extends ImageRenderComponent {
    
    private static final float ATTACK_SPACEMENT = 20;
    
    private int movingQty = 0;
    private Image imageCopy;
    private Vector2f origin;
    private Vector2f destiny;
    private float xSpeed = 0;
    private float ySpeed = 0;
    private Vector2f movingPos;
    private boolean exploding;
    private Animation explosion;
    
    public ArmyRenderComponent(String id, Image img) {
        super(id, img);
        try {
            SpriteSheet ss = new SpriteSheet("resources/images/explosion-spritesheet.png", 1024/8, 768/6);
            explosion = new Animation(ss, 40);
            explosion.setLooping(false);
            this.imageCopy = img;
        } catch (SlickException ex) {
            Logger.getLogger(ArmyRenderComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.getPosition();
        float scale = ((Army) owner).getTerritory().getScale();
        image.draw(pos.x, pos.y, scale);
        gr.setColor(Color.white);
        gr.drawString((((Army) owner).getQuantity() - movingQty)+"", pos.x, pos.y);
        if (movingQty > 0 && (xSpeed != 0 || ySpeed != 0)) {
            imageCopy.draw(movingPos.x, movingPos.y, scale);
            gr.drawString(movingQty+"", movingPos.x, movingPos.y);
        }
        if (exploding)
            drawCenteredExplosion();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (exploding && explosion.isStopped()) { //animation has ended
            exploding = false;
            movingQty = 0;
            destiny = null;
            xSpeed = 0;
            ySpeed = 0;
        }
        else if (!exploding) {
            if (movingQty > 0) {
                if (xSpeed == 0 && ySpeed == 0) {
                    movingPos = origin;
                    xSpeed = (destiny.x - origin.x)/80f;
                    ySpeed = (destiny.y - origin.y)/80f;
                }
                else if ((xSpeed < 0 && movingPos.x > destiny.x + ATTACK_SPACEMENT) || (xSpeed > 0 && movingPos.x < destiny.x - ATTACK_SPACEMENT))
                    move();
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
    
    public void startExplosion() {
        exploding = true;
        explosion.restart();
    }
    
    private void move() {
        movingPos.x += xSpeed;
        movingPos.y += ySpeed;
    }
    
    private void drawCenteredExplosion() {
        float xDisplacement = explosion.getWidth()/2;
        float yDisplacement = explosion.getHeight()/2;
        explosion.draw(movingPos.x - xDisplacement, movingPos.y - yDisplacement);
    }
    
}
