package main;

import gui.InGameGUIController;
import models.AIPlayer;
import models.Board;
import models.Player;

public class TurnHelper {
    
    private GameScene parent;
    private Board b;
    private InGameGUIController ctrl;
    private static TurnHelper instance;
    
    public TurnHelper(GameScene parent, InGameGUIController ctrl){
        instance = this;
        this.parent = parent;
        this.ctrl = ctrl;
        b = Board.getInstance();
    }
    
    public static TurnHelper getInstance(){
        return instance;
    }
    
    public void changeTurn(){
        b.changePlayer();
        Player curr = b.getCurrentPlayer();
        for(models.BackEndTerritory t : b.getCurrentPlayer().getTerritories()) {
            t.resetMovedArmies();
        }
        if(b.isOnInitialSetup()){
            if(!curr.isAIPlayer())
                ctrl.showInfoTerritories();
            else {
//                ctrl
//                AIPlayer ai = (AIPlayer)curr;
                ctrl.startPlayerInitialDistribution();
//                ai.getDifficulty().distributeArmies();
//                ctrl.updatePlayersData();
//                changeTurn();
            }
            
        }
        else {
            parent.showPlayerTurnMsg();
            if (b.isOnFirstTurn())
                ctrl.setRavenMessage(curr.getName()+" está jogando.");
            else
                ctrl.setRavenMessage("\\#333333ff#"+curr.getName()+" recebeu \\#CC0000#"+curr.getPendingArmies()+"\\#333333ff# exército(s) para distribuir.");
        }
        ctrl.updatePlayersData();
    }
}
