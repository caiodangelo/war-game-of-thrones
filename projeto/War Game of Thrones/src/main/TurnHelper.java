package main;

import gui.InGameGUIController;
import models.AIPlayer;
import models.Board;
import models.Difficulty;
import models.Player;
import models.TerritoryTransaction;

public class TurnHelper {

    private GameScene parent;
    private Board b;
    private InGameGUIController ctrl;
    private static TurnHelper instance;
    private IAHelper iaHelper;

    public TurnHelper(GameScene parent, InGameGUIController ctrl) {
        instance = this;
        this.parent = parent;
        this.ctrl = ctrl;
        b = Board.getInstance();
    }

    public static TurnHelper getInstance() {
        return instance;
    }

    public void changeTurn() {
        b.changePlayer();
        Player curr = b.getCurrentPlayer();
        for (models.BackEndTerritory t : b.getCurrentPlayer().getTerritories()) {
            t.resetMovedArmies();
        }
        if (b.isOnInitialSetup()) {
            if (!curr.isAIPlayer()) {
                ctrl.showInfoTerritories();
//                parent.resetMapPosition();
            } else {
                iaHelper = new IAHelper(this, (AIPlayer) curr);
                iaHelper.handleIAStart();
            }
//                ctrl.startPlayerInitialDistribution();
        } else {
            parent.showPlayerTurnMsg();
            if (b.isOnFirstTurn())
                ctrl.setRavenMessage(curr.getName() + " está jogando.");
            else
                ctrl.setRavenMessage(curr.getName() + " recebeu \\#CC0000#" + curr.getPendingArmies() + "\\#333333ff# exército(s) para distribuir.");
            if (curr.isAIPlayer()) {
                iaHelper = new IAHelper(this, (AIPlayer) curr);
                iaHelper.handleIAStart();
            }
        }
        ctrl.updatePlayersData();
    }

    public IAHelper getIaHelper() {
        return iaHelper;
    }
}