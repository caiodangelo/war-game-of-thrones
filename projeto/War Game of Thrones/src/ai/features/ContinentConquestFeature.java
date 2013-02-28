package ai.features;

import ai.Feature;
import models.Board;
import models.Player;

/**
 * Essa feature vai ver o quão o gameState é bom ou não em relação à Region que o player quer conquistar
 *
 * @author rodrigo
 */
public class ContinentConquestFeature extends Feature {

    public ContinentConquestFeature() {
        importance = 3;
        scaleFactor = 1;
    }

    @Override
    public double calculate(Board gameState, Player player) {
        // TODO: calcular esta feature
        return 0;
    }

}
