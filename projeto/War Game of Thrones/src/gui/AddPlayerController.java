package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import de.lessvoid.nifty.controls.Label;
import main.Main;
import static main.Constants.*;
import models.*;
import util.PopupManager;

public class AddPlayerController implements ScreenController{

    private TextField nameField;
    private Nifty n;
    private Screen s;
    private DropDown<HouseData> housesDropdown;
    private DropDown<AIDifficulty> difficultyDropdown;
    private List<HouseData> availableHouses;
    private List<PlayerData> createdPlayers;
    private RadioButton humanButton, cpuButton;
    private Button addButton, playButton;
    private Element iconsPanel, emptyNamePopup;
    private Element [] playerIcons;
    private Label [] playerNames;
    private int currentEditingIndex = -1;
    
    
    //Overriden methods
    @Override
    public void bind(Nifty nifty, Screen screen) {
        n = nifty;
        s = screen;
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
        difficultyDropdown = screen.findNiftyControl("dropDownAIDifficulty", DropDown.class);
        humanButton = screen.findNiftyControl("humanRadioBtn", RadioButton.class);
        cpuButton = screen.findNiftyControl("cpuRadioBtn", RadioButton.class);
        emptyNamePopup = nifty.createPopup("emptyNamePopup");
    }
    
    @Override
    public void onStartScreen() { 
        difficultyDropdown.clear();
        difficultyDropdown.addItem(AIDifficulty.EASY);
        difficultyDropdown.addItem(AIDifficulty.MEDIUM);
        difficultyDropdown.addItem(AIDifficulty.HARD);
        resetController();
    }

    @Override
    public void onEndScreen() {    }
    
    public void resetController(){
        addButton.enable();
        playButton.disable();
        for(int i = 1; i < playerIcons.length; i++)
            playerIcons[i].hide();
            HouseData houses[] = {
                                new HouseData("Baratheon", "resources/images/house_logos/baratheon.png", "Veado", new Color("#B6B631")),  
                                new HouseData("Free Folk", "resources/images/house_logos/free_folk.png", "Urso polar", new Color("#6DAC52")),  
                                new HouseData("Greyjoy", "resources/images/house_logos/greyjoy.png", "Lula", new Color("#5B5B5B")),  
                                new HouseData("Lannister", "resources/images/house_logos/lannister.png", "Leão", new Color("#AB2B2B")),
                                new HouseData("Stark", "resources/images/house_logos/stark.png", "Lobo", new Color("#8E8E8E")),
                                new HouseData("Targaryen", "resources/images/house_logos/targaryen.png", "Dragão", new Color("#C96826")),  
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
        HouseData houseData = housesDropdown.getSelection();
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
        
        if(currentPlayer.isHuman){
            humanButton.select();
            difficultyDropdown.getElement().setVisible(false);
        } else {
            cpuButton.select();
            difficultyDropdown.selectItem(currentPlayer.difficulty);
            difficultyDropdown.getElement().setVisible(true);
        }
        
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
        difficultyDropdown.selectItem(currentPlayer.difficulty);
    }
    
    //Elements interaction callbacks
    private void editPlayer(String playerIndex, boolean saveCurrent){
        if(nameFieldInvalid() && !ACCEPT_INVALID_NAMES)
            showEmptyNameWarning();
        else {
            if(saveCurrent)
                saveCurrentPlayerData();
            currentEditingIndex = Integer.parseInt(playerIndex);
            PlayerData editedPlayer = createdPlayers.get(currentEditingIndex);
            availableHouses.add(0, editedPlayer.house);
            resetDisplay();
        }
    }
    
    public void editPlayer(String playerIndex){
        editPlayer(playerIndex, true);
    }
    
    private void addBlankPlayerData(){
        PlayerData dummy = new PlayerData("", true, availableHouses.get(0), AIDifficulty.EASY);
        createdPlayers.add(dummy);
        currentEditingIndex = createdPlayers.size() - 1;
    }
    
    private void saveCurrentPlayerData(){
        String name = nameField.getDisplayedText();
        boolean isHuman = humanButton.isActivated();
        
        HouseData houseData = housesDropdown.getSelection();
        AIDifficulty diff = difficultyDropdown.getSelection();
        
        PlayerData pd = new PlayerData(name, isHuman, houseData, diff);
        if(createdPlayers.size() <= currentEditingIndex)
            createdPlayers.add(pd);
        else
            createdPlayers.set(currentEditingIndex, pd);
        availableHouses.remove(houseData);
    }
    
    public void addPlayer(){
        int playersCount = createdPlayers.size();
        if(nameField != null && playersCount < 6){
            if(nameFieldInvalid() && !ACCEPT_INVALID_NAMES){
                showEmptyNameWarning();
            } else {
                updatePlayerImage();
                saveCurrentPlayerData();
                addBlankPlayerData();

                resetDisplay();
                System.out.println("players count " + playersCount);
                if(ACCEPT_LESS_THAN_3_PLAYERS || createdPlayers.size() >= 3)
                    playButton.enable();
                playerIcons[currentEditingIndex].show();
                updatePlayerImage();
            }
        }
        if(createdPlayers.size() == 6){
            addButton.disable();
            addButton.setText("Máximo de jogadores");
        }
    }
    
    private boolean nameFieldInvalid(){
        String name = nameField.getDisplayedText();
        int length = name.length();
        return length < 3 || length > 10;
    }
    
    private void showEmptyNameWarning(){
        PopupManager.showPopup(n, s, emptyNamePopup);
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
            int playersCount = createdPlayers.size();
            if(createdPlayers.size() == 1 || (!ACCEPT_LESS_THAN_3_PLAYERS && playersCount < 3))
                playButton.disable();
            resetDisplay();
            
        }
    }
    
    private House createBackEndHouse(HouseData d){
        return new House(d.name, d.color, d.symbol, d.imgPath);
    }
    
    private Player createBackEndPlayer(PlayerData pd, House h){
        System.out.println("is human? " + pd.isHuman);
        if(pd.isHuman)
            return new HumanPlayer(pd.name, h);
        AIPlayer ai = new AIPlayer(pd.name, h);
        int difficulty = pd.difficulty.getID();
        //easy = 1, medium = 2, hard = 3
        Difficulty d;
        System.out.println("create backend player");
        switch(difficulty){
            case 0:
                ai.setDifficulty(new EasyAI());
                System.out.println("creating easy IA");
                break;
            case 1:
                ai.setDifficulty(new MediumAI());
                System.out.println("creating medium IA");
                break;
            case 2:
                ai.setDifficulty(new ExtremeAI());
                System.out.println("creating hard IA");
                break;
        }
        return ai;
    }
    
    public void playButtonPressed() {
        try{
            if(nameFieldInvalid() && !ACCEPT_INVALID_NAMES)
                showEmptyNameWarning();
            else{
                saveCurrentPlayerData();
                Board.reset();
                ContextMenuController.reset();
                Board b = Board.getInstance();
                int playersCount = createdPlayers.size();
                for(int i = 0; i < playersCount; i++){
                    int randomPos;
                    if(RANDOM_PLAYER_ORDER)
                        randomPos = (int)(Math.random()*createdPlayers.size());
                    else
                        randomPos = 0;
                    PlayerData playerData = createdPlayers.remove(randomPos);
                    b.isPlayerCountValid();
                    House h = createBackEndHouse(playerData.house);
                    Player p = createBackEndPlayer(playerData, h);
                    int type = playerData.isHuman ? Board.HUMAN_PLAYER : Board.AI_PLAYER;

                    b.addHouse(h);
                    b.addPlayer(p, i, type);
                }
                
                b.createMissions();
                b.raffleMission();
                b.distributeInitialTerritories();
                Main.getInstance().enterState(main.WarScenes.GAME_SCENE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @NiftyEventSubscriber(id="dropDown2")
    public void onDropdownSelectionChanged(String id, DropDownSelectionChangedEvent evt){
        updatePlayerImage();
    }
    
    @NiftyEventSubscriber(id="RadioGroup-1")
    public void onDropdownSelectionChanged(String id, RadioButtonGroupStateChangedEvent evt){
        boolean cpuChecked = cpuButton.isActivated();
        Element e = difficultyDropdown.getElement();
        e.setVisible(cpuChecked);
    }
    
    @NiftyEventSubscriber(id="nameTextField")
    public void onPlayerNameChanged(String id, TextFieldChangedEvent evt){
        nameField = evt.getTextFieldControl();
        
        playerNames[currentEditingIndex].setText(evt.getText());
    }
    
    public void closePopup(){
        n.gotoScreen("startingScreen");
    }
    
    public void dismissEmptyNamePopup(){
        PopupManager.closePopup(n, emptyNamePopup);
    }
    
    //Auxiliary classes
    private static class PlayerData{
        String name;
        HouseData house;
        boolean isHuman;
        AIDifficulty difficulty;
        
        public PlayerData(String name, boolean isHuman, HouseData house, AIDifficulty diff){
            this.name = name;
            this.isHuman = isHuman;
            this.house = house;
            this.difficulty = diff;
        }
    }
    
    private static class HouseData{
        String name;
        String imgPath;
        String symbol;
        Color color;
        
        private HouseData(String houseName, String imageFile, String symbol, Color c){
            name = houseName;
            imgPath = imageFile;
            this.symbol = symbol;
            color = c;
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