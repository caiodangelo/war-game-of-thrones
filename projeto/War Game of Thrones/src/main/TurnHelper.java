package main;

import gui.InGameGUIController;
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
        parent.showPlayerTurnMsg();
        if(b.isOnInitialSetup())
            ctrl.showInfoTerritories();
        else {
            Player curr = b.getCurrentPlayer();
            ctrl.setRavenMessage(curr.getName() + " está jogando.");
        }
        ctrl.updatePlayersData();
    }
}
