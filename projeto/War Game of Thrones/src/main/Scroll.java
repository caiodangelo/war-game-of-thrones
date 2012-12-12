package main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;
import util.ImageMovementsComponent;

public class Scroll extends ImageMovementsComponent {
    
    //float viewX = 0.5f, viewY = 0.5f;
    //public static final float MOVE_OFFSET = 0.05f;

    public Scroll(String id, Entity owner) {
        super(id, owner);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float positionX = owner.getPosition().getX();
        float positionY = owner.getPosition().getY();
        Input input = gc.getInput();
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
            setUpdates(owner.getScale(), new Vector2f(positionX, positionY));
        }

        if (scrolledRight(isGrabbed, mouseX)) {
            positionX += mouseX;
            //viewX -= MOVE_OFFSET;
            setUpdates(owner.getScale(), new Vector2f(positionX, positionY));
        }

        if (scrolledUp(isGrabbed, mouseY)) {
            positionY -= mouseY;
            //viewY -= MOVE_OFFSET;
            setUpdates(owner.getScale(), new Vector2f(positionX, positionY));
        }

        if (scrolledDown(isGrabbed, mouseY)) {
            positionY -= mouseY;
            //viewY += MOVE_OFFSET;
            setUpdates(owner.getScale(), new Vector2f(positionX, positionY));
        }
    }

}