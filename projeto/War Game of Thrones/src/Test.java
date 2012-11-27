import java.awt.DisplayMode;
import org.newdawn.slick.*;

public class Test extends BasicGame{

    private TestObject obj;
    
    public Test(){
        super("Teste da slick");
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        obj = new TestObject();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        obj.update(i / 1000f);
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        obj.render(grphcs);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Test());
        DisplayMode dm = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int w = dm.getWidth(), h = dm.getHeight();
        app.setDisplayMode(w, h, false);
        app.setTargetFrameRate(60);
        app.start();
        
    }
    
    
}
