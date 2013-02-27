package util;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;
import de.lessvoid.nifty.slick2d.input.SlickSlickInputSystem;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

public abstract class Scene extends NiftyOverlayBasicGameState{
    
    private List<Entity> entities;
    private GameContainer container;
    private List<Entity> entitiesToBeAdded;
    private List<Entity> entitiesToBeRemoved;
    private static boolean inited = false;
    
    public void addEntity(Entity e){
        SlickSlickInputSystem s = new SlickSlickInputSystem(this);
        Scene oldScene = e.getScene();
        if(oldScene != null)
            oldScene.removeEntity(e);
        if(!entities.contains(e)){
            entities.add(e);
            e.setScene(this);
            e.onAdded();
        }
    }
    
    public void addToEntitiesToBeAdded(Entity e){
        SlickSlickInputSystem s = new SlickSlickInputSystem(this);
        Scene oldScene = e.getScene();
        if(oldScene != null)
            oldScene.removeEntity(e);
        entitiesToBeAdded.add(e);
    }
    
    public void removeEntity(Entity e){
        entitiesToBeRemoved.add(e);
    }
    
    public void setupNifty(Nifty n){
        n.gotoScreen("empty");
    }
    
    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException { 
        entities = new ArrayList<Entity>();
        entitiesToBeAdded = new ArrayList<Entity>();
        entitiesToBeRemoved = new ArrayList<Entity>();
        setupNifty(getNifty());
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {}
    
    @Override
    public void prepareNifty(Nifty nifty, StateBasedGame game) {
        if(!inited)
            getNifty().fromXmlWithoutStartScreen("resources/xml/screens.xml");
        inited = true;
    }
    
    @Override
    public void initGameAndGUI(GameContainer container, StateBasedGame game) throws SlickException{
        initNifty(container, game);
    }

    @Override
    public void renderGame(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        for(Entity e : entities)
            e.render(container, game, g);
    }
    
    @Override
    protected void updateGame(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        float dt = delta / 1000f;
        for(Entity e : entities)
            e.update(container, game, dt);
        for(Entity e : entitiesToBeAdded) {
            if(!entities.contains(e)){
                entities.add(e);
                e.setScene(this);
                e.onAdded();
            }
        }
        for(Entity e : entitiesToBeRemoved) {
            if (entities.remove(e)) {
                e.setScene(null);
                e.onRemoved();
            }
        }
        entitiesToBeAdded.clear();
        entitiesToBeRemoved.clear();
        
    }
}


//package util;
//
//import de.lessvoid.nifty.Nifty;
//import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;
//import de.lessvoid.nifty.slick2d.input.SlickSlickInputSystem;
//import java.util.ArrayList;
//import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;
//import org.newdawn.slick.SlickException;
//import org.newdawn.slick.state.StateBasedGame;
//
//import java.util.List;
//
//public abstract class Scene extends NiftyOverlayBasicGameState{
//    
//    private List<Entity> entities;
//    private GameContainer container;
//    private List<Entity> entitiesToBeAdded;
//    private List<Entity> entitiesToBeRemoved;
//    private static boolean inited = false;
//    
//    public void addEntity(Entity e){
//        SlickSlickInputSystem s = new SlickSlickInputSystem(this);
//        Scene oldScene = e.getScene();
//        if(oldScene != null)
//            oldScene.removeEntity(e);
//        entitiesToBeAdded.add(e);
//    }
//    
//    public void removeEntity(Entity e){
//        entitiesToBeRemoved.add(e);
//    }
//    
//    public void setupNifty(Nifty n){
//        n.gotoScreen("empty");
//    }
//    
//    @Override
//    public void enterState(GameContainer container, StateBasedGame game) throws SlickException { 
//        entities = new ArrayList<Entity>();
//        entitiesToBeAdded = new ArrayList<Entity>();
//        entitiesToBeRemoved = new ArrayList<Entity>();
//        setupNifty(getNifty());
//    }
//
//    @Override
//    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {}
//    
//    @Override
//    public void prepareNifty(Nifty nifty, StateBasedGame game) {
//        if(!inited)
//            getNifty().fromXmlWithoutStartScreen("resources/xml/screens.xml");
//        inited = true;
//    }
//    
//    @Override
//    public void initGameAndGUI(GameContainer container, StateBasedGame game) throws SlickException{
//        initNifty(container, game);
//    }
//
//    @Override
//    public void renderGame(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
//        for(Entity e : entities)
//            e.render(container, game, g);
//    }
//    
//    @Override
//    protected void updateGame(GameContainer container, StateBasedGame game, int delta) throws SlickException {
//        float dt = delta / 1000f;
//        for(Entity e : entities)
//            e.update(container, game, dt);
//        for(Entity e : entitiesToBeAdded) {
//            if(!entities.contains(e)){
//                entities.add(e);
//                e.setScene(this);
//                e.onAdded();
//            }
//        }
//        for(Entity e : entitiesToBeRemoved) {
//            if (entities.remove(e)) {
//                e.setScene(null);
//                e.onRemoved();
//            }
//        }
//        entitiesToBeAdded.clear();
//        entitiesToBeRemoved.clear();
//    }
//}
