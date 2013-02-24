package main;

import gui.InGameGUIController;
import models.StatisticGameManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Component;

public class DiceMovementsComponent extends Component {

    private static final float TIME_TO_START = 1;
    private static final int SHAKING_AMPLITUDE = 3;
    
    private double trajetory;
    private Vector2f originalPosition;
    private Vector2f destination;
    private boolean movingDown;
    private boolean winner;
    private float timer;

    public DiceMovementsComponent(String id, Vector2f pos, Vector2f dest, boolean w) {
        originalPosition = pos;
        destination = dest;
        movingDown = originalPosition.y < dest.y;
        trajetory = 0;
        winner = w;
        timer = 0;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        boolean reached = false;
        DiceManager dm = DiceManager.getInstance();
        int multiplier;
        if (timer >= TIME_TO_START) {
            if ((movingDown && trajetory >= Math.PI) || (!movingDown && trajetory >= Math.PI))
                reached = true;
            if (!reached) {
                if (movingDown)
                    multiplier = 1;
                else
                    multiplier = -1;
                parametricTrajetory(multiplier);
            }
            else {
                if (!((Dice) owner).hasReachedDestination()) {
                    ((Dice) owner).setReachedDestination(true);
                    dm.checkIfAllDicesReachedDestination();
                }
                if (dm.allDicesOnCorrectPosition()) {
                    if (winner)
                        shake();
                    InGameGUIController guiControl = InGameGUIController.getInstance();
                    guiControl.setInfoLabelText("Clique para dispensar os dados.");
                    Input i = gc.getInput();
                    if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
//                        if some player wins, call end game animation
//                        StatisticGameManager.getInstance().setPlayTime();
//                        Main.getInstance().enterState(WarScenes.STATISTICS_SCENE);
//                        else
                        dm.removeDices();
                        guiControl.setInfoLabelText(null);
                    }
                }
            }
        }
        else
            timer += delta;
    }
    
    private void parametricTrajetory(int multiplier) {
        owner.setPosition(new Vector2f(parametricTrajetoryX(multiplier), parametricTrajetoryY(multiplier)));
        trajetory += 0.05;
    }
    
    private float parametricTrajetoryX(int mult) {
        if (destination.y == originalPosition.y) //dice won't move
            return owner.getPosition().x;
        else
            return (float) (originalPosition.x + mult * 20 * Math.cos(trajetory + Math.PI/2.0));
    }
    
    private float parametricTrajetoryY(int mult) {
        return (float) (originalPosition.y + mult * Math.abs(originalPosition.y - destination.y) * Math.sin(trajetory/2.0));   
    }
    
    private void shake() {
        float x = owner.getPosition().x;
        float y = owner.getPosition().y;
        owner.setPosition(new Vector2f((float) (x + SHAKING_AMPLITUDE * Math.cos(trajetory)), (float) (y + SHAKING_AMPLITUDE * Math.sin(trajetory))));
        trajetory += 0.5;
    }
}
