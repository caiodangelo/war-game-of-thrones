package main;

import gui.ContextMenuController;
import gui.InGameGUIController;
import models.AIPlayer;
import models.Difficulty;
import models.TerritoryTransaction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;

/**
 *
 * @author rodrigo
 */
public class AIArmyMovementer extends Entity implements MovementCompleteListener {

    private AIPlayer player;
    private Difficulty difficulty;
    private Map map;
    private MapMover mover;
    private ContextMenuController ctrl;
    private TerritoryTransaction nextRearrange = null;

    public AIArmyMovementer(AIPlayer p, MapMover mover) {
        this.player = p;
        this.mover = mover;
        this.map = GameScene.getInstance().getMap();
        difficulty = player.getDifficulty();
        ctrl = InGameGUIController.getInstance().getContextMenuController();
    }

    public void start() {
        System.out.println("AI ARMY MOVEMENTER START");
        processNextZoom();
    }

    private void processNextZoom() {
        if (!player.getMission().isCompleted()) {
            nextRearrange = difficulty.nextMove();
            if (nextRearrange != null && nextRearrange.isValid()) {
                Territory destiny = map.getFrontEndTerritory(nextRearrange.defender);
                mover.activate(destiny.getArmyRelativePos(), this);
            } else {
                InGameGUIController.getInstance().nextPlayerTurn();
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        super.update(gc, sb, delta);
    }

    @Override
    public void onMovementComplete() {
        Territory origin = map.getFrontEndTerritory(nextRearrange.attacker);
        Territory destiny = map.getFrontEndTerritory(nextRearrange.defender);
        ctrl.performeRearrange(origin, destiny, 1);
        processNextZoom();
    }
}
