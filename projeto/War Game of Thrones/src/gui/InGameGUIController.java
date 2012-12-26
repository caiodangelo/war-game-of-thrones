package gui;

import communication.BEPImpl;
import communication.BackEndPlayer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class InGameGUIController implements ScreenController{

    private StatusPanelControl [] statusPanels;
    private Label playerStatusName, playerStatusCards, playerStatusUnits, playerStatusTerritories;
    private Screen screen;
    public static BackEndPlayer [] players;
    
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
        playerStatusName = screen.findNiftyControl("playerStatusName", Label.class);
        playerStatusCards = screen.findNiftyControl("playerStatusCards", Label.class);
        playerStatusUnits = screen.findNiftyControl("playerStatusUnits", Label.class);
        playerStatusTerritories = screen.findNiftyControl("playerStatusTerritories", Label.class);
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
    
    private void updateCurrentPlayersData(){
        int currentPlayerIndex = 0;
        BackEndPlayer currPlayer = players[currentPlayerIndex];
        playerStatusName.setText(currPlayer.getName());
        playerStatusCards.setText(currPlayer.getCardsCount() + " Cartas");
        playerStatusUnits.setText(currPlayer.getUnitsCount() + " Exércitos");
        playerStatusTerritories.setText(currPlayer.getTerritoriesCount() + " Territórios");
    }
    
    

}
