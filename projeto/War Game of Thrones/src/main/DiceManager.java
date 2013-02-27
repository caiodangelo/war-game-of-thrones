package main;

import gui.InGameGUIController;
import java.util.ArrayList;
import models.Battle;
import models.Board;
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
    private Battle battle;
    private boolean dicesOnScreen;
    private boolean dicesOnCorrectPosition;
    private Territory attackingTerritory;
    private Territory defendingTerritory;
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
            dicesSet = dicesSet && (!ad.isRolling());
        }
        for (Dice dd : defDices) {
            dicesSet = dicesSet && (!dd.isRolling());
        }
        if (dicesSet) {
            int pos = 0;
            ArrayList<Dice> tempAtkDices = new ArrayList<Dice>(atkDices);
            ArrayList<Dice> tempDefDices = new ArrayList<Dice>(defDices);
            boolean atkIsWinner = false;
            Dice atkDiceToMove;
            Dice defDiceToMove;
            while (!tempAtkDices.isEmpty() && !tempDefDices.isEmpty()) {
                atkDiceToMove = getHigherResultDice(tempAtkDices);
                defDiceToMove = getHigherResultDice(tempDefDices);
                if (atkDiceToMove.getResult() > defDiceToMove.getResult())
                    atkIsWinner = true;
                else
                    atkIsWinner = false;
                tempAtkDices.remove(atkDiceToMove);
                atkDiceToMove.addComponent(new DiceMovementsComponent("dice-movements", atkDiceToMove.getPosition(), ATK_POSITIONS[pos], atkIsWinner));
                tempDefDices.remove(defDiceToMove);
                defDiceToMove.addComponent(new DiceMovementsComponent("dice-movements", defDiceToMove.getPosition(), DEF_POSITIONS[pos], !atkIsWinner));
                pos++;
            }
            while (!tempAtkDices.isEmpty()) {
                atkDiceToMove = getHigherResultDice(tempAtkDices);
                tempAtkDices.remove(atkDiceToMove);
                atkDiceToMove.addComponent(new DiceMovementsComponent("dice-movements", atkDiceToMove.getPosition(), ATK_POSITIONS[pos], atkIsWinner));
                pos++;
            }
            while (!tempDefDices.isEmpty()) {
                defDiceToMove = getHigherResultDice(tempDefDices);
                tempDefDices.remove(defDiceToMove);
                defDiceToMove.addComponent(new DiceMovementsComponent("dice-movements", defDiceToMove.getPosition(), DEF_POSITIONS[pos], !atkIsWinner));
                pos++;
            }
        }
    }
    
    public Dice getHigherResultDice(ArrayList<Dice> list) {
        if (list.isEmpty() || list == null)
            return null;
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
            d = new Dice(ATK_POSITIONS[i], true, battle.getAttackersDices()[i]);
            atkDices.add(d);
            gameScene.addEntity(d);
        }
        for (int i = 0; i < def; i++) {
            d = new Dice(DEF_POSITIONS[i], false, battle.getDefendersDices()[i]);
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
        if (attackingTerritory != null) {
            ArmyRenderComponent comp = (ArmyRenderComponent) attackingTerritory.getArmy().getComponent("army-renderer");
            comp.startExplosion();
            AudioManager.getInstance().playSound(AudioManager.ATTACK_SOUND);
        }
        attackingTerritory = null;
    }
    
    public void removeDices() {
        atkDices.addAll(defDices); //concatennating
        for(Dice d : atkDices) {
            gameScene.removeEntity(d);
        }
        atkDices.clear();
        defDices.clear();
        dicesOnScreen = false;
        dicesOnCorrectPosition = false;
        InGameGUIController guiController = InGameGUIController.getInstance();
        String currPlayerName = Board.getInstance().getCurrentPlayer().getName();
        String attackedPlayerName = defendingTerritory.getBackEndTerritory().getOwner().getName();
        int atkDeaths = battle.getAttackerDeaths();
        battle.getAttacker().setMovedArmies(atkDeaths);
        int defDeaths = battle.getDefendersDeaths();
        battle.getDefender().setMovedArmies(defDeaths);
        guiController.setRavenMessage(currPlayerName+" sofreu "+atkDeaths+" baixa(s)! "+attackedPlayerName+" sofreu "+defDeaths+" baixa(s)!");
        battle.concludeAttack();
        if (battle.isConquested()) {
            guiController.selectVictoriousArmiesToMove(battle.getNumberAttackers() - atkDeaths);
            guiController.setRavenMessage(currPlayerName+" conquistou o território de "+attackedPlayerName+"!");
            defendingTerritory.getArmy().changeImage();
        }
    }
    
    public boolean allDicesOnCorrectPosition() {
        return dicesOnCorrectPosition;
    }
    
    public void setAttackingTerritory(Territory attacker) {
        attackingTerritory = attacker;
    }
    
    public void setDefendingTerritory(Territory defender) {
        defendingTerritory = defender;
    }
    
    public void setBattle(Battle b) {
        battle = b;
    }
    
    public Battle getBattle() {
        return battle;
    }

}
