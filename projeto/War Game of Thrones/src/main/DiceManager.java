package main;

import java.util.ArrayList;

public class DiceManager {
    
    private static DiceManager instance;
    private boolean dicesOnScreen;
    private ArrayList<Dice> atkDices;
    private ArrayList<Dice> defDices;
    
    public DiceManager() {
        atkDices = new ArrayList();
        defDices = new ArrayList();
        dicesOnScreen = false;
    }
    
    public static DiceManager getInstance() {
        if (instance == null)
            instance = new DiceManager();
        return instance;   
    }
    
    public static void reset() {
        instance = null;
    }
    
    public void addAtkDice(Dice d) {
        atkDices.add(d);
    }
    
    public void addDefDice(Dice d) {
        defDices.add(d);
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
            while (!(atkDices.isEmpty())) {
                Dice diceToMove = getHigherResultDice(atkDices);
                atkDices.remove(diceToMove);
                diceToMove.addComponent(new DiceMovementsComponent("dice-movements", diceToMove.getPosition(), GameScene.ATK_POSITIONS[pos]));
                pos++;
            }
            pos = 0;
            while (!(defDices.isEmpty())) {
                Dice diceToMove = getHigherResultDice(defDices);
                defDices.remove(diceToMove);
                diceToMove.addComponent(new DiceMovementsComponent("dice-movements", diceToMove.getPosition(), GameScene.DEF_POSITIONS[pos]));
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
    
    public void setDicesOnScreen(boolean onScreen) {
        dicesOnScreen = onScreen;
    }
    
    public boolean dicesOnScreen() {
        return dicesOnScreen;
    }
}
