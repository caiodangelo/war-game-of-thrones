package util;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.slick2d.input.SlickSlickInputSystem;
import de.lessvoid.nifty.slick2d.render.SlickRenderDevice;
import de.lessvoid.nifty.slick2d.sound.SlickSoundDevice;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.spi.render.RenderDevice;
import de.lessvoid.nifty.spi.sound.SoundDevice;
import de.lessvoid.nifty.tools.TimeProvider;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashSet;

public abstract class Scene extends BasicGameState{
    
    private HashSet<Entity> entities;
    private static Nifty n;
    private GameContainer container;
    
    public Nifty getNifty() {
        if(n == null){
            RenderDevice renderer = new SlickRenderDevice(container);
            SoundDevice soundDevice = new SlickSoundDevice();
            InputSystem input = new SlickSlickInputSystem(this);
            TimeProvider provider = new TimeProvider();
            n = new Nifty(renderer, soundDevice, input, provider);
        }
        return n;
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
        this.container = container;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        for(Entity e : entities)
            e.render(container, game, g);
        if(n != null)
            n.render(false);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        float dt = delta / 1000f;
        for(Entity e : entities)
            e.update(container, game, dt);
        if(n != null)
            n.update();
    }
}
