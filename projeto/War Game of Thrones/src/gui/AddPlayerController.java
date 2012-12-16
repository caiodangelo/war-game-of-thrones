package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.RadioButton;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AddPlayerController implements ScreenController{

    private TextField nameField;
    private Nifty n;
    private DropDown housesDropdown;
    private List<HouseData> availableHouses;
    private List<PlayerData> createdPlayers;
    private RadioButton humanButton;
    private Button addButton, playButton;
    private Element iconsPanel;
    private Element playerIcons[];
    
    public void resetController(){
        addButton.enable();
        playButton.disable();
        for(int i = 1; i < playerIcons.length; i++)
            playerIcons[i].hide();
        HouseData houses[] = {  new HouseData("Stark", "resources/images/logo_stark.png"),
                                new HouseData("Targaryen", "resources/images/logo_targaryen.png"),  
                                new HouseData("Greyjoy", "resources/images/logo_greyjoy.png"),  
                                new HouseData("Baratheon", "resources/images/logo_baratheon.png"),  
                                new HouseData("Free Folk", "resources/images/logo_free_folk.png"),  
                                new HouseData("Lannister", "resources/images/logo_Lannister.png")
        };
        createdPlayers = new ArrayList<PlayerData>();
        availableHouses = new ArrayList<HouseData>();
        availableHouses.addAll(Arrays.asList(houses));
        resetDisplay();
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        n = nifty;
        playerIcons = new Element[6];
        for(int i = 0; i < 6; i++)
            playerIcons[i] = screen.findElementByName("player" + (i+1) + "Icon");
        iconsPanel = screen.findElementByName("playerIconsPanel");
        addButton = screen.findNiftyControl("addPlayerButton", Button.class);
        playButton = screen.findNiftyControl("playButton", Button.class);
        nameField = screen.findNiftyControl("nameTextField", TextField.class);
        housesDropdown = screen.findNiftyControl("dropDown2", DropDown.class);
        humanButton = screen.findNiftyControl("humanRadioBtn", RadioButton.class);
    }
    
    public void updatePlayerImage(){
        int playerID = createdPlayers.size();
        Element icon = playerIcons[playerID];
        ImageRenderer r = icon.getRenderer(ImageRenderer.class);
        HouseData houseData = (HouseData)housesDropdown.getSelection();
        if(houseData != null)
            r.setImage(n.createImage(houseData.imgPath, false));
    }
    
    public void dropDownClicked(){
        updatePlayerImage();
    }
    
    private void resetDisplay(){
        humanButton.select();
        housesDropdown.clear();
        
        for(HouseData house : availableHouses)
            housesDropdown.addItem(house);
        
        if(nameField != null){
            nameField.setText("");
            nameField.setFocus();
        }
        
        for(int i = 0; i < createdPlayers.size(); i++){
            HouseData house = createdPlayers.get(i).house;
            Element icon = playerIcons[i];
            ImageRenderer img = icon.getRenderer(ImageRenderer.class);
            img.setImage(n.createImage(house.imgPath, false));
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
            updatePlayerImage();
            String name = nameField.getDisplayedText();
            boolean isHuman = humanButton.isActivated();
            HouseData houseData = (HouseData)housesDropdown.getSelection();
            PlayerData pd = new PlayerData(name, isHuman, houseData);
            createdPlayers.add(pd);
            availableHouses.remove(houseData);
            resetDisplay();
            playButton.enable();
            int nextPlayer = createdPlayers.size();
            playerIcons[nextPlayer].show();
            updatePlayerImage();
        }
        if(createdPlayers.size() == 6){
            addButton.disable();
        }
    }
    
    public void playButtonPressed() {
        main.Main.getInstance().enterState(main.WarScenes.GAME_SCENE);
    }
    
    @NiftyEventSubscriber(id="dropDown2")
    public void onDropdownSelectionChanged(String id, DropDownSelectionChangedEvent evt){
        updatePlayerImage();
    }
    
    @NiftyEventSubscriber(id="nameTextField")
    public void onPlayerNameChanged(String id, TextFieldChangedEvent evt){
        nameField = evt.getTextFieldControl();
    }
    
    public void closePopup(){
        n.gotoScreen("startingScreen");
    }
    
    private static class PlayerData{
        String name;
        HouseData house;
        boolean isHuman;
        public PlayerData(String name, boolean isHuman, HouseData house){
            this.name = name;
            this.isHuman = isHuman;
            this.house = house;
        }
    }
    
    private static class HouseData{
        String name;
        String imgPath;
        public HouseData(String houseName, String imageFile){
            name = houseName;
            imgPath = imageFile;
        }
        
        public NiftyImage createImage(Nifty n){
            return n.createImage(imgPath, false);
        }
        
        @Override
        public String toString(){
            return name;
        }
    }
}