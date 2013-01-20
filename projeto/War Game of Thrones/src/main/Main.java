package main;

import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;
import java.awt.DisplayMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Main extends NiftyStateBasedGame{
    
    private static Main instance;
    
    public static float windowW, windowH;
    
    private AppGameContainer container;
    
    private Main(){
        super("War Game of Thrones", true);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int w = dm.getWidth(), h = dm.getHeight();
        windowW = w;
        windowH = h;
    }
    
    public AppGameContainer getGameContainer(){
        return container;
    }
    
    public void enterState(WarScenes scene) {
        enterState(scene.ordinal());
    }
    
    public static Main getInstance(){
        if(instance == null)
            instance = new Main();
        return instance;
    }

    public static void main(String[] args) throws SlickException {
        disableNiftyWarnings();
        Main m = getInstance();
        
        AppGameContainer app = new AppGameContainer(m);
        m.container = app;
        boolean fullscreen = false;
        app.setDisplayMode((int)windowW, (int)windowH, fullscreen);
        app.setTargetFrameRate(60);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainScene());
        addState(new GameScene());
        enterState(WarScenes.STARTING_SCENE);
//        enterState(WarScenes.GAME_SCENE);
    }
    
    private static void disableNiftyWarnings(){
        Logger.getLogger("").setLevel(Level.SEVERE);
    }
}
