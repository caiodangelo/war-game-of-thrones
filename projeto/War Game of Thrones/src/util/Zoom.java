package util;

import java.awt.DisplayMode;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Zoom extends ImageRenderComponent {

    private float screenWidth;
    private float screenHeight;
    
    //float viewX = 0.5f, viewY = 0.5f;
    //public static final float MOVE_OFFSET = 0.05f;

    public Zoom(String id, Image image) {
        super(id, image);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        screenWidth = dm.getWidth();
        screenHeight = dm.getHeight();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float scale = owner.getScale();
        float positionX = owner.getPosition().getX();
        float positionY = owner.getPosition().getY();
        Input input = gc.getInput();
        int mouseWheel = Mouse.getDWheel();
        int mouseX = Mouse.getDX();
        int mouseY = Mouse.getDY();
        if (input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON))
            Mouse.setGrabbed(true);
        else
            Mouse.setGrabbed(false);
        boolean isGrabbed = Mouse.isGrabbed();

        if (scrolledLeft(isGrabbed, mouseX)) {
            positionX += mouseX;
            //viewX += MOVE_OFFSET;
            setUpdates(scale, new Vector2f(positionX, positionY));
        }

        if (scrolledRight(isGrabbed, mouseX)) {
            positionX += mouseX;
            //viewX -= MOVE_OFFSET;
            setUpdates(scale, new Vector2f(positionX, positionY));
        }

        if (scrolledUp(isGrabbed, mouseY)) {
            positionY -= mouseY;
            //viewY -= MOVE_OFFSET;
            setUpdates(scale, new Vector2f(positionX, positionY));
        }

        if (scrolledDown(isGrabbed, mouseY)) {
            positionY -= mouseY;
            //viewY += MOVE_OFFSET;
            setUpdates(scale, new Vector2f(positionX, positionY));
        }
        
        if (mouseWheel > 0) {
            zoomIn(scale, delta, new Vector2f(positionX, positionY));
        }

        if (mouseWheel < 0) {
            zoomOut(scale, delta, new Vector2f(positionX, positionY));
        }
//        position.x = (main.Main.windowW / 2f) - viewX * getImageWidth(scale);
//        position.y = (main.Main.windowH / 2f) - viewY * getImageHeight(scale);
    }
    
    private void zoom(float scale, float delta, int multiplier, Vector2f position) {
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

    private void zoomIn(float scale, float delta, Vector2f position) {
        zoom(scale, delta, 1, position);
    }

    private void zoomOut(float scale, float delta, Vector2f position) {
        zoom(scale, delta, -1, position);
    }

    private void setUpdates(float scale, Vector2f position) {
        if (scale < 3 && position.x <= 0 && position.y <= 0 && position.x + getImageWidth(scale) >= screenWidth && position.y + getImageHeight(scale) >= screenHeight) {
            owner.setScale(scale);
            owner.setPosition(position);
        }
    }
    
    private boolean scrolledLeft(boolean isGrabbed, int mouseX) {
        return isGrabbed && mouseX < 0;
    }
    
    private boolean scrolledRight(boolean isGrabbed, int mouseX) {
        return isGrabbed && mouseX > 0;
    }
    
    private boolean scrolledUp(boolean isGrabbed, int mouseY) {
        return isGrabbed && mouseY < 0;
    }
    
    private boolean scrolledDown(boolean isGrabbed, int mouseY) {
        return isGrabbed && mouseY > 0;
    }
}