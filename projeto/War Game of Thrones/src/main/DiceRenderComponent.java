package main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.RenderComponent;

public class DiceRenderComponent extends RenderComponent {
    
    private static final int FRAME_DURATION = 80;
    
    private SpriteSheet sheet;
    private Animation anim;
    List<Integer> numbers;
    
    public DiceRenderComponent(String id, SpriteSheet ss) {
        super(id);
        try {
            sheet = new SpriteSheet("resources/images/dado-atk.png", 451/6, 74);
            int[] durations = new int[6];
            for (int i = 0; i < durations.length; i++) {
                durations[i] = FRAME_DURATION;
            }
            numbers = Arrays.asList(0, 1, 2, 3, 4, 5);
            Collections.shuffle(numbers);
            int[] diceNumbers = new int[12];
            for (int i = 0; i < diceNumbers.length; i++) {
                diceNumbers[i] = numbers.get(i/2);
                i++;
                diceNumbers[i] = 0;
            }
            anim = new Animation(sheet, diceNumbers, durations);
            //anim = new Animation(sheet, 80);
        } catch (SlickException ex) {
            Logger.getLogger(DiceRenderComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        Vector2f pos = owner.getPosition();
        anim.draw(pos.x, pos.y);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        Dice dice = ((Dice) owner);
        DiceManager dm = DiceManager.getInstance();
        if (!dice.isRolling() && dice.getResult() < 0) {
            int rand = (int) (Math.random()*6);
            dice.setResult(rand);
            dm.checkIfAllDicesAreSet();
            anim.setCurrentFrame(numbers.indexOf(rand));
            anim.stop();
        }
        GameScene gs = new GameScene();
        Input i = gc.getInput();
        if (i.isKeyDown(Input.KEY_0))
            dm.showDices(3, 3);
        else if (i.isKeyDown(Input.KEY_1))
            dm.removeDices();
    }
    
}
