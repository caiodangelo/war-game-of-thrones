package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Component;

public class DiceMovementsComponent extends Component {

    private static final float X_DISPLACEMENT = 0.5f;
    private static final float Y_DISPLACEMENT = 1;
    private static final int TIME_TO_START = 70;
    private Vector2f originalPosition;
    private Vector2f destination;
    private boolean movingDown;
    private int timer;

    public DiceMovementsComponent(String id, Vector2f pos, Vector2f dest) {
        originalPosition = pos;
        destination = dest;
        movingDown = originalPosition.y < dest.y;
        timer = 0;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        float x = owner.getPosition().x;
        float y = owner.getPosition().y;
        int xMultiplier = 0;
        int yMultiplier = 0;
        boolean reached = false;
        if (timer >= TIME_TO_START) {
            if (movingDown) {
                if (y >= destination.y)
                    reached = true;
                else if ((destination.y - y) < (destination.y - originalPosition.y) / 2)
                    xMultiplier = 1;
                else
                    xMultiplier = -1;
                yMultiplier = 1;
            } else {
                if (y <= destination.y)
                    reached = true;
                else if ((y - destination.y) < (originalPosition.y - destination.y) / 2)
                    xMultiplier = 1;
                else
                    xMultiplier = -1;
                yMultiplier = -1;
            }
            if (!reached)
                owner.setPosition(new Vector2f(x + (X_DISPLACEMENT * xMultiplier), y + (Y_DISPLACEMENT * yMultiplier)));
        } else {
            timer++;
        }
    }
}
