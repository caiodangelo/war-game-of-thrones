package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
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
        Army armyOwner = (Army) owner;
        float scale = armyOwner.getTerritory().getScale();
        image.draw(pos.x, pos.y, scale);
        gr.setColor(Color.white);
        int count = (armyOwner.getQuantity()) + (distributing ? 0 : - movingQty);
        String countText = count + "";
        Font f = gr.getFont();
        int textWidth = f.getWidth(countText),
                textHeight = f.getHeight(countText);
        gr.drawString(countText, pos.x + (imageCopy.getWidth() * scale-textWidth)/2f, pos.y + (imageCopy.getHeight() * scale-textHeight)/2f);
        if (movingQty > 0 && (xSpeed != 0 || ySpeed != 0)) {
            imageCopy.draw(movingPos.x, movingPos.y, scale);
            gr.drawString(movingQty+"", movingPos.x + (imageCopy.getWidth() * scale-textWidth)/2f, movingPos.y + (imageCopy.getHeight() * scale-textHeight)/2f);
        }
        if (exploding)
            drawCenteredExplosion();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        //syncing army copy position
        float xDifference;
        float yDifference;
        if (movingQty > 0) {
            if ((xDifference = owner.getPosition().x - origin.x) != 0) {
                origin.x += xDifference;
                movingPos.x += xDifference;
                destiny.x += xDifference;
            }
            if ((yDifference = owner.getPosition().y - origin.y) != 0) {
                origin.y += yDifference;
                movingPos.y += yDifference;
                destiny.y += yDifference;
            }
        }
        
        
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
                    movingPos = new Vector2f(origin);
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
                    movingPos = new Vector2f(origin);
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
    
    public void setMovementTo(Territory dest) {
        origin = new Vector2f(owner.getPosition());
        destiny = dest.getArmy().getPosition();
        destinyTerritory = dest;
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
