package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
public class MainScreenController implements ScreenController{

    private Nifty n;
    private Screen s;
    private Element exitConfirmPopup, helpPopup;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.n = nifty;
        this.s = screen;
        exitConfirmPopup = n.createPopup("quitConfirmationPopup");
        helpPopup = n.createPopup("helpPopup");
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
    public void showHelp(){
        n.showPopup(s, helpPopup.getId(), null);
    }
    
    public void closeHelpPopup(){
        n.closePopup(helpPopup.getId());
    }
    
    public void exit(){
        n.showPopup(s, exitConfirmPopup.getId(), null);
    }
    
    public void exitGame(){
        main.Main.getInstance().getGameContainer().exit();
    }
    
    public void dismissExitConfirmation(){
        n.closePopup(exitConfirmPopup.getId());
    }
}
