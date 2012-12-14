package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.RadioButton;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AddPlayerController implements ScreenController{

    private TextField nameField;
    private Nifty n;
    private DropDown housesDropdown;
    private List<String> availableHouses;
    private List<PlayerData> createdPlayers;
    private RadioButton humanButton;
    private Button addButton, playButton;
    
    public void resetController(){
        addButton.enable();
        playButton.disable();
        
        String houses[] = {"Stark", "Targaryen", "Lannister", "Greyjoy", "Baratheon", "Free Folk"};
        createdPlayers = new ArrayList<PlayerData>();
        availableHouses = new ArrayList<String>();
        availableHouses.addAll(Arrays.asList(houses));
        resetDisplay();
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        n = nifty;
        
        
        addButton = screen.findNiftyControl("addPlayerButton", Button.class);
        playButton = screen.findNiftyControl("playButton", Button.class);
        nameField = screen.findNiftyControl("nameTextField", TextField.class);
        housesDropdown = screen.findNiftyControl("dropDown2", DropDown.class);
        humanButton = screen.findNiftyControl("humanRadioBtn", RadioButton.class);
    }
    
    private void resetDisplay(){
        humanButton.select();
        housesDropdown.clear();
        
        for(String house : availableHouses)
            housesDropdown.addItem(house);
        
        if(nameField != null){
            nameField.setText("");
            nameField.setFocus();
        }
    }
    
    @Override
    public void onStartScreen() {   
        resetController();
    }

    @Override
    public void onEndScreen() {    }
    
    public void addPlayer(){
        if(nameField != null && createdPlayers.size() < 6){
            String name = nameField.getDisplayedText();
            boolean isHuman = humanButton.isActivated();
            String house = (String)housesDropdown.getSelection();
            PlayerData pd = new PlayerData(name, isHuman, house);
            createdPlayers.add(pd);
            availableHouses.remove(house);
            resetDisplay();
            playButton.enable();
        }
        if(createdPlayers.size() == 6){
            addButton.disable();
        }
    }
    
    public void playButtonPressed() {
        main.Main.getInstance().enterState(main.WarScenes.GAME_SCENE);
    }
    
    @NiftyEventSubscriber(id="nameTextField")
    public void onPlayerNameChanged(String id, TextFieldChangedEvent evt){
        nameField = evt.getTextFieldControl();
    }
    
    public void closePopup(){
        n.gotoScreen("startingScreen");
    }
    
    private static class PlayerData{
        String name, house;
        boolean isHuman;
        public PlayerData(String name, boolean isHuman, String house){
            this.name = name;
            this.isHuman = isHuman;
            this.house = house;
        }
    }
}
