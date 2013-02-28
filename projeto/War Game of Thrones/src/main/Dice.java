package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import util.Entity;

public class Dice extends Entity {
    
    public static final int WIDTH = 451/6;
    public static final int HEIGHT = 74;
    
    private boolean atkDice;
    private int result;
    private boolean rolling;
    private boolean reachedDestination;
    
    public Dice(Vector2f pos, boolean atk, int r) {
        setLayer(3);
        position = pos;
        atkDice = atk;
        rolling = true;
        result = r;
        SpriteSheet diceSheet;
        try {
            if (atkDice)
                diceSheet = new SpriteSheet("resources/images/dado-atk.png", 451/6, 74);
            else
                diceSheet = new SpriteSheet("resources/images/dado-def.png", 451/6, 74);
            addComponent(new DiceRenderComponent("dice-renderer", diceSheet));
            addComponent(new DiceControllerComponent());
        } catch (SlickException ex) {
            Logger.getLogger(Dice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isAtk() {
        return atkDice;
    }
    
    public void setRolling(boolean r) {
        rolling = r;
    }
    
    public boolean isRolling() {
        return rolling;
    }
    
    public void setResult(int r) {
        result = r;
    }
    
    public int getResult() {
        return result;
    }
    
    public void setReachedDestination(boolean r) {
        reachedDestination = r;
    }
    
    public boolean hasReachedDestination() {
        return reachedDestination;
    }
}
