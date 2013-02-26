package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import java.util.HashMap;
import java.util.List;
import main.AudioManager;
import main.Territory;
import main.TurnHelper;
import main.WarScenes;
import models.BackEndTerritory;
import models.Board;
import models.House;
import models.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import util.PopupManager;

public class InGameGUIController implements ScreenController{

    private StatusPanelControl [] statusPanels;
    private Label playerStatusName, playerStatusCards, playerStatusUnits, playerStatusTerritories, infoTerritories, 
            ravenMessage, alert, territoryName;
    private Screen s;
    private Nifty n;
    private Board b;
    public static Player [] players;
    
    private Element objectivePopup, exitConfirmPopup, tablesPopup, objectiveLabel, viewCardsLabel, 
            optionsPopup, helpPopup, cardsPopup, infoPanel, nextTurnConfirmPopup, tablesIcon, infoTerritoriesPopup,
            alertPopup, territoryNamePopup;
    private boolean mouseOverObjective = false;
    
    private ContextMenuController ctxMenuCtrl;
    private static InGameGUIController instance;
    private HashMap<Integer, String> turnsOrder;
    private HashMap<String, String> regionsColors;
    
    public InGameGUIController(){
            turnsOrder = new HashMap();
            turnsOrder.put(0, "primeiro");
            turnsOrder.put(1, "segundo");
            turnsOrder.put(2, "terceiro");
            turnsOrder.put(3, "quarto");
            turnsOrder.put(4, "quinto");
            turnsOrder.put(5, "sexto");
            regionsColors = new HashMap();
            regionsColors.put("Além da Muralha", "\\#FFFFFF#");
            regionsColors.put("Cidades Livres", "\\#660066#");
            regionsColors.put("O Norte", "\\#66CCFF#");
            regionsColors.put("O Sul", "\\#66CC00#");
            regionsColors.put("Tridente", "\\#CCCC00#");
            regionsColors.put("O Mar Dothraki", "\\#FF6600#");
            instance = this;
    }
    
    public static InGameGUIController getInstance() {
        if (instance == null)
            instance = new InGameGUIController();
        return instance;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.s = screen;
        n = nifty;
        playerStatusName = screen.findNiftyControl("playerStatusName", Label.class);
        playerStatusCards = screen.findNiftyControl("playerStatusCards", Label.class);
        playerStatusUnits = screen.findNiftyControl("playerStatusUnits", Label.class);
        playerStatusTerritories = screen.findNiftyControl("playerStatusTerritories", Label.class);
        objectivePopup = n.createPopup("objectivePopup");
        exitConfirmPopup = n.createPopup("quitConfirmationPopup");
        tablesPopup = n.createPopup("tablesPopup");
        objectiveLabel = screen.findElementByName("seeObjectiveButton");
        viewCardsLabel = screen.findElementByName("seeCardsButton");
        tablesIcon = screen.findElementByName("tablesIcon");
        optionsPopup = n.createPopup("optionsPopup");
        helpPopup = n.createPopup("helpPopup");
        cardsPopup = n.createPopup("cardsPopup");
        ctxMenuCtrl = new ContextMenuController(n, this);
        infoTerritoriesPopup = n.createPopup("infoTerritoriesPopup");
        infoTerritories = infoTerritoriesPopup.findNiftyControl("infoTerritories", Label.class);
        alertPopup = n.createPopup("alertPopup");
        alert = alertPopup.findNiftyControl("alert", Label.class);
        
        ravenMessage = screen.findNiftyControl("ravenMessage", Label.class);
        infoPanel = screen.findElementByName("infoPanel");
        nextTurnConfirmPopup = n.createPopup("nextTurnConfirmationPopup");
    }
    
    public void setRavenMessage(String msg){
        ravenMessage.setText(msg);
    }
    
    public void showAlert(String text) {
        alert.setText(text);
        PopupManager.showPopup(n, s, alertPopup);
    }
    
    public void showTerritoryName(Territory t) {
//        territoryName.setText(t.getBackEndTerritory().getName());
//        n.showPopup(s, null, territoryNamePopup);
    }
    
    @Override
    public void onStartScreen() {  
        b = Board.getInstance();
        List<Player> playersList = Board.getInstance().getPlayers();
        players = playersList.toArray(new Player[0]);
        
        retrieveStatusPanels(s);
        updatePlayersData();
        
        Label musicVolumeValue = optionsPopup.findNiftyControl("musicVolumeValue", Label.class);
        musicVolumeValue.setText(((int) (100 * AudioManager.getInstance().getMusicVolume()))+"");
        Label soundVolumeValue = optionsPopup.findNiftyControl("soundVolumeValue", Label.class);
        soundVolumeValue.setText(((int) (100 * AudioManager.getInstance().getSoundVolume()))+"");
        CheckBox musicMute = optionsPopup.findNiftyControl("musicMute", CheckBox.class);
        CheckBox soundMute = optionsPopup.findNiftyControl("soundMute", CheckBox.class);
        if (AudioManager.getInstance().musicIsMuted())
            musicMute.check();
        if (AudioManager.getInstance().soundIsMuted())
            soundMute.check();
        Slider musicSlider = optionsPopup.findNiftyControl("musicSlider", Slider.class);
        Slider soundSlider = optionsPopup.findNiftyControl("soundSlider", Slider.class);
        optionsPopup.findNiftyControl("sliderCPUdifficulty", Slider.class).disable();
        musicSlider.setValue(1 - AudioManager.getInstance().getMusicVolume());
        soundSlider.setValue(1 - AudioManager.getInstance().getSoundVolume());
    }
    
    @Override
    public void onEndScreen() {    }
    
    public static void handleTerritoryClick(Territory t){
        instance.ctxMenuCtrl.handleTerritoryClick(instance.s, t);
    }
    
    private void retrieveStatusPanels(Screen s){
        int playersCount = players.length;
        statusPanels = new StatusPanelControlImpl[playersCount];
        for(int i = 0; i < 6; i++){
            StatusPanelControl spc = s.findNiftyControl("player" + i + "Status", StatusPanelControl.class);
            if(i >= playersCount)
                spc.getElement().setVisible(false);
            else
                statusPanels[i] = spc;
        }
    }
    
    public void updatePlayersData(){
        for(int i = 0; i < players.length; i++){
            StatusPanelControl spc = statusPanels[i];
            Player current = players[i];
            spc.updateData(current.getName(), current.numCards(), current.numArmies(), current.numTerritories());
            spc.setNameColor(players[i].getHouse().getColor());
        }
        updateCurrentPlayersData();
    }
    
    private Player getCurrentPlayer(){
        return b.getCurrentPlayer();
    }
    
    private Color getCurrentPlayerColor(){
        return getCurrentPlayer().getHouse().getColor();
    }
    
    private void updateCurrentPlayersData(){
        boolean skippedStartScreen = main.Main.JUMP_TO_GAME;
        if(!skippedStartScreen){
            Player currPlayer = getCurrentPlayer();
            Color currentPlayerColor = getCurrentPlayerColor();
            playerStatusName.setText(currPlayer.getName());
            playerStatusName.setColor(currentPlayerColor);
            StatusPanelControlImpl.setLabel(playerStatusCards, currPlayer.numCards(), "Carta");
            StatusPanelControlImpl.setLabel(playerStatusUnits, currPlayer.numArmies(), "Exército");
            StatusPanelControlImpl.setLabel(playerStatusTerritories, currPlayer.numTerritories(), "Território");
            updateCurrentHouse(currPlayer.getHouse());
        }
    }
    
    public void showPlayerObjective(){
        resetMouseCursor();
        Label description = objectivePopup.findNiftyControl("objectiveDescLabel", Label.class);
        String objectiveStr = getCurrentPlayer().getMission().getDescription();
        description.setText(objectiveStr);
        PopupManager.showPopup(n, s, objectivePopup);
    }
    
    public void showPlayerCards(){
        resetMouseCursor();
        PopupManager.showPopup(n, s, cardsPopup);
    }
    
    public void dismissPlayerObjective(){
        PopupManager.closePopup(n, objectivePopup);
    }
    
    public void dismissPlayerCards(){
        PopupManager.closePopup(n, cardsPopup);
    }
    
    public void nextPlayerTurnConfirm() {
        PopupManager.showPopup(n, s, nextTurnConfirmPopup);
    }
    
    public void nextPlayerTurn() {
        PopupManager.closePopup(n, nextTurnConfirmPopup);
        int pendingArmies = getCurrentPlayer().getPendingArmies();
        if (pendingArmies > 0)
            showAlert("Você ainda possui "+pendingArmies+" exércitos para distribuir!");
        else {
            ctxMenuCtrl.setDistributing(false);
            TurnHelper.getInstance().changeTurn();
        }
    }
    
    public void dismissNextTurnConfirmation(){
        PopupManager.closePopup(n, nextTurnConfirmPopup);
    }
    
    //Top Menu event handling
    public void theGameMenuClicked(){
        System.out.println("THE GAME");
    }
    
    public void showOptions(){
        PopupManager.showPopup(n, s, optionsPopup);
    }
    
    public void helpMenuClicked(){
        PopupManager.showPopup(n, s, helpPopup);
    }
    
    public void exitMenuClicked(){
        PopupManager.showPopup(n, s, exitConfirmPopup);
    }
    //Top Menu event end
   
    public void closeOptions(){
        PopupManager.closePopup(n, optionsPopup);
    }
    
    public void closeHelpPopup(){
        PopupManager.closePopup(n, helpPopup);
    }
    
    //Popups event handling
    
    public void exitGame(){
        PopupManager.closePopup(n, exitConfirmPopup);
        main.Main.getInstance().enterState(WarScenes.STARTING_SCENE);
//        main.Main.getInstance().getGameContainer().exit();
    }
    
    public void showTables(){
        resetMouseCursor();
        PopupManager.showPopup(n, s, tablesPopup);
    }
    
    public void dismissExitConfirmation(){
        PopupManager.closePopup(n, exitConfirmPopup);
    }
    
    public void dismissTablesPopup(){
        PopupManager.closePopup(n, tablesPopup);
    }
    
    public void dismissAlertPopup() {
        PopupManager.closePopup(n, alertPopup);
    }
    
    /**
     * Use null to hide the panel.
     */
    public void setInfoLabelText(String text){
        Label infoLabel = s.findNiftyControl("infoLabel", Label.class);
        if(text != null){
            infoLabel.setText(text);
            infoPanel.setVisible(true);
        } else
            infoPanel.setVisible(false);
    }
    
    //Popups event handling end
    
    private void resetMouseCursor(){
        GameContainer c = main.Main.getInstance().getContainer();
        c.setDefaultMouseCursor();
    }
    
    private boolean mouseOverLink(int mouseX, int mouseY){
        return objectiveLabel.isMouseInsideElement(mouseX, mouseY)
                || viewCardsLabel.isMouseInsideElement(mouseX, mouseY)
                || tablesIcon.isMouseInsideElement(mouseX, mouseY);
    }
    
    public void mouseMovedOverBottomPanel(){
        Input in = main.Main.getInstance().getContainer().getInput();
        boolean inside = mouseOverLink(in.getMouseX(), in.getMouseY());
        if(inside != mouseOverObjective){
            mouseOverObjective = inside;
            if(!inside)
                resetMouseCursor();
            else{
                GameContainer c = main.Main.getInstance().getContainer();
                try {
                    c.setMouseCursor("resources/cursors/aero_link.png", 8, 1);
                } catch (SlickException ex) {
                        System.out.println("error setting cursor");
                }
            }
        }
    }
    
    public void dismissRearrangePopup(){
        ctxMenuCtrl.dismissRearrangePopup();
    }
    
    public void rearrangeConfirmed() {
        ctxMenuCtrl.rearrangeConfirmed();
    }
    
    public void dismissRearrangeConfirmation() {
        ctxMenuCtrl.dismissRearrangeConfirmation();
    }
    
    public void rearrangePopupOK(){
        ctxMenuCtrl.rearrangeOK();
    }
    
    public void confirmAtkUnits(){
        ctxMenuCtrl.confirmAtkUnits();
    }
    
    public void confirmDefUnits(){
        ctxMenuCtrl.confirmDefUnits();
    }
    
    public void cancelAttackPopup(){
        ctxMenuCtrl.cancelAttackPopup();
    }
    
    public void showInfoTerritories() {
        String content = "\nAtenção, ";
        Board b = Board.getInstance();
        Player currPlayer = b.getCurrentPlayer();
        String turn = turnsOrder.get(b.getPlayerOrder(currPlayer));
        content += currPlayer.getName()+"! Os turnos foram sorteados e você é o "+turn+" a jogar!\n\nSeus territórios são:\n";
        String colorCode;
        for (BackEndTerritory t : currPlayer.getTerritories()) {
            colorCode = regionsColors.get(t.getRegion().getName());
            content += "\n"+colorCode+t.getName()+colorCode;
        }
        infoTerritories.setText(content);
        PopupManager.showPopup(n, s, infoTerritoriesPopup);
    }
     
    public void closeInfoTerritoriesPopup(){
        PopupManager.closePopup(n, infoTerritoriesPopup);
        Player curr = b.getCurrentPlayer();
        List<BackEndTerritory> ts = b.getCurrentPlayer().getTerritories();
        
        for(BackEndTerritory t : ts){
            t.setNumArmies(1);
        }
        setInfoLabelText("Você ainda possui "+getCurrentPlayer().getPendingArmies()+" exército(s) para distribuir.");
        updatePlayersData();
        setRavenMessage(curr.getName() + " está distribuindo os exércitos.");
    }
            
    @NiftyEventSubscriber(id = "menuItemid")
    public void MenuItemClicked(final String id, final MenuItemActivatedEvent event) {
        ctxMenuCtrl.MenuItemClicked(id, event, s);
    }
    
    @NiftyEventSubscriber(id="musicSlider")
    public void onMusicSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager.getInstance().changeMusicVolume(newVolume);
        Label musicVolumeValue = optionsPopup.findNiftyControl("musicVolumeValue", Label.class);
        musicVolumeValue.setText(((int) (newVolume*100))+"");
    }
    
    @NiftyEventSubscriber(id="soundSlider")
    public void onSoundSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager.getInstance().changeSoundVolume(newVolume);
        Label soundVolumeValue = optionsPopup.findNiftyControl("soundVolumeValue", Label.class);
        soundVolumeValue.setText(((int) (newVolume*100))+"");
    }
    
    @NiftyEventSubscriber(id="musicMute")
    public void onMusicMuteChange(final String id, final CheckBoxStateChangedEvent event) {
        AudioManager am = AudioManager.getInstance();
        if (event.isChecked())
            am.muteMusic();
        else
            am.unmuteMusic();
    }
    
    @NiftyEventSubscriber(id="soundMute")
    public void onSoundMuteChange(final String id, final CheckBoxStateChangedEvent event) {
        AudioManager am = AudioManager.getInstance();
        if (event.isChecked())
            am.muteSound();
        else
            am.unmuteSound();
    }
     
    @NiftyEventSubscriber(id="dontShowRearrangeConfirmationAgain")
    public void onDontShowRearrangeConfirmationAgainChange(final String id, final CheckBoxStateChangedEvent event) {
        ctxMenuCtrl.mayShowRearrangeConfirmationAgain(!event.isChecked());
    }
    
    private void updateCurrentHouse(House h){
        Element imgElement = s.findElementByName("currHouseImg");
        ImageRenderer r = imgElement.getRenderer(ImageRenderer.class);
        r.setImage(n.createImage(h.getImgPath(), false));
        Label houseNameLabel = s.findNiftyControl("currHouseName", Label.class);
        houseNameLabel.setText(h.getName());
    }
}
