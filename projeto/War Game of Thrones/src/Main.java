import java.awt.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;

public class Main extends NiftyStateBasedGame{
    
    public Main(){
        super("War Game of Thrones", true);
        
    }

    public static void main(String[] args) throws SlickException {
        Main m = new Main();
        AppGameContainer app = new AppGameContainer(m);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int w = dm.getWidth(), h = dm.getHeight();
        boolean fullscreen = false;
        
        app.setDisplayMode(w, h, fullscreen);
        app.setTargetFrameRate(60);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new GameScene());
        addState(new MainScene());
//        enterState(WarScenes.STARTING_SCENE.ordinal());
        enterState(WarScenes.GAME_SCENE.ordinal());
    }
    
}
