package main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;
import util.ImageMovementsComponent;
import util.PopupManager;

public class Zoom extends ImageMovementsComponent {
    
    //float viewX = 0.5f, viewY = 0.5f;
    //public static final float MOVE_OFFSET = 0.05f;

    public Zoom(String id, Image img) {
        super(id, img);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if(!PopupManager.isAnyPopupOpen()){
            float scale = owner.getScale() * 2;
    //        int mouseWheel = Mouse.getDWheel();
            int mouseWheel = GameScene.getMouseWheel();


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

}