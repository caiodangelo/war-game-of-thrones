import de.lessvoid.nifty.Nifty;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;

public class MainScene extends Scene{

    private Nifty n;
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        n = getNifty();
        n.fromXml("addPlayer.xml", "addPlayer", new AddPlayerController());
    }

    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

}
