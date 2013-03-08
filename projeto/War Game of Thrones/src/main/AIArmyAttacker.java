package main;

import gui.ContextMenuController;
import gui.InGameGUIController;
import models.AIPlayer;
import models.Difficulty;
import models.TerritoryTransaction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;

/**
 *
 * @author rodrigo
 */
public class AIArmyAttacker extends Entity implements MovementCompleteListener {

    private AIPlayer player;
    private Difficulty difficulty;
    private Map map;
    private MapMover mover;
    private ContextMenuController ctrl;
    private TerritoryTransaction nextAttack = null;

    public AIArmyAttacker(AIPlayer p, MapMover mover) {
        this.player = p;
        this.mover = mover;
        this.map = GameScene.getInstance().getMap();
        difficulty = player.getDifficulty();
        ctrl = InGameGUIController.getInstance().getContextMenuController();
    }

    public void start() {
        System.out.println("AI ARMY ATTACKER START");
        processNextZoom();
    }

    private void processNextZoom() {
        try{
            if (!player.getMission().isCompleted()) {
                System.out.println("ia obj not complete");
                nextAttack = difficulty.nextAttack();
                System.out.println("next attack is " + nextAttack);
                if (nextAttack != null && nextAttack.isValid()) {
                    System.out.println("ia next attack not null");
                    Territory destiny = map.getFrontEndTerritory(nextAttack.defender);
                    mover.activate(destiny.getArmyRelativePos(), this);
                } else {
                    System.out.println("ia next attack null");
                    GameScene.getInstance().startAIMovementAnim(player);
                }
            }
            else
                GameScene.getInstance().startGameEndingAnimation();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        super.update(gc, sb, delta);
    }

    @Override
    public void onMovementComplete() {
        Territory origin = map.getFrontEndTerritory(nextAttack.attacker);
        Territory destiny = map.getFrontEndTerritory(nextAttack.defender);
        int count = nextAttack.numberOfAttackers;
        System.out.println("B atk with " + count + " from " + origin + " to " + destiny);
        ctrl.showIAAttackPopup(origin, destiny, count);
    }
}
