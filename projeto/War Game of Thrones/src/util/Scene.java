package util;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;
import de.lessvoid.nifty.slick2d.input.SlickSlickInputSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashSet;

public abstract class Scene extends NiftyOverlayBasicGameState{
    
    private HashSet<Entity> entities;
    private GameContainer container;
    protected float screenWidth = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
    protected float screenHeight = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
    private static boolean inited = false;
    
    public void addEntity(Entity e){
        SlickSlickInputSystem s = new SlickSlickInputSystem(this);
        Scene oldScene = e.getScene();
        if(oldScene != null)
            oldScene.removeEntity(e);
        if(entities.add(e)){
            e.setScene(this);
            e.onAdded();
        }
    }
    
    public void removeEntity(Entity e){
        if(entities.remove(e)){
            e.setScene(null);
            e.onRemoved();
        }
    }
    
    public void setupNifty(Nifty n){
        n.gotoScreen("emtpy");
    }
    
    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException { 
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
        entities = new HashSet<Entity>();
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
    }
}
