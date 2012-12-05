package util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Zoom extends Component {
    
    private boolean scrollingDown;
    private boolean scrollingUp;
    private boolean scrollingRight;
    private boolean scrollingLeft;
    private int scrollSpeed;
    private final int speedIncrement = 2;

    public Zoom(String id) {
        this.id = id;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float rotation = owner.getRotation();
        float scale = owner.getScale();
        Vector2f position = owner.getPosition();

        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_A)) {
            if (scrollingLeft)
                scrollSpeed += speedIncrement;
            else {
                scrollingDown = false;
                scrollingUp = false;
                scrollingRight = false;
                scrollingLeft = true;
            }
            position.x -= scrollSpeed * delta;
        }

        if (input.isKeyDown(Input.KEY_D)) {
            if (scrollingRight)
                scrollSpeed += speedIncrement;
            else {
                scrollingDown = false;
                scrollingUp = false;
                scrollingRight = true;
                scrollingLeft = false;
            }
            position.x += scrollSpeed * delta;
        }

        if (input.isKeyDown(Input.KEY_W)) {
            if (scrollingUp)
                scrollSpeed += speedIncrement;
            else {
                scrollingDown = false;
                scrollingUp = true;
                scrollingRight = false;
                scrollingLeft = false;
            }
            position.y += scrollSpeed * delta;
        }
        
        if (input.isKeyDown(Input.KEY_S)) {
            if (scrollingDown)
                scrollSpeed += speedIncrement;
            else {
                scrollingDown = true;
                scrollingUp = false;
                scrollingRight = false;
                scrollingLeft = false;
            }
            position.y -= scrollSpeed * delta;
        }
        
        if (noScrollKeyPressed(input)) {
            scrollingDown = false;
            scrollingUp = false;
            scrollingRight = false;
            scrollingLeft = false;
            scrollSpeed = 300;
        }
        
        if (input.isKeyDown(Input.KEY_X)) {
            scale += 1 * delta;
        }
        
        if (input.isKeyDown(Input.KEY_C)) {
            scale += -1 * delta;
        }

        owner.setPosition(position);
        owner.setRotation(rotation);
        owner.setScale(scale);
    }
    
    private boolean noScrollKeyPressed(Input input) {
        return !input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S);
    }
}