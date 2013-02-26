package main;

import de.lessvoid.nifty.tools.Color;
import models.Player;
import util.Entity;

public class GameEndingAnimation extends Entity{

    private GameEndingAnimationRenderer renderer;
    
    public GameEndingAnimation(){
        addComponent(renderer = new GameEndingAnimationRenderer("gameEndingRenderer"));
    }

    void activate(Player winner) {
        renderer.activate(winner);
    }
    
}
