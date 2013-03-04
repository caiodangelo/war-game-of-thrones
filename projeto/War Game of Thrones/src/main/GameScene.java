package main;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.tools.Color;
import gui.InGameGUIController;
import java.util.Date;
import models.AIPlayer;
import models.Board;
import models.Player;
import models.StatisticGameManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.Scene;

public class GameScene extends Scene{
    
    private static int mouseWheel;
    private static GameScene instance;
    
    private PlayerTurnMessage turnMsg;
    private Board b;
    private InGameGUIController ctrl;
    private TurnHelper helper;
    private TerritoryName terrName;
    private Map map;
    private MapMover mapMover;
    
    public GameScene(){
        super();
        instance = this;
    }
    
    public static GameScene getInstance(){
        return instance;
    }
    
    
    public static int getMouseWheel(){
        return mouseWheel;
    }
    
    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("inGameScreen");
    }
    
    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException { 
        super.enterState(container, game);
        StatisticGameManager.getInstance().setInitTime(new Date());
        map = new Map();
        addEntity(map);
        //Left button was pressed to play but this record must be erased.
        container.getInput().clearMousePressedRecord();
        AudioManager am = AudioManager.getInstance();
        am.stopMusic(AudioManager.OPENING);
        DiceManager dm = DiceManager.getInstance();
        dm.setGameScene(this);
        
        b = Board.getInstance();
        turnMsg = new PlayerTurnMessage();
        addEntity(turnMsg);
        ctrl = InGameGUIController.getInstance();
        helper = new TurnHelper(this, ctrl);
        
        terrName = new TerritoryName();
        addEntity(terrName);
        AudioManager.getInstance().playMusic(AudioManager.GAME_RUNNING);
        
        addEntity(new RegionNames(Map.armyPositions, map));
        if(!b.getCurrentPlayer().isAIPlayer())
            ctrl.showInfoTerritories();
        else
            ctrl.startPlayerInitialDistribution();
        
        mapMover = new MapMover(map);
        addEntity(mapMover);
    }
    
    public Map getMap(){
        return map;
    }
    
    public void setHighlightedTerritory(Territory t){
        terrName.setHighlightedTerritory(t);
    }
    
    public void showPlayerTurnMsg(){
        b = Board.getInstance();
        Player p = b.getCurrentPlayer();
        Color c = p.getHouse().getColor();
        
        turnMsg.activate(p, c);
    }

    
    
    @Override
    public void mouseWheelMoved(int newValue) {
        super.mouseWheelMoved(newValue);
        mouseWheel = newValue;
    }

    @Override
    protected void updateGame(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.updateGame(container, game, delta);
        mouseWheel = 0;
    }

    @Override
    public int getID() {
        return WarScenes.GAME_SCENE.ordinal();
    }

    void handleTerritoryClick(Territory territory) {
        Player curr = b.getCurrentPlayer();
        int pendingArmies = curr.getPendingArmies();
        if (!b.isOnInitialSetup() && pendingArmies == 0) {
            InGameGUIController.handleTerritoryClick(territory);
        }
        else {
            ctrl.updatePlayersData();
            if (territory.getBackEndTerritory().getOwner() == curr){
                models.BackEndTerritory t = territory.getBackEndTerritory();
                t.increaseArmies(1);
                t.resetMovedArmies();
                curr.removePendingArmies(1);
                pendingArmies--;
                if (t.getNumArmies() < 3 && b.hasGameEnded()) //checking if 17 territories with 2 armies mission is completed
                    startGameEndingAnimation();
            } 
            if (pendingArmies == 0) {
                if (b.isOnInitialSetup()) {
                    ctrl.setRavenMessage(curr.getName()+" distribuiu seus exércitos!");
                    helper.changeTurn();
                }
                else
                    ctrl.setRavenMessage(curr.getName()+" está jogando!");
            }
            else {
                ctrl.setRavenMessage("\\#333333ff#"+curr.getName()+" ainda possui \\#CC0000#"+pendingArmies+"\\#333333ff# exército(s) para distribuir.");
            }
        }
    }
    
    public void startGameEndingAnimation() {
        ctrl.openEmptyPopup();
        GameEndingAnimation a = new GameEndingAnimation();
        addEntity(a);
        a.activate(b.getWinner());
        FireworksManager fm = new FireworksManager();
        addEntity(fm);
    }
    
    protected void startAIDistributionAnim(AIPlayer p, IAHelper helper){
        IAArmyDistributor dist = new IAArmyDistributor(this, p, helper, map, mapMover);
        addEntity(dist);
        dist.start();
    }
}
