package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.RadioButton;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import de.lessvoid.nifty.controls.Label;

public class AddPlayerController implements ScreenController{

    private TextField nameField;
    private Nifty n;
    private DropDown housesDropdown;
    private List<HouseData> availableHouses;
    private List<PlayerData> createdPlayers;
    private RadioButton humanButton, cpuButton;
    private Button addButton, playButton;
    private Element iconsPanel;
    private Element [] playerIcons;
    private Label [] playerNames;
    private int currentEditingIndex = -1;
    
    
    //Overriden methods
    @Override
    public void bind(Nifty nifty, Screen screen) {
        n = nifty;
        playerIcons = new Element[6];
        for(int i = 0; i < 6; i++)
            playerIcons[i] = screen.findElementByName("player" + i + "Icon");
        playerNames = new Label[6];
        for(int i = 0; i < 6; i++)
            playerNames[i] = screen.findNiftyControl("player" + i + "Name", Label.class);
        iconsPanel = screen.findElementByName("playerIconsPanel");
        addButton = screen.findNiftyControl("addPlayerButton", Button.class);
        playButton = screen.findNiftyControl("playButton", Button.class);
        nameField = screen.findNiftyControl("nameTextField", TextField.class);
        housesDropdown = screen.findNiftyControl("dropDown2", DropDown.class);
        humanButton = screen.findNiftyControl("humanRadioBtn", RadioButton.class);
        cpuButton = screen.findNiftyControl("cpuRadioBtn", RadioButton.class);
    }
    
    @Override
    public void onStartScreen() {   
        resetController();
    }

    @Override
    public void onEndScreen() {    }
    
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
        addBlankPlayerData();
        resetDisplay();
    }
    
    public void updatePlayerImage(){
        Element icon = playerIcons[currentEditingIndex];
        ImageRenderer r = icon.getRenderer(ImageRenderer.class);
        HouseData houseData = (HouseData)housesDropdown.getSelection();
        if(houseData != null)
            r.setImage(n.createImage(houseData.imgPath, false));
        EffectEventId effectID = EffectEventId.onCustom;
        for(Element e : playerIcons)
            e.stopEffect(effectID);
        icon.startEffect(effectID);
    }
    
    private void resetDisplay(){
        housesDropdown.clear();
        PlayerData currentPlayer = createdPlayers.get(currentEditingIndex);
        (currentPlayer.isHuman ? humanButton : cpuButton).select();
        
        String playerName = currentPlayer.name;
        nameField.setText(playerName);
        nameField.setFocus();
        nameField.setCursorPosition(playerName.length());
        
        for(HouseData house : availableHouses)
            housesDropdown.addItem(house);
        for(int i = 0; i < 6; i++){
            Element icon = playerIcons[i];
            Label name = playerNames[i];
            if(i < createdPlayers.size()){
                ImageRenderer r = icon.getRenderer(ImageRenderer.class);
                PlayerData pd = createdPlayers.get(i);
                HouseData houseData = pd.house;
                r.setImage(n.createImage(houseData.imgPath, false));
                icon.show();
                
                String pName = pd.name;
                name.setText(pName);
            } else {
                icon.hide();
                name.setText("");
            }
        }
        housesDropdown.selectItem(currentPlayer.house);
    }
    
    //Elements interaction callbacks
    private void editPlayer(String playerIndex, boolean saveCurrent){
        if(saveCurrent)
            saveCurrentPlayerData();
        currentEditingIndex = Integer.parseInt(playerIndex);
        PlayerData editedPlayer = createdPlayers.get(currentEditingIndex);
        availableHouses.add(0, editedPlayer.house);
        resetDisplay();
    }
    
    public void editPlayer(String playerIndex){
        editPlayer(playerIndex, true);
    }
    
    private void addBlankPlayerData(){
        PlayerData dummy = new PlayerData("", true, availableHouses.get(0));
        createdPlayers.add(dummy);
        currentEditingIndex = createdPlayers.size() - 1;
    }
    
    private void saveCurrentPlayerData(){
        String name = nameField.getDisplayedText();
        boolean isHuman = humanButton.isActivated();
        HouseData houseData = (HouseData)housesDropdown.getSelection();
        PlayerData pd = new PlayerData(name, isHuman, houseData);
        if(createdPlayers.size() <= currentEditingIndex)
            createdPlayers.add(pd);
        else
            createdPlayers.set(currentEditingIndex, pd);
        availableHouses.remove(houseData);
    }
    
    public void addPlayer(){
        if(nameField != null && createdPlayers.size() < 6){
            updatePlayerImage();
            saveCurrentPlayerData();
            addBlankPlayerData();
            
            resetDisplay();
            playButton.enable();
            playerIcons[currentEditingIndex].show();
            updatePlayerImage();
        }
        if(createdPlayers.size() == 6){
            addButton.disable();
            addButton.setText("Máximo de jogadores");
        }
    }
    
    public void excludePlayer(){
        PlayerData removedPlayer = createdPlayers.remove(currentEditingIndex);
        if(currentEditingIndex >= createdPlayers.size())
            currentEditingIndex--;
        if(currentEditingIndex < 0)
            resetController();
        else {
            editPlayer(currentEditingIndex + "", false);
            addButton.enable();
            addButton.setText("+Adicionar Jogador");
            if(createdPlayers.size() == 1)
                playButton.disable();
            resetDisplay();
            
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
        
        playerNames[currentEditingIndex].setText(evt.getText());
    }
    
    public void closePopup(){
        n.gotoScreen("startingScreen");
    }
    
    
    //Auxiliary classes
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
//package gui;
//
//import de.lessvoid.nifty.Nifty;
//import de.lessvoid.nifty.NiftyEventSubscriber;
//import de.lessvoid.nifty.controls.DropDown;
//import de.lessvoid.nifty.controls.TextField;
//import de.lessvoid.nifty.controls.Button;
//import de.lessvoid.nifty.controls.RadioButton;
//import de.lessvoid.nifty.controls.TextFieldChangedEvent;
//import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
//import de.lessvoid.nifty.effects.EffectEventId;
//import de.lessvoid.nifty.elements.Element;
//import de.lessvoid.nifty.elements.render.ImageRenderer;
//import de.lessvoid.nifty.elements.render.TextRenderer;
//import de.lessvoid.nifty.render.NiftyImage;
//import de.lessvoid.nifty.screen.Screen;
//import de.lessvoid.nifty.screen.ScreenController;
//import java.util.Arrays;
//import java.util.List;
//import java.util.ArrayList;
//import de.lessvoid.nifty.controls.Label;
//
//public class AddPlayerController implements ScreenController{
//
//    private TextField nameField;
//    private Nifty n;
//    private DropDown housesDropdown;
//    private List<HouseData> availableHouses;
//    private List<PlayerData> createdPlayers;
//    private RadioButton humanButton, cpuButton;
//    private Button addButton, playButton;
//    private Element iconsPanel;
//    private Element [] playerIcons, playerNames;
//    private Label l;
//    private int currentEditingIndex = -1;
//    
//    
//    //Overriden methods
//    @Override
//    public void bind(Nifty nifty, Screen screen) {
//        n = nifty;
//        playerIcons = new Element[6];
//        for(int i = 0; i < 6; i++)
//            playerIcons[i] = screen.findElementByName("player" + i + "Icon");
//        playerNames = new Element[6];
//        for(int i = 0; i < 6; i++)
//            playerNames[i] = screen.findElementByName("player" + i + "Name");
//        l = screen.findNiftyControl("lbl", Label.class);
//        iconsPanel = screen.findElementByName("playerIconsPanel");
//        addButton = screen.findNiftyControl("addPlayerButton", Button.class);
//        playButton = screen.findNiftyControl("playButton", Button.class);
//        nameField = screen.findNiftyControl("nameTextField", TextField.class);
//        housesDropdown = screen.findNiftyControl("dropDown2", DropDown.class);
//        humanButton = screen.findNiftyControl("humanRadioBtn", RadioButton.class);
//        cpuButton = screen.findNiftyControl("cpuRadioBtn", RadioButton.class);
//    }
//    
//    @Override
//    public void onStartScreen() {   
//        resetController();
//    }
//
//    @Override
//    public void onEndScreen() {    }
//    
//    public void resetController(){
//        addButton.enable();
//        playButton.disable();
//        for(int i = 1; i < playerIcons.length; i++)
//            playerIcons[i].hide();
//        HouseData houses[] = {  new HouseData("Stark", "resources/images/logo_stark.png"),
//                                new HouseData("Targaryen", "resources/images/logo_targaryen.png"),  
//                                new HouseData("Greyjoy", "resources/images/logo_greyjoy.png"),  
//                                new HouseData("Baratheon", "resources/images/logo_baratheon.png"),  
//                                new HouseData("Free Folk", "resources/images/logo_free_folk.png"),  
//                                new HouseData("Lannister", "resources/images/logo_Lannister.png")
//        };
//        createdPlayers = new ArrayList<PlayerData>();
//        availableHouses = new ArrayList<HouseData>();
//        availableHouses.addAll(Arrays.asList(houses));
//        addBlankPlayerData();
//        resetDisplay();
//    }
//    
//    public void updatePlayerImage(){
//        Element icon = playerIcons[currentEditingIndex];
//        ImageRenderer r = icon.getRenderer(ImageRenderer.class);
//        HouseData houseData = (HouseData)housesDropdown.getSelection();
//        if(houseData != null)
//            r.setImage(n.createImage(houseData.imgPath, false));
//        EffectEventId effectID = EffectEventId.onCustom;
//        for(Element e : playerIcons)
//            e.stopEffect(effectID);
//        icon.startEffect(effectID);
//    }
//    
//    private void resetDisplay(){
//        housesDropdown.clear();
//        PlayerData currentPlayer = createdPlayers.get(currentEditingIndex);
//        (currentPlayer.isHuman ? humanButton : cpuButton).select();
//        
//        String playerName = currentPlayer.name;
//        nameField.setText(playerName);
//        nameField.setFocus();
//        nameField.setCursorPosition(playerName.length());
//        
//        for(HouseData house : availableHouses)
//            housesDropdown.addItem(house);
//        
//        for(int i = 0; i < 6; i++){
//            Element icon = playerIcons[i];
//            Element name = playerNames[i];
//            if(i < createdPlayers.size()){
//                ImageRenderer r = icon.getRenderer(ImageRenderer.class);
//                PlayerData pd = createdPlayers.get(i);
//                HouseData houseData = pd.house;
//                r.setImage(n.createImage(houseData.imgPath, false));
//                icon.show();
//                
//                String pName = pd.name;
//                TextRenderer t = name.getRenderer(TextRenderer.class);
//                t.setText(pName);
//                name.show();
//            } else {
//                icon.hide();
//                name.hide();
//            }
//        }
//        housesDropdown.selectItem(currentPlayer.house);
//    }
//    
//    //Elements interaction callbacks
//    private void editPlayer(String playerIndex, boolean saveCurrent){
//        if(saveCurrent)
//            saveCurrentPlayerData();
//        currentEditingIndex = Integer.parseInt(playerIndex);
//        PlayerData editedPlayer = createdPlayers.get(currentEditingIndex);
//        availableHouses.add(0, editedPlayer.house);
//        resetDisplay();
//    }
//    
//    public void editPlayer(String playerIndex){
//        editPlayer(playerIndex, true);
//    }
//    
//    private void addBlankPlayerData(){
//        PlayerData dummy = new PlayerData("", true, availableHouses.get(0));
//        createdPlayers.add(dummy);
//        currentEditingIndex = createdPlayers.size() - 1;
//    }
//    
//    private void saveCurrentPlayerData(){
//        String name = nameField.getDisplayedText();
//        boolean isHuman = humanButton.isActivated();
//        HouseData houseData = (HouseData)housesDropdown.getSelection();
//        PlayerData pd = new PlayerData(name, isHuman, houseData);
//        if(createdPlayers.size() <= currentEditingIndex)
//            createdPlayers.add(pd);
//        else
//            createdPlayers.set(currentEditingIndex, pd);
//        availableHouses.remove(houseData);
//    }
//    
//    public void addPlayer(){
//        if(nameField != null && createdPlayers.size() < 6){
//            updatePlayerImage();
//            saveCurrentPlayerData();
//            addBlankPlayerData();
//            
//            resetDisplay();
//            playButton.enable();
//            playerIcons[currentEditingIndex].show();
//            updatePlayerImage();
//        }
//        if(createdPlayers.size() == 6){
//            addButton.disable();
//        }
//    }
//    
//    public void excludePlayer(){
//        PlayerData removedPlayer = createdPlayers.remove(currentEditingIndex);
//        if(currentEditingIndex >= createdPlayers.size())
//            currentEditingIndex--;
//        if(currentEditingIndex < 0){
//            availableHouses.remove(removedPlayer.house);
//            addBlankPlayerData();
//            playButton.disable();
//        }
//        editPlayer(currentEditingIndex + "", false);
//        addButton.enable();
//        resetDisplay();
//    }
//    
//    public void playButtonPressed() {
//        main.Main.getInstance().enterState(main.WarScenes.GAME_SCENE);
//    }
//    
//    @NiftyEventSubscriber(id="dropDown2")
//    public void onDropdownSelectionChanged(String id, DropDownSelectionChangedEvent evt){
//        updatePlayerImage();
//    }
//    
//    @NiftyEventSubscriber(id="nameTextField")
//    public void onPlayerNameChanged(String id, TextFieldChangedEvent evt){
//        nameField = evt.getTextFieldControl();
//        
//        
////        Element nameText = playerNames[currentEditingIndex];
////        TextRenderer tr = nameText.getRenderer(TextRenderer.class);
////        tr.setText(evt.getText());
//        l.setText(evt.getText());
//    }
//    
//    public void closePopup(){
//        n.gotoScreen("startingScreen");
//    }
//    
//    
//    //Auxiliary classes
//    private static class PlayerData{
//        String name;
//        HouseData house;
//        boolean isHuman;
//        public PlayerData(String name, boolean isHuman, HouseData house){
//            this.name = name;
//            this.isHuman = isHuman;
//            this.house = house;
//        }
//    }
//    
//    private static class HouseData{
//        String name;
//        String imgPath;
//        public HouseData(String houseName, String imageFile){
//            name = houseName;
//            imgPath = imageFile;
//        }
//        
//        public NiftyImage createImage(Nifty n){
//            return n.createImage(imgPath, false);
//        }
//        
//        @Override
//        public String toString(){
//            return name;
//        }
//    }
//}