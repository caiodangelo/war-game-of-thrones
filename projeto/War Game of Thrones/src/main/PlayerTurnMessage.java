package main;

import de.lessvoid.nifty.tools.Color;
import util.Entity;

public class PlayerTurnMessage extends Entity{

    private PlayerTurnMessageRenderer renderer;
    
    public PlayerTurnMessage(){
        addComponent(renderer = new PlayerTurnMessageRenderer("turnRenderer"));
    }

    void activate(String playerName, Color c) {
        renderer.activate(playerName, c);
    }
    
}
