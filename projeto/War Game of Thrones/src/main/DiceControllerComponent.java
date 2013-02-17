package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import util.Component;

public class DiceControllerComponent extends Component {
    
    public DiceControllerComponent() {
        
    }
    
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        Input input = gc.getInput();
        float mouseX = input.getAbsoluteMouseX();
        float mouseY = input.getAbsoluteMouseY();
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseOver(mouseX, mouseY))
            ((Dice) owner).setRolling(false);
    }
    
    private boolean mouseOver(float x, float y) {
        return x >= owner.getPosition().x && x <= owner.getPosition().x + Dice.WIDTH && y >= owner.getPosition().y && y <= owner.getPosition().y + Dice.HEIGHT;
    }
    
}
