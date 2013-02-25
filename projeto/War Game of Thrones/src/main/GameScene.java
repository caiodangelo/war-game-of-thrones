package main;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.tools.Color;
import gui.InGameGUIController;
import java.util.Date;
import models.Board;
import models.Player;
import models.StatisticGameManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;

public class GameScene extends Scene{
    
    private static int mouseWheel;
    private PlayerTurnMessage turnMsg;
    private Board b;
    private InGameGUIController ctrl;
    private TurnHelper helper;
    
    
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
        StatisticGameManager.getInstance().setInitTime(new Date());
        Map map = new Map();
        addEntity(map);
        //Left button was pressed to play but this record must be erased.
        container.getInput().clearMousePressedRecord();
        AudioManager am = AudioManager.getInstance();
        am.stopMusic(AudioManager.OPENING);
        DiceManager dm = DiceManager.getInstance();
        dm.setGameScene(this);
        
        turnMsg = new PlayerTurnMessage();
        addEntity(turnMsg);
        InGameGUIController.getInstance().showInfoTerritories();
        showPlayerTurnMsg();
        b = Board.getInstance();
//        turnMsg.activate(b.getPlayer(0).getName());
        ctrl = InGameGUIController.getInstance();
        helper = new TurnHelper(this, ctrl);
    }
    
    public void showPlayerTurnMsg(){
        Board b = Board.getInstance();
        Player p = b.getCurrentPlayer();
        String playerName = p.getName();
        Color c = p.getHouse().getColor();
        
        turnMsg.activate(playerName, c);
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

    void handleTerritoryClick(Territory territory) {
        Player curr = b.getCurrentPlayer();
        if(!b.isOnInitialSetup())
            InGameGUIController.handleTerritoryClick(territory);
        else{
            ctrl.updatePlayersData();
            int pendingArmies = curr.getPendingArmies();
            if(territory.getBackEndTerritory().getOwner() == curr){
                models.Territory t = territory.getBackEndTerritory();
                t.increaseArmies(1);
                curr.removePendingArmies(1);
                pendingArmies--;
            } 
            if(pendingArmies == 0) {
                ctrl.setInfoLabelText(null);
                helper.changeTurn();
            }
            else
                ctrl.setInfoLabelText("Você ainda possui "+curr.getPendingArmies()+" exército(s) para distribuir.");
        }
    }
    
}
