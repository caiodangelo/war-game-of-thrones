package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.ImageMovementsComponent;
import util.PopupManager;

public class Scroll extends ImageMovementsComponent {
    
    float viewX = 0.5f, viewY = 0.5f;
    public static final float MOVE_OFFSET = 0.005f;
    private int previousMouseX, previousMouseY;
    private boolean mouseSet = false;
    
    private Vector2f mapPos, mapSize;

    public Scroll(String id, Image img) {
        super(id, img);
        mapPos = main.Main.getMapPos();
        mapSize = main.Main.getMapSize();
        try {
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(Scroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if(!PopupManager.isAnyPopupOpen()){
            Input input = gc.getInput();
            int mouseX = 0;
            int mouseY = 0;
            int newMouseX = input.getMouseX();
            int newMouseY = (int)mapSize.y-input.getMouseY();
            if(mouseSet){
                mouseX = newMouseX - previousMouseX;
                mouseY = newMouseY - previousMouseY;
            }
            previousMouseX = newMouseX;
            previousMouseY = newMouseY;
            mouseSet = true;
            if (input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON))
                Mouse.setGrabbed(true);
            else
                Mouse.setGrabbed(false);
            boolean isGrabbed = Mouse.isGrabbed();

            if (scrolledLeft(isGrabbed, mouseX))
                viewX += MOVE_OFFSET;

            if (scrolledRight(isGrabbed, mouseX))
                viewX -= MOVE_OFFSET;

            if (scrolledUp(isGrabbed, mouseY))
                viewY -= MOVE_OFFSET;

            if (scrolledDown(isGrabbed, mouseY))
                viewY += MOVE_OFFSET;

            checkView();

            //zoom checking
            float scale = owner.getScale();
            int mouseWheel = GameScene.getMouseWheel();

            if (mouseWheel > 0)
                zoomIn(scale, delta, owner.getPosition());

            if (mouseWheel < 0)
                zoomOut(scale, delta, owner.getPosition());
            Vector2f position = new Vector2f();

            position.x = mapPos.x + (mapSize.x / 2f) - viewX * getImageWidth(owner.getScale());
            position.y = mapPos.y + (mapSize.y / 2f) - viewY * getImageHeight(owner.getScale());
            setUpdates(owner.getScale(), position);
            
        }
    }
    
    private float getMinViewX(){
        float resp = (mapSize.x/2f) / (getImageWidth(owner.getScale()));
        return resp;
    }
    
    private float getMaxViewX(){
        float resp = 1-((mapSize.x/2f) / (getImageWidth(owner.getScale())));
        return resp;
    }
    
    private float getMinViewY(){
        float resp = (mapSize.y/2f) / (getImageHeight(owner.getScale()));
        return resp;
    }
    
    private float getMaxViewY(){
        float resp = 1-((mapSize.y/2f) / (getImageHeight(owner.getScale())));
        return resp;
    }
    
    private void checkView(){
        float maxX = getMaxViewX();
        float maxY = getMaxViewY();
        float minX = getMinViewX();
        float minY = getMinViewY();
        
        viewX = Math.max(minX, viewX);
        viewX = Math.min(maxX, viewX);            
        viewY = Math.max(minY, viewY);
        viewY = Math.min(maxY, viewY);            
    }
}