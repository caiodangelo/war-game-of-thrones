package util;

import java.awt.DisplayMode;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class MainSceneAnimation extends ImageRenderComponent {

    private float screenWidth;
    private float screenHeight;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;
    private final int animationSpeed = 180;

    public MainSceneAnimation(String id, Image image) {
        super(id, image);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        screenWidth = dm.getWidth();
        screenHeight = dm.getHeight();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        //criar novo Vector2f para que seja apontado um endereço de memória diferente
        Vector2f position = new Vector2f(owner.getPosition());
        if (movingRight)
            moveRight(position, delta);
        if (movingLeft)
            moveLeft(position, delta);
        if (movingUp)
            moveUp(position, delta);
        if (movingDown)
            moveDown(position, delta);
        if (!movingRight && !movingLeft && !movingUp && !movingDown)
            sortDirections();
    }
    
    private void moveLeft(Vector2f position, float delta) {
        position.x += animationSpeed * delta;
        updatePosition(position);
    }
    
    private void moveRight(Vector2f position, float delta) {
        position.x -= animationSpeed * delta;
        updatePosition(position);
    }
    
    private void moveUp(Vector2f position, float delta) {
        position.y += animationSpeed * delta;
        updatePosition(position);
    }
    
    private void moveDown(Vector2f position, float delta) {
        position.y -= animationSpeed * delta;
        updatePosition(position);
    }
    
    private void updatePosition(Vector2f position) {
        if (position.x <= 0 && position.y <= 0 && position.x + getImageWidth(owner.getScale()) >= screenWidth && position.y + getImageHeight(owner.getScale()) >= screenHeight)
            owner.setPosition(position);
        else
            sortDirections();
    }
    
    private void sortDirections() {
        movingLeft = false;
        movingRight = false;
        movingUp = false;
        movingDown = false;
        double n = Math.random();
        if (n < 0.33)
            movingLeft = true;
        else if (n > 0.66)
            movingRight = true;
        n = Math.random();
        if (n < 0.33)
            movingUp = true;
        else if (n > 0.66)
            movingDown = true;
    }

}