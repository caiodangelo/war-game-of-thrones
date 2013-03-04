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
        if (!player.getMission().isCompleted()) {
            nextAttack = difficulty.nextAttack();
            if (nextAttack != null) {
                Territory destiny = map.getFrontEndTerritory(nextAttack.defender);
                mover.activate(destiny.getArmyRelativePos(), this);
            } else {
                GameScene.getInstance().startAIMovementAnim(player);
            }
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
