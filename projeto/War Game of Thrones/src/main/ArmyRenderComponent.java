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
    private Vector2f origin;
    private Vector2f destiny;
    private Territory destinyTerritory;
    private float xSpeed = 0;
    private float ySpeed = 0;
    private Vector2f movingPos;
    private Image imageCopy;
    private boolean exploding;
    private boolean distributing;
    private Animation explosion;
    
    public ArmyRenderComponent(String id, Image img) {
        super(id, img);
        try {
            SpriteSheet ss = new SpriteSheet("resources/images/explosion-spritesheet.png", 1024/8, 768/6);
            explosion = new Animation(ss, 40);
            explosion.setLooping(false);
            imageCopy = img;
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
        if (!distributing)
            gr.drawString((((Army) owner).getQuantity() - movingQty)+"", pos.x, pos.y);
        else
            gr.drawString(((Army) owner).getQuantity()+"", pos.x, pos.y);
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
        else if (!exploding && !distributing) {
            if (movingQty > 0) {
                if (xSpeed == 0 && ySpeed == 0) {
                    movingPos = origin;
                    xSpeed = (destiny.x - origin.x)/80f;
                    ySpeed = (destiny.y - origin.y)/80f;
                }
                else if (!closeToDestiny())
                    move();
            }
        }
        else if (distributing) {
            if (movingQty > 0) {
                if (xSpeed == 0 && ySpeed == 0) {
                    movingPos = origin;
                    xSpeed = (destiny.x - origin.x)/80f;
                    ySpeed = (destiny.y - origin.y)/80f;
                }
                else if ((xSpeed < 0 && movingPos.x <= destiny.x) || (xSpeed > 0 && movingPos.x >= destiny.x)) {
                    destinyTerritory.getBackEndTerritory().increaseArmies(movingQty);
                    movingQty = 0;
                    distributing = false;
                    destiny = null;
                    xSpeed = 0;
                    ySpeed = 0;
                }
                else
                    move();
            }
        }
    }
    
    public void setMovingQuantity(int q) {
        this.movingQty = q;
    }
    
    public void setOrigin(Territory o) {
        origin = o.getArmy().getPosition();
    }
    
    public void setDestiny(Territory d) {
        destiny = d.getArmy().getPosition();
        destinyTerritory = d;
    }
    
    public void startExplosion() {
        exploding = true;
        explosion.restart();
    }
    
    public void startDistribution() {
        distributing = true;
    }
    
    private void move() {
        movingPos.x += xSpeed;
        movingPos.y += ySpeed;
    }
    
    private boolean closeToDestiny() {
        float xDistance = Math.abs(destiny.x - movingPos.x);
        float yDistance = Math.abs(destiny.y - movingPos.y);
        float diagonalDistance = (float) Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
        return diagonalDistance < ATTACK_SPACEMENT;
    }
    
    private void drawCenteredExplosion() {
        float xDisplacement = explosion.getWidth()/2f;
        float yDisplacement = explosion.getHeight()/2f;
        explosion.draw(movingPos.x - xDisplacement, movingPos.y - yDisplacement);
    }
    
}
