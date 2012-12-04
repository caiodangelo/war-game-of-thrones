import com.sun.org.apache.xerces.internal.util.XML11Char;
import de.lessvoid.nifty.Nifty;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;

public class MainScene extends Scene{

    private Nifty n;
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public String getScreenName() {
        return "addPlayer";
    }
}
