package main;


import static main.Constants.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import util.Component;

public class DiceControllerComponent extends Component {
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        Dice diceOwner = (Dice)owner;
        Input input = gc.getInput();
        float mouseX = input.getAbsoluteMouseX();
        float mouseY = input.getAbsoluteMouseY();
        if ((input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && (SINGLE_CLICK_STOP_DICES || mouseOver(mouseX, mouseY))) || diceOwner.isIsAIDice())
            diceOwner.setRolling(false);
    }
    
    private boolean mouseOver(float x, float y) {
        return x >= owner.getPosition().x && x <= owner.getPosition().x + Dice.WIDTH && y >= owner.getPosition().y && y <= owner.getPosition().y + Dice.HEIGHT;
    }
    
}
