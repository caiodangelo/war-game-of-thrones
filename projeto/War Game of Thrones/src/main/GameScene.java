package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;

public class GameScene extends Scene{
    
    @Override
    public void initGameAndGUI(GameContainer container, StateBasedGame game) throws SlickException {
        super.initGameAndGUI(container, game);
        Image mapImage = new Image("resources/images/mapa.jpg");
        Map map = new Map(mapImage);
        map.addComponent(new Zoom("zoom", map));
        map.addComponent(new Scroll("scroll", map));
        addEntity(map);
    }

    @Override
    public int getID() {
        return WarScenes.GAME_SCENE.ordinal();
    }
}
