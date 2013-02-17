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
    
    private static final float ATK_DICES_X_POSITION = Main.windowW * 0.45f;
    private static final float DEF_DICES_X_POSITION = Main.windowW * 0.55f;
    private static final float DICES_INITIAL_Y_POSITION = Main.windowH * 0.4f;
    private static final float Y_DICE_SPACING = Main.windowH * 0.1f;
    
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
        showDices(3, 2);
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
        for (int i = 0; i < atk; i++) {
            d = new Dice(new Vector2f(ATK_DICES_X_POSITION, DICES_INITIAL_Y_POSITION + (i * Y_DICE_SPACING)), true);
            dices.add(d);
            addEntity(d);
        }
        for (int i = 0; i < def; i++) {
            d = new Dice(new Vector2f(DEF_DICES_X_POSITION, DICES_INITIAL_Y_POSITION + (i * Y_DICE_SPACING)), false);
            dices.add(d);
            addEntity(d);
        }
    }
    
    public void removeDices() {
        for(Dice d : dices) {
            removeEntity(d);
        }
        dices.clear();
    }
}
