package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
public class MainScreenController implements ScreenController{

    private Nifty n;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.n = nifty;
    }

    @Override
    public void onStartScreen() {    }

    @Override
    public void onEndScreen() {    }
    
    //Button click actions
    public void showAddPlayerMenu(){
        n.gotoScreen("addPlayer");
    }
    
    public void showOptions(){}
    public void showCredits(){}
    
    public void exit(){
        main.Main.getInstance().getContainer().exit();
    }
}
