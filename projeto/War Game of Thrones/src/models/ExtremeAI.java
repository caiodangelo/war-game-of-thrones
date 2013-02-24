package models;

import ai.DistributionEvaluator;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class ExtremeAI extends Difficulty {

    @Override
    public void distributeArmies() {
        while (player.getPendingArmies() > 0) {
            Territory chosen = null;
            double maxRating = 0.0;
            for (Territory territory : player.getTerritories()) {
                DistributionEvaluator evaluator = new DistributionEvaluator(Board.getInstance(), player);
                evaluator.setTerritory(territory);
                double rating = evaluator.evaluate();
                maxRating = Math.max(maxRating, rating);
                if (maxRating == rating) {
                    chosen = territory;
                }
            }
            player.distributeArmies(chosen, 1);
        }
    }

    @Override
    protected boolean keepAttacking() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CardTerritory> tradeCards() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TerritoryTransaction nextAttack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int moveAfterConquest(Territory origin, Territory conquered, int numberCanMove) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean keepMoving() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TerritoryTransaction nextMove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}