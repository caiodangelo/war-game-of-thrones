package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.Label;
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

    private Nifty n;
    private Screen s;
    private InGameGUIController parent;
    
    private Element [] cardsImages, chkBoxElements;
    private CheckBox [] checks;
    
    private Element cardsPopup;
    private Label obligateTradeText;
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
        obligateTradeText = cardsPopup.findNiftyControl("obligateTradeText", Label.class);
    }
    
    private void showObligateText(){
        showMessage("Você já tem 5 cartas e deverá fazer uma troca.");
    }
    
    private void hideObligateText(){
        obligateTradeText.getElement().setVisible(false);
    }
    
    public void showPopup(){
        verifyCheckedBoxes();
        updateContents();
        PopupManager.showPopup(n, s, cardsPopup);
    }
    
    public void dissmissPopup(){
        resetPopup();
        PopupManager.closePopup(n, cardsPopup);
    }
    
    private void resetPopup(){
        for(CheckBox c : checks)
            c.uncheck();
    }
    
    public void updateContents(){
        //update current player's cards
        Board b = Board.getInstance();
        List<CardTerritory> playerCards = b.getCurrentPlayer().getCards();
        
        int cardsCount = playerCards.size();
        
        
        boolean mustTrade = cardsCount == 5;
        if(mustTrade)
            showObligateText();
        else
            hideObligateText();
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
    
    private void showCantTradeMessage(){
        showMessage("Você só pode fazer trocas no início da rodada.");
    }
    
    private void verifyCheckedBoxes(){
        int checkedCount = 0;
        for(CheckBox c : checks){
            if(c.isChecked())
                checkedCount++;
        }
        
        List<CardTerritory> selected = getSelectedCards();
        
        hideObligateText();
        
        boolean playerIsDistributing = Board.getInstance().getCurrentPlayer().getPendingArmies() > 0;
        if(checkedCount == 3 && 
                (RepositoryCardsTerritory.checkCardsTradeable(selected))){
            if(playerIsDistributing)
                tradeButton.setEnabled(true);
            else{
                tradeButton.setEnabled(false);
                showCantTradeMessage();
            }
        }
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
        
        List<CardTerritory> cardsToTrade = getSelectedCards();
        int oldArmies = p.getPendingArmies();
        RepositoryCardsTerritory repo = RepositoryCardsTerritory.getInstance();
        repo.swapCards(cardsToTrade, p);
        int newArmies = p.getPendingArmies() - oldArmies;
//        dissmissPopup();
        parent.showPendingArmiesMsg();
        parent.updatePlayersData();
        
        resetPopup();
        this.updateContents();
        this.verifyCheckedBoxes();
        showMessage("Você conseguiu " + newArmies + " unidades!");
    }
    
    private void showMessage(String msg){
        obligateTradeText.setText(msg);
        obligateTradeText.getElement().setVisible(true);
    }
}