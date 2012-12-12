package main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;
import util.ImageMovementsComponent;

public class Zoom extends ImageMovementsComponent {
    
    //float viewX = 0.5f, viewY = 0.5f;
    //public static final float MOVE_OFFSET = 0.05f;

    public Zoom(String id, Entity owner) {
        super(id, owner);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float scale = owner.getScale();
        int mouseWheel = Mouse.getDWheel();
        
        if (mouseWheel > 0) {
            zoomIn(scale, delta, owner.getPosition());
        }

        if (mouseWheel < 0) {
            zoomOut(scale, delta, owner.getPosition());
        }
//        position.x = (main.Main.windowW / 2f) - viewX * getImageWidth(scale);
//        position.y = (main.Main.windowH / 2f) - viewY * getImageHeight(scale);
    }

}