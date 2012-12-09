package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;
import util.Zoom;

public class GameScene extends Scene{

    Map m = null;
    
    @Override
    public void initGameAndGUI(GameContainer container, StateBasedGame game) throws SlickException {
        super.initGameAndGUI(container, game);
        Image map = new Image("resources/images/mapa.jpg");
        m = new Map();
        m.addComponent(new Zoom("zoom", map));
        m.setPosition(new Vector2f(0, 0));
        addEntity(m);
    }

    @Override
    public int getID() {
        return WarScenes.GAME_SCENE.ordinal();
    }
}
