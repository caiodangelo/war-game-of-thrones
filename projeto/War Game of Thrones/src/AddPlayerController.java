import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.ScreenController;
public class AddPlayerController implements ScreenController{

    public static int generalID = 0;
    int myId = 0;
    private TextFieldControl nameField;
    private Nifty n;
    private Screen s;
    
    public AddPlayerController(){
        myId = generalID++;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("binded");
        this.n = nifty;
        this.s = screen;
        nameField = screen.findControl("nameField", TextFieldControl.class);
    }
    
    public void showAddPlayerMenu(){
        System.out.println("SHOW");
        Element popUp = n.createPopup("popupAddPlayer");
        n.showPopup(n.getCurrentScreen(), popUp.getId(), null);
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
