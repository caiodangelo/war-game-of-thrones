package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import util.Component;

public class FireworksManagerComponent extends Component {
    
    private static final float TIME_TO_DRAW_NEW_ANIMATION = 0.2f;
    
    private float timer;
    protected String id;

    public FireworksManagerComponent(String id) {
        this.id = id;
        timer = TIME_TO_DRAW_NEW_ANIMATION;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        if (timer >= TIME_TO_DRAW_NEW_ANIMATION) {
            timer = 0;
            ((FireworksManager) owner).drawNewAnimation();
        }
        else
            timer += delta;
    }
    
}
