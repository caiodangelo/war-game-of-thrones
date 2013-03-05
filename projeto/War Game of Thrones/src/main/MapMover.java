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

   

    private enum ScrollState {WAITING, MOVING, COMPLETE_DELAY}
    private ScrollState state = ScrollState.WAITING;
    
    private static final float SPEED = 0.1f;
    private static final float END_SCALE = 1.5f;
    private static final float COMPLETE_WAIT = 0.5f;
    private float elapsed = 0;
    private Vector2f start, end, moveVector;
    private float startScale = 0, endScale;
    
    private MovementCompleteListener listener;
    
    public MapMover(Map map){
        this.map = map;
        scrollComponent = map.getScroll();
    }
    
    public void activate(Vector2f destPosition, MovementCompleteListener l){
        activate(destPosition, END_SCALE,l);
    }
    
    public void activate(Vector2f destPosition, float endScale, MovementCompleteListener l) {
        state = ScrollState.MOVING;
        start = new Vector2f(scrollComponent.viewX, scrollComponent.viewY);
        end = destPosition;
        moveVector = sub(end, start);
        moveVector.normalise();
        moveVector.scale(SPEED);
        state =  ScrollState.MOVING;
        startScale = map.getScale();
        this.endScale = endScale;
        this.listener = l; 
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        super.update(gc, sb, delta);
        switch(state){
            case WAITING:
//            Input in = gc.getInput();
//            if(in.isKeyDown(Input.KEY_D)){
//                Vector2f dest = map.getMouseRelativePosition(in);
//                activate(dest, null);
//            }
                break;
            case MOVING:
                if(reachedDestiny()){
                    scrollComponent.viewX = end.x;
                    scrollComponent.viewY = end.y;
                    terminate();
                    return;
                }
                float moveX = moveVector.x * delta;
                float moveY = moveVector.y * delta;
                scrollComponent.zoomIn(map.getScale(), 1/60f, map.getPosition());
                scrollComponent.viewX += moveX;
                scrollComponent.viewY += moveY;
                float newScale = (endScale - startScale) * getPctgComplete() + startScale;
                map.setScale(newScale);
                if(mapOutofBounds())
                    terminate();
                break;
            case COMPLETE_DELAY:
                elapsed += delta;
                if(elapsed >= COMPLETE_WAIT){
                    state = ScrollState.WAITING;
                    if(listener != null){
                        MovementCompleteListener temp = listener;
                        listener = null;
                        temp.onMovementComplete();
                    }
                }
                break;
        }
    }
    
    private void terminate(){
        state = ScrollState.COMPLETE_DELAY;
        elapsed = 0;
    }
    
    public float getPctgComplete(){
        float originalDist = end.distance(start);
        Vector2f curPos = new Vector2f(scrollComponent.viewX, scrollComponent.viewY);
        float currDist = curPos.distance(start);
        if(originalDist == 0)
            return 1;
        return Math.min(currDist / originalDist, 1f);
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
