package main;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;

public class DiceManager {
    
    private static final Vector2f FIRST_ATK_DICE_POSITION = new Vector2f(Main.windowW * 0.45f, Main.windowH * 0.4f);
    private static final Vector2f SECOND_ATK_DICE_POSITION = new Vector2f(Main.windowW * 0.45f, Main.windowH * 0.5f);
    private static final Vector2f THIRD_ATK_DICE_POSITION = new Vector2f(Main.windowW * 0.45f, Main.windowH * 0.6f);
    private static final Vector2f FIRST_DEF_DICE_POSITION = new Vector2f(Main.windowW * 0.55f, Main.windowH * 0.4f);
    private static final Vector2f SECOND_DEF_DICE_POSITION = new Vector2f(Main.windowW * 0.55f, Main.windowH * 0.5f);
    private static final Vector2f THIRD_DEF_DICE_POSITION = new Vector2f(Main.windowW * 0.55f, Main.windowH * 0.6f);
    public static final Vector2f[] ATK_POSITIONS = {FIRST_ATK_DICE_POSITION, SECOND_ATK_DICE_POSITION, THIRD_ATK_DICE_POSITION};
    public static final Vector2f[] DEF_POSITIONS = {FIRST_DEF_DICE_POSITION, SECOND_DEF_DICE_POSITION, THIRD_DEF_DICE_POSITION};
    
    private static DiceManager instance;
    private GameScene gameScene;
    private boolean dicesOnScreen;
    private boolean dicesOnCorrectPosition;
    private ArrayList<Dice> atkDices;
    private ArrayList<Dice> defDices;
    
    public DiceManager() {
        atkDices = new ArrayList();
        defDices = new ArrayList();
    }
    
    public static DiceManager getInstance() {
        if (instance == null)
            instance = new DiceManager();
        return instance;   
    }
    
    public void setGameScene(GameScene gs) {
        gameScene = gs;
    }
    
    public void checkIfAllDicesAreSet() {
        boolean dicesSet = true;
        for (Dice ad : atkDices) {
            dicesSet = dicesSet && (ad.getResult() >= 0);
        }
        for (Dice dd : defDices) {
            dicesSet = dicesSet && (dd.getResult() >= 0);
        }
        if (dicesSet) {
            int pos = 0;
            ArrayList<Dice> tempAtkDices = new ArrayList<Dice>(atkDices);
            while (!(tempAtkDices.isEmpty())) {
                Dice diceToMove = getHigherResultDice(tempAtkDices);
                tempAtkDices.remove(diceToMove);
                diceToMove.addComponent(new DiceMovementsComponent("dice-movements", diceToMove.getPosition(), ATK_POSITIONS[pos]));
                pos++;
            }
            pos = 0;
            ArrayList<Dice> tempDefDices = new ArrayList<Dice>(defDices);
            while (!(tempDefDices.isEmpty())) {
                Dice diceToMove = getHigherResultDice(tempDefDices);
                tempDefDices.remove(diceToMove);
                diceToMove.addComponent(new DiceMovementsComponent("dice-movements", diceToMove.getPosition(), DEF_POSITIONS[pos]));
                pos++;
            }
        }
    }
    
    public Dice getHigherResultDice(ArrayList<Dice> list) {
        int higher = -1;
        Dice winner = null;
        for (Dice d : list) {
            if (d.getResult() > higher) {
                higher = d.getResult();
                winner = d;
            }
        }
        return winner;
    }
    
    public boolean dicesOnScreen() {
        return dicesOnScreen;
    }
    
    public void showDices(int atk, int def) {
        Dice d;
        for (int i = 0; i < atk; i++) {
            d = new Dice(ATK_POSITIONS[i], true);
            atkDices.add(d);
            gameScene.addEntity(d);
        }
        for (int i = 0; i < def; i++) {
            d = new Dice(DEF_POSITIONS[i], false);
            defDices.add(d);
            gameScene.addEntity(d);
        }
        dicesOnScreen = true;
    }
    
    public void checkIfAllDicesReachedDestination() {
        boolean dicesReachedDest = true;
        for (Dice ad : atkDices) {
            dicesReachedDest = dicesReachedDest && ad.hasReachedDestination();
        }
        for (Dice dd : defDices) {
            dicesReachedDest = dicesReachedDest && dd.hasReachedDestination();
        }
        dicesOnCorrectPosition = dicesReachedDest;
    }
    
    public void removeDices() {
        System.out.println(atkDices.size());
        for(Dice ad : atkDices) {
            gameScene.removeEntity(ad);
        }
        atkDices.clear();
        for(Dice dd : defDices) {
            gameScene.removeEntity(dd);
        }
        defDices.clear();
        dicesOnScreen = false;
    }
    
    public boolean allDicesOnCorrectPosition() {
        return dicesOnCorrectPosition;
    }
}
