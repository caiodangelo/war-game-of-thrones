package util;

import main.Main;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class ImageMovementsComponent extends ImageRenderComponent {

    public ImageMovementsComponent(String id, Image img) {
        super(id, img);
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
    
    private void zoom(float scale, float delta, int multiplier, Vector2f position) {
        scale += multiplier * delta;
        setUpdates(scale, position);
    }
    
    protected void setUpdates(float scale, Vector2f position) {
        if (scale < 3 && position.x <= 0 && position.y <= 0 && position.x + getImageWidth(scale) >= Main.windowW && position.y + getImageHeight(scale) >= Main.windowH) {
            owner.setScale(scale);
            owner.setPosition(position);
        }
    }
}