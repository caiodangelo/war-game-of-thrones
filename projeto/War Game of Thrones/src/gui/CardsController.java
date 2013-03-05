package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import java.util.ArrayList;
import java.util.List;
import main.CardPaths;
import models.Board;
import models.CardTerritory;
import models.Player;
import models.RepositoryCardsTerritory;
import util.PopupManager;

public class CardsController {

    private static boolean DEBUGGING = false;
    
    private Nifty n;
    private Screen s;
    private InGameGUIController parent;
    
    private Element [] cardsImages, chkBoxElements;
    private CheckBox [] checks;
    
    private Element cardsPopup, obligateTradeText;
    private Button tradeButton, dissmissButton;
    
    public CardsController(Nifty n, Screen s, InGameGUIController parent){
        this.n = n;
        this.s = s;
        this.parent = parent;
        
        cardsPopup = n.createPopup("cardsPopup");
        
        //retrieve elements
        cardsImages = new Element[5];
        chkBoxElements = new Element[5];
        checks = new CheckBox[5];
        
        for(int i = 0; i < 5; i++){
            cardsImages[i] = cardsPopup.findElementByName("card" + i);
            
            checks[i] = cardsPopup.findNiftyControl("card" + i + "Checkbox", CheckBox.class);
            
            chkBoxElements[i] = cardsPopup.findElementByName("card" + i + "Checkbox");
        }   
        
        //buttons
        dissmissButton = cardsPopup.findNiftyControl("dismissCardsButton", Button.class);
        tradeButton = cardsPopup.findNiftyControl("tradeCardsButton", Button.class);
        obligateTradeText = cardsPopup.findElementByName("obligateTradeText");
    }
    
    public void showPopup(){
        verifyCheckedBoxes();
        updateContents();
        PopupManager.showPopup(n, s, cardsPopup);
    }
    
    public void dissmissPopup(){
        for(CheckBox c : checks){
            c.uncheck();
        }
        PopupManager.closePopup(n, cardsPopup);
    }
    
    public void updateContents(){
        //update current player's cards
        Board b = Board.getInstance();
        List<CardTerritory> playerCards = b.getCurrentPlayer().getCards();
        
        int cardsCount = playerCards.size();
        
        
        boolean mustTrade = cardsCount == 5;
        obligateTradeText.setVisible(mustTrade);
        dissmissButton.setEnabled(!mustTrade);
        
        for(int i = 0; i < 5; i++){
            Element card = cardsImages[i];
            if(i >= cardsCount){
                card.setVisible(false);
                chkBoxElements[i].setVisible(false);
            }
            else{
                card.setVisible(true);
                chkBoxElements[i].setVisible(true);
                ImageRenderer r = cardsImages[i].getRenderer(ImageRenderer.class);
                String path = CardPaths.getPath(playerCards.get(i));
                r.setImage(n.createImage(path, mustTrade));
            }
        }
    }
    
    public void onCheckboxClicked(int index){
        verifyCheckedBoxes();
    }
    
    public boolean playerMustSwapCards(){
        Player p = Board.getInstance().getCurrentPlayer();
        return p.getCards().size() == 5;
    }
    
    private void verifyCheckedBoxes(){
        int checkedCount = 0;
        for(CheckBox c : checks){
            if(c.isChecked())
                checkedCount++;
        }
        
        List<CardTerritory> selected = getSelectedCards();
        
        boolean playerIsDistributing = Board.getInstance().getCurrentPlayer().getPendingArmies() > 0;
        if(playerIsDistributing && checkedCount == 3 && 
                (RepositoryCardsTerritory.checkCardsTradeable(selected)))
            tradeButton.setEnabled(true);
        else
            tradeButton.setEnabled(false);
    }
    
    private List<CardTerritory> getSelectedCards(){
        Player p = Board.getInstance().getCurrentPlayer();
        List<CardTerritory> allCards = p.getCards();
        int cardsCount = allCards.size();
        

        List<CardTerritory> selectedCards = new ArrayList<CardTerritory>();
        for(int i = 0; i < 5; i++){
            if(checks[i].isChecked() && i < cardsCount){
                selectedCards.add(allCards.get(i));
            }
        }
        return selectedCards;
    }

    protected void onCardClick(int index) {
        checks[index].toggle();
    }

    protected void tradeCards() {
        Player p = Board.getInstance().getCurrentPlayer();
        List<CardTerritory> allCards = p.getCards();
        int cardsCount = allCards.size();
        
        List<CardTerritory> cardsToTrade = getSelectedCards();
        
        RepositoryCardsTerritory repo = RepositoryCardsTerritory.getInstance();
        repo.swapCards(cardsToTrade, p);
        System.out.println("player got " + p.getPendingArmies() + " new armies");
        dissmissPopup();
        parent.showPendingArmiesMsg();
        parent.updatePlayersData();
    }
}