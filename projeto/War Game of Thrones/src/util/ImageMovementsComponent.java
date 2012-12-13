package util;

import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ImageMovementsComponent extends ImageRenderComponent {
    
    //float viewX = 0.5f, viewY = 0.5f;
    //public static final float MOVE_OFFSET = 0.05f;

    public ImageMovementsComponent(String id, Entity owner) {
        super(id, owner);
    }
    
    protected void zoom(float scale, float delta, int multiplier, Vector2f position) {
        float oldW = getImageWidth(scale);
        float oldH = getImageHeight(scale);
        scale += multiplier * delta;
        float newW = getImageWidth(scale);
        float newH = getImageHeight(scale);
//        position.x = (main.Main.windowW / 2f) - viewX * image.getWidth() * scale;
//        position.y = (main.Main.windowH / 2f) - viewY * image.getHeight() * scale;
//        System.out.printf("\nviewx %f viewy %f \n", viewX, viewY);
//        position.x -= ((newW - oldW) / 2f);
//        position.y -= ((newH - oldH) / 2f);
        setUpdates(scale, position);
    }

    protected void zoomIn(float scale, float delta, Vector2f position) {
        zoom(scale, delta, 1, position);
    }

    protected void zoomOut(float scale, float delta, Vector2f position) {
        zoom(scale, delta, -1, position);
    }
    
    protected boolean scrolledLeft(boolean isGrabbed, int mouseX) {
        return isGrabbed && mouseX < 0;
    }
    
    protected boolean scrolledRight(boolean isGrabbed, int mouseX) {
        return isGrabbed && mouseX > 0;
    }
    
    protected boolean scrolledUp(boolean isGrabbed, int mouseY) {
        return isGrabbed && mouseY < 0;
    }
    
    protected boolean scrolledDown(boolean isGrabbed, int mouseY) {
        return isGrabbed && mouseY > 0;
    }
    
    protected void setUpdates(float scale, Vector2f position) {
        if (scale < 3 && position.x <= 0 && position.y <= 0 && position.x + getImageWidth(scale) >= Main.windowW && position.y + getImageHeight(scale) >= Main.windowH) {
            owner.setScale(scale);
            owner.setPosition(position);
        }
    }
}