package gui;

import communication.BEPImpl;
import communication.BackEndPlayer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class InGameGUIController implements ScreenController{

    private StatusPanelControl [] statusPanels;
    private Label playerStatusName, playerStatusCards, playerStatusUnits, playerStatusTerritories;
    private Screen screen;
    private Nifty n;
    public static BackEndPlayer [] players;
    private Element objectivePopup;
    
    public InGameGUIController(){
        //DEBUG ONLY
        if(players == null){
            players = new BackEndPlayer[6];
            for(int i = 0; i < players.length; i++)
                players[i] = new BEPImpl();
        }
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        n = nifty;
        playerStatusName = screen.findNiftyControl("playerStatusName", Label.class);
        playerStatusCards = screen.findNiftyControl("playerStatusCards", Label.class);
        playerStatusUnits = screen.findNiftyControl("playerStatusUnits", Label.class);
        playerStatusTerritories = screen.findNiftyControl("playerStatusTerritories", Label.class);
        objectivePopup = n.createPopup("objectivePopup");
    }
    
    @Override
    public void onStartScreen() {  
        retrieveStatusPanels(screen);
        updatePlayersData();
    }
    
    @Override
    public void onEndScreen() {    }
    
    private void retrieveStatusPanels(Screen s){
        int playersCount = players.length;
        statusPanels = new StatusPanelControlImpl[playersCount];
        for(int i = 0; i < 6; i++){
            StatusPanelControl spc = s.findNiftyControl("player" + i + "Status", StatusPanelControl.class);
            if(i >= playersCount)
                spc.getElement().setVisible(false);
            else{
                statusPanels[i] = spc;
            }
        }
    }
    
    private void updatePlayersData(){
        for(int i = 0; i < players.length; i++){
            StatusPanelControl spc = statusPanels[i];
            BackEndPlayer current = players[i];
            spc.updateData(current.getName(), current.getCardsCount(), current.getUnitsCount(), current.getTerritoriesCount());
        }
        updateCurrentPlayersData();
    }
    
    //TODO: check who really is the next player
    private BackEndPlayer getCurrentPlayer(){
        return players[0];
    }
    
    private void updateCurrentPlayersData(){
        BackEndPlayer currPlayer = getCurrentPlayer();
        playerStatusName.setText(currPlayer.getName());
        playerStatusCards.setText(currPlayer.getCardsCount() + " Cartas");
        playerStatusUnits.setText(currPlayer.getUnitsCount() + " Exércitos");
        playerStatusTerritories.setText(currPlayer.getTerritoriesCount() + " Territórios");
    }
    
    public void showPlayerObjective(){
        n.showPopup(screen, objectivePopup.getId(), null);
        Label description = objectivePopup.findNiftyControl("objectiveDescLabel", Label.class);
        String objectiveStr = getCurrentPlayer().getMission().getDescription();
        description.setText(objectiveStr);
    }
    
    public void dismissPlayerObjective(){
        n.closePopup(objectivePopup.getId());
    }
    
    //Top Menu event handling
    public void theGameMenuClicked(){
        System.out.println("THE GAME");
    }
    public void optionsMenuClicked(){
        System.out.println("OPTIONS");
    }
    public void helpMenuClicked(){
        System.out.println("HELP");
    }
    public void exitMenuClicked(){
        System.out.println("EXIT");
    }
}
