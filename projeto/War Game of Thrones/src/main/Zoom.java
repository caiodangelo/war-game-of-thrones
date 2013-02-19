package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageMovementsComponent;
import util.PopupManager;

public class Zoom extends ImageMovementsComponent {

    public Zoom(String id, Image img) {
        super(id, img);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if(!PopupManager.isAnyPopupOpen()) {
            float scale = owner.getScale() * 2;
            int mouseWheel = GameScene.getMouseWheel();

            if (mouseWheel > 0)
                zoomIn(scale, delta, owner.getPosition());

            if (mouseWheel < 0)
                zoomOut(scale, delta, owner.getPosition());
        }
    }

}