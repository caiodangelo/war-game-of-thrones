package main;

import de.lessvoid.nifty.tools.Color;
import models.Player;
import util.Entity;

public class PlayerTurnMessage extends Entity{

    private PlayerTurnMessageRenderer renderer;
    
    public PlayerTurnMessage(){
        setLayer(3);
        addComponent(renderer = new PlayerTurnMessageRenderer("turnRenderer"));
    }

    void activate(Player player, Color c) {
        renderer.activate(player, c);
    }
    
}
