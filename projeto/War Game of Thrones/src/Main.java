import java.awt.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame{
    
    public Main(){
        super("War Game of Thrones");
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main());
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
    }
    
}
