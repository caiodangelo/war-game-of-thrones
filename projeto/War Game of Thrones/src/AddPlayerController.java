import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
public class AddPlayerController implements ScreenController{

    public static int generalID = 0;
    int myId = 0;
    
    public AddPlayerController(){
        myId = generalID++;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("binded");
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }
    
    public void next(){
        System.out.println("elemento foi clicado!");
    }
}
