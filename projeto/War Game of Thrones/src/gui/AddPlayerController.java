package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.ScreenController;
public class AddPlayerController implements ScreenController{

    private TextFieldControl nameField;
    private Nifty n;
    private Element popUp;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        n = nifty;
        nameField = screen.findControl("nameField", TextFieldControl.class);
        DropDown dp = screen.findNiftyControl("dropDown2", DropDown.class);
        String houses[] = {"Stark", "Targaryen", "Lannister", "Greyjoy", "Baratheon", "Free Folk"};
        for(String house : houses)
            dp.addItem(house);
    }
    
    @Override
    public void onStartScreen() {    }

    @Override
    public void onEndScreen() {    }
    
    public void next(){
        System.out.println("elemento foi clicado!");
        System.out.println(nameField.getText());
    }
    
    
    public void playButtonPressed() {
        main.Main.getInstance().enterState(main.WarScenes.GAME_SCENE.ordinal());
    }
    
    public void closePopup(){
        n.gotoScreen("startingScreen");
    }
}
