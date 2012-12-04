import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;

public class GameScene extends Scene{

    @Override
    public void initGameAndGUI(GameContainer container, StateBasedGame game) throws SlickException {
        super.initGameAndGUI(container, game);
        addEntity(new Map());
    }

    @Override
    public int getID() {
        return WarScenes.GAME_SCENE.ordinal();
    }
}
