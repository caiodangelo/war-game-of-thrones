package main;

import util.MapAreaRenderer;
import de.lessvoid.nifty.Nifty;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;
import util.Scene;

public class GameScene extends Scene{
    
    private static final Vector2f FIRST_ATK_DICE_POSITION = new Vector2f(Main.windowW * 0.45f, Main.windowH * 0.4f);
    private static final Vector2f SECOND_ATK_DICE_POSITION = new Vector2f(Main.windowW * 0.45f, Main.windowH * 0.5f);
    private static final Vector2f THIRD_ATK_DICE_POSITION = new Vector2f(Main.windowW * 0.45f, Main.windowH * 0.6f);
    private static final Vector2f FIRST_DEF_DICE_POSITION = new Vector2f(Main.windowW * 0.55f, Main.windowH * 0.4f);
    private static final Vector2f SECOND_DEF_DICE_POSITION = new Vector2f(Main.windowW * 0.55f, Main.windowH * 0.5f);
    private static final Vector2f THIRD_DEF_DICE_POSITION = new Vector2f(Main.windowW * 0.55f, Main.windowH * 0.6f);
    public static final Vector2f[] ATK_POSITIONS = {FIRST_ATK_DICE_POSITION, SECOND_ATK_DICE_POSITION, THIRD_ATK_DICE_POSITION};
    public static final Vector2f[] DEF_POSITIONS = {FIRST_DEF_DICE_POSITION, SECOND_DEF_DICE_POSITION, THIRD_DEF_DICE_POSITION};
    
    private static ArrayList<Dice> dices = new ArrayList();
    private static int mouseWheel;
    
    public static int getMouseWheel(){
        return mouseWheel;
    }
    
    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("inGameScreen");
    }
    
    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException { 
        super.enterState(container, game);
        Map map = new Map();
        addEntity(map);
        //Left button was pressed to play but this record must be erased.
        container.getInput().clearMousePressedRecord();
        AudioManager am = AudioManager.getInstance();
        am.stopMusic(AudioManager.OPENING);
        showDices(3, 3);
    }

    @Override
    public void mouseWheelMoved(int newValue) {
        super.mouseWheelMoved(newValue);
        mouseWheel = newValue;
    }

    @Override
    protected void updateGame(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.updateGame(container, game, delta);
        mouseWheel = 0;
    }

    @Override
    public int getID() {
        return WarScenes.GAME_SCENE.ordinal();
    }
    
    public void showDices(int atk, int def) {
        Dice d;
        DiceManager dm = DiceManager.getInstance();
        for (int i = 0; i < atk; i++) {
            d = new Dice(ATK_POSITIONS[i], true);
            dm.addAtkDice(d);
            dices.add(d);
            addEntity(d);
        }
        for (int i = 0; i < def; i++) {
            d = new Dice(DEF_POSITIONS[i], false);
            dm.addDefDice(d);
            dices.add(d);
            addEntity(d);
        }
        dm.setDicesOnScreen(true);
    }
    
    public void removeDices() {
        for(Dice d : dices) {
            removeEntity(d);
        }
        dices.clear();
        DiceManager.reset();
    }
    
}
