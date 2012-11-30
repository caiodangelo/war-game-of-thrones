package util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashSet;

public abstract class Scene extends BasicGameState{
    
    private HashSet<Entity> entities;
    
    public Scene() {
        
    }
    
    public void addEntity(Entity e){
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

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        entities = new HashSet<Entity>();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        for(Entity e : entities)
            e.render(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        float dt = delta / 1000f;
        for(Entity e : entities)
            e.update(container, game, dt);
    }
}
