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

    public Zoom(String id, Image image) {
        super(id, image);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        screenWidth = dm.getWidth();
        screenHeight = dm.getHeight();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        //float rotation = owner.getRotation();
        float scale = owner.getScale();
        //criar novo Vector2f para que seja apontado um endereço de memória diferente
        Vector2f position = new Vector2f(owner.getPosition());

        Input input = gc.getInput();
        int mouseWheel = Mouse.getDWheel();
        int mouseX = Mouse.getDX();
        int mouseY = Mouse.getDY();

        if (scrolledLeft(input, mouseX)) {
            position.x += 50 * mouseX * delta;
            setUpdates(scale, position);
        }

        if (scrolledRight(input, mouseX)) {
            position.x += 50 * mouseX * delta;
            setUpdates(scale, position);
        }

        if (scrolledUp(input, mouseY)) {
            position.y -= 50 * mouseY * delta;
            setUpdates(scale, position);
        }

        if (scrolledDown(input, mouseY)) {
            position.y -= 50 * mouseY * delta;
            setUpdates(scale, position);
        }
        
        if (mouseWheel > 0) {
            zoomIn(scale, delta, position);
        }

        if (mouseWheel < 0) {
            zoomOut(scale, delta, position);
        }
        //owner.setRotation(rotation);
    }
    
    private void zoom(float scale, float delta, int multiplier, Vector2f position) {
        float oldW = getImageWidth(scale);
        float oldH = getImageHeight(scale);
        scale += multiplier * delta;
        float newW = getImageWidth(scale);
        float newH = getImageHeight(scale);
        position.x -= ((newW - oldW) / 2f);
        position.y -= ((newH - oldH) / 2f);
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
    
    private boolean scrolledLeft(Input input, int mouseX) {
        System.out.println(mouseX);
        return input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON) && mouseX < 0;
    }
    
    private boolean scrolledRight(Input input, int mouseX) {
        return input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON) && mouseX > 0;
    }
    
    private boolean scrolledUp(Input input, int mouseY) {
        return input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON) && mouseY < 0;
    }
    
    private boolean scrolledDown(Input input, int mouseY) {
        return input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON) && mouseY > 0;
    }
}