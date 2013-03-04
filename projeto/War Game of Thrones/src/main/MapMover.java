package main;

import static main.VectorOps.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;

public class MapMover extends Entity{

    private Map map;
    private Scroll scrollComponent;
    private enum ScrollState {WAITING, MOVING}
    private ScrollState state = ScrollState.WAITING;
    
    private final float speed = 0.1f;
    private Vector2f start, end, moveVector;
    
    public MapMover(Map map){
        this.map = map;
        scrollComponent = map.getScroll();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        super.update(gc, sb, delta);
        if(state == ScrollState.WAITING){
            Input in = gc.getInput();
//            System.out.println(map.getMouseRelativePosition(in));
            if(in.isKeyDown(Input.KEY_D)){
                start = new Vector2f(scrollComponent.viewX, scrollComponent.viewY);
                //random value
                end = map.getMouseRelativePosition(in);
                moveVector = sub(end, start);
                moveVector.normalise();
                moveVector.scale(speed);
                state =  ScrollState.MOVING;
            }
        } else {
            
            if(reachedDestiny()){
                scrollComponent.viewX = end.x;
                scrollComponent.viewY = end.y;
                state = ScrollState.WAITING;
                return;
            }
            float moveX = moveVector.x * delta;
            float moveY = moveVector.y * delta;
            scrollComponent.viewX += moveX;
            scrollComponent.viewY += moveY;
            if(mapOutofBounds()){
                state = ScrollState.WAITING;
            }
            
        }
    }
    
    private boolean mapOutofBounds(){
        boolean validateX = scrollComponent.validateViewX();
        boolean validateY = scrollComponent.validateViewY();
        return !validateX && !validateY;
    }
    
    private boolean reachedDestiny(){
        float originalSignX = Math.signum(moveVector.x);
        float currSignX = Math.signum(end.x - scrollComponent.viewX);
        
        float originalSignY = Math.signum(moveVector.y);
        float currSignY = Math.signum(end.y - scrollComponent.viewY);
        
        return originalSignX != currSignX || originalSignY != currSignY;
    }
    
    

}
