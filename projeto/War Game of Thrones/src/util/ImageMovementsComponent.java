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
        Vector2f mapPos = Main.getMapPos();
        Vector2f mapSize = Main.getMapSize();
        Vector2f newPos = owner.getPosition();
        if (scale < 2 && (position.x <= mapPos.x && position.x + getImageWidth(scale) >= mapSize.x + mapPos.x) && (position.y <= mapPos.y && position.y + getImageHeight(scale) >= mapSize.y + mapPos.y))
            owner.setScale(scale);
        if (position.x <= mapPos.x && position.x + getImageWidth(scale) >= mapSize.x + mapPos.x) {
            newPos = new Vector2f(position.x, newPos.y);
            owner.setPosition(newPos);
        }
        if (position.y <= mapPos.y && position.y + getImageHeight(scale) >= mapSize.y + mapPos.y) {
            newPos = new Vector2f(newPos.x, position.y);
            owner.setPosition(newPos);
        }
    }
}