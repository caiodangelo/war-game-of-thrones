package main;

import static main.Constants.*;
import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;
import java.awt.DisplayMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import util.LibraryLoader;

public class Main extends NiftyStateBasedGame{
    
    private static Main instance;
    public static float windowW, windowH;
    private static Vector2f mapPos, mapSize;
    private static boolean showingTerritoriesNames;
    
    public static Vector2f getMapPos(){
        if(mapPos == null){
            float x, y;
            x = windowW * 0.12f;
            y = windowH * 0.03f;
            mapPos = new Vector2f(x, y);
        }
        return mapPos;
    }
    
    public static Vector2f getMapSize(){
        if(mapSize == null){
            float width, height;
            width = windowW * 0.88f;
            height = windowH * 0.97f * (1-0.18f);
            mapSize = new Vector2f(width, height);
        }
        return mapSize;
    }
    
    private AppGameContainer container;
    
    private Main(){
        super("War Game of Thrones", true);
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int w = dm.getWidth(), h = dm.getHeight();
        windowW = w;
        windowH = h;
        showingTerritoriesNames = true;
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

    
    public static void main(String[] args) throws Exception {
        LibraryLoader.loadLibrary();
        
        disableNiftyWarnings();
        Main m = getInstance();
        
        AppGameContainer app = new AppGameContainer(m);
        m.container = app;
        app.setDisplayMode((int)windowW, (int)windowH, FULLSCREEN);
        app.setTargetFrameRate(60);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainScene());
        addState(new GameScene());
        addState(new StatisticsScene());
        if(JUMP_TO_STATISTICS)
            enterState(WarScenes.STATISTICS_SCENE);
        else if(!JUMP_TO_GAME){
            enterState(WarScenes.STARTING_SCENE);
        } else
            enterState(WarScenes.GAME_SCENE);
    }
    
    private static void disableNiftyWarnings(){
        Logger.getLogger("").setLevel(Level.SEVERE);
    }
    
    public static boolean isShowingTerritoriesNames() {
        return showingTerritoriesNames;
    }
    
    public static void showTerritoriesNames(boolean s) {
        showingTerritoriesNames = s;
    }
}
