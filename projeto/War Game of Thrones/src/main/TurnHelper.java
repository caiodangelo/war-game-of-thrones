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
        if (!b.isOnInitialSetup()) {
            System.out.println(b.getCurrentPlayer().getMission().getName());
            System.out.println(b.getCurrentPlayer().getMission().isCompleted());
        }
        for(models.BackEndTerritory t : b.getCurrentPlayer().getTerritories()) {
            t.resetMovedArmies();
        }
        if(b.isOnInitialSetup())
            ctrl.showInfoTerritories();
        else {
            parent.showPlayerTurnMsg();
            Player curr = b.getCurrentPlayer();
            if (b.isOnFirstTurn())
                ctrl.setRavenMessage(curr.getName()+" está jogando.");
            else
                ctrl.setRavenMessage("\\#333333ff#"+curr.getName()+" ainda possui \\#CC0000#"+curr.getPendingArmies()+"\\#333333ff# exército(s) para distribuir.");
        }
        ctrl.updatePlayersData();
    }
}
