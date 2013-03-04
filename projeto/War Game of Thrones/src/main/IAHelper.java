package main;

import gui.ContextMenuController;
import gui.InGameGUIController;
import models.AIPlayer;
import models.Board;
import models.Difficulty;
import models.TerritoryTransaction;
import org.newdawn.slick.geom.Vector2f;

public class IAHelper {

    private TurnHelper turnHelper;
    private AIPlayer p;
    private Board b;

    public IAHelper(TurnHelper turnHelper, AIPlayer aiPlayer) {
        this.turnHelper = turnHelper;
        this.p = aiPlayer;
        b = Board.getInstance();
    }

    public void handleIAStart() {
        if (!b.isOnFirstTurn()) {
            System.out.println("IA distributing");
            GameScene.getInstance().startAIDistributionAnim(p, this);
        } else {
            System.out.println("AI can't distribute on first turn");
            GameScene.getInstance().startAIAttackerAnim(p);
        }
    }
//    public void next(){
//        System.out.println("next");
//        Difficulty d = p.getDifficulty();
//
//        TerritoryTransaction tt = d.nextAttack();
////        while(tt != null)
////            tt = d.nextAttack();
////        tt = null;
//        Map m = GameScene.getInstance().getMap();
//        boolean keepAttacking = tt != null;
//        System.out.println("keep atk? " + keepAttacking);
//        ContextMenuController ctrl = InGameGUIController.getInstance().getContextMenuController();
//
//        if(keepAttacking){
//            Territory origin = m.getFrontEndTerritory(tt.attacker);
//            Territory dest = m.getFrontEndTerritory(tt.defender);
//            int count = tt.numberOfAttackers;
//            System.out.println("B atk with " + count + " from " + origin + " to " +dest);
////            GameScene.getInstance().startAIDistributionAnim(p, this);
//            ctrl.showIAAttackPopup(origin, dest, count);
//        } else {
//            GameScene.getInstance().startAIMovementAnim(p);
////            System.out.println("else");
////            tt = d.nextMove();
////            while(tt != null){
////                Territory origin = m.getFrontEndTerritory(tt.attacker);
////                Territory dest = m.getFrontEndTerritory(tt.defender);
////                int count = tt.numberOfAttackers;
////                System.out.println("IA moving " + count + " from " + origin + " to " + dest);
////                ctrl.performeRearrange(origin, dest,count);
////                tt = d.nextMove();
////            }
////            System.out.println("ia called change turn");
////            turnHelper.changeTurn();
//        }
//
//    }
}
