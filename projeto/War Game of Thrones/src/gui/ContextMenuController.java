package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyMethodInvoker;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import main.ArmyRenderComponent;
import main.DiceManager;
import main.Territory;
import models.BackEndTerritory;
import models.Battle;
import models.Board;
import models.Player;
import util.PopupManager;

public class ContextMenuController {

    private DropDown<UnitCount> selectUnitsDropdown;
    private DropDown<Integer> atkDropDown, defDropDown;
    private Element contextMenu, rearrangeConfirmationPopup, rearrangePopup, attackPopup;
    
    private Nifty n;
    private InGameGUIController parent;
    
    private static final byte MENU_ATTACK = 0, MENU_DISTRIBUTE = 1, MENU_CANCEL = 2;
    
    private Territory originTerritory, destTerritory, currentTemp;
    
    private boolean onAtkSequence;
    private boolean distributing;
    private boolean mayShowRearrangeConfirmation;
    
    public ContextMenuController(Nifty n, InGameGUIController parent){
        this.n = n;
        this.parent = parent;
        
        rearrangeConfirmationPopup = n.createPopup("rearrangeConfirmationPopup");
        rearrangePopup = n.createPopup("rearrangePopup");
        selectUnitsDropdown = rearrangePopup.findNiftyControl("dropDownSelectArmies", DropDown.class);
        
        attackPopup = n.createPopup("attackPopup");
        atkDropDown = attackPopup.findNiftyControl("atkDropDown", DropDown.class);
        defDropDown = attackPopup.findNiftyControl("defDropDown", DropDown.class);
        
        //create context menu
        contextMenu = n.createPopup("niftyPopupMenu");
        Menu<Byte> popupMenu = contextMenu.findNiftyControl("#menu", Menu.class);
        popupMenu.setWidth(new SizeValue("15%"));
        popupMenu.addMenuItem("Atacar daqui", "resources/images/icons/atacar.png", MENU_ATTACK);
        popupMenu.addMenuItem("Distribuir exércitos", "resources/images/icons/distribuir.png", MENU_DISTRIBUTE);
        popupMenu.addMenuItem("Cancelar", MENU_CANCEL);
        popupMenu.setId("menuItemid");
        contextMenu.getElementInteraction().getPrimary().setOnClickMethod(new NiftyMethodInvoker(n, "closePopupMenu()", this));
        contextMenu.getElementInteraction().getSecondary().setOnClickMethod(new NiftyMethodInvoker(n, "closePopupMenu()", this));
        contextMenu.getElementInteraction().getTertiary().setOnClickMethod(new NiftyMethodInvoker(n, "closePopupMenu()", this));
        mayShowRearrangeConfirmation = true;
    }
    
    protected void handleTerritoryClick(Screen s, Territory t){
        Player curr = Board.getInstance().getCurrentPlayer();
        currentTemp = t;
        boolean ownedTerritory = t.getBackEndTerritory().getOwner() == curr;
        if(originTerritory == null){
            if (ownedTerritory)
                PopupManager.showPopup(n, s, contextMenu);
        } else {
            destTerritory = t;
            boolean areNeighbors = t.getBackEndTerritory().isNeighbour(originTerritory.getBackEndTerritory());
            parent.setInfoLabelText(null);
            if(onAtkSequence) {
                if (!ownedTerritory && areNeighbors)
                    showAttackPopup(s);
                else {
                    if (ownedTerritory)
                        parent.showAlert("Você não pode atacar um território que já possui!");
                    else
                        parent.showAlert("Só é possível atacar territórios vizinhos!");
                    originTerritory = null;
                    destTerritory = null;
                }
            }
            else {
                if (ownedTerritory && areNeighbors)
                    showRearrangePopup(s);
                else {
                    if (!ownedTerritory)
                        parent.showAlert("Você não pode levar exércitos para um território que não possui!");
                    else
                        parent.showAlert("Só é possível distribuir para territórios vizinhos!");
                    originTerritory = null;
                    destTerritory = null;
                }
            }  
        }
    }
    
    //territory context menu event handling
    private void showRearrangePopup(Screen screen){
        selectUnitsDropdown.clear();
        Player owner = originTerritory.getBackEndTerritory().getOwner();
        BackEndTerritory origin = originTerritory.getBackEndTerritory();
        int unitsCount = origin.getNumArmiesCanMoveThisRound();
        for(int i = 1; i <= unitsCount; i++)
            selectUnitsDropdown.addItem(new UnitCount(i));
        PopupManager.showPopup(n, screen, rearrangePopup);
        parent.setRavenMessage(owner.getName()+" está distribuindo seus exércitos.");
    }
    
    private void showAttackPopup(Screen screen){
        PopupManager.showPopup(n, screen, attackPopup);
        atkDropDown.clear();
        defDropDown.clear();
        
        //set player names and colors
        Label atkPlayerName = attackPopup.findNiftyControl("atkPlayerName", Label.class);
        Label defPlayerName = attackPopup.findNiftyControl("defPlayerName", Label.class);
        BackEndTerritory backAtkTer = originTerritory.getBackEndTerritory(), 
                backDefTer = destTerritory.getBackEndTerritory();
        Player attacker = backAtkTer.getOwner();
        Player defender = backDefTer.getOwner();
        atkPlayerName.setColor(attacker.getHouse().getColor());
        atkPlayerName.setText(attacker.getName());
        defPlayerName.setColor(defender.getHouse().getColor());
        defPlayerName.setText(defender.getName());
        
        int maxAtkUnits = Math.min(3, backAtkTer.getNumArmies() - 1);
        int maxDefUnits = Math.min(3, backDefTer.getNumArmies());
        for(int i = 1; i <= maxAtkUnits; i++)
            atkDropDown.addItem(i);
        for(int i = 1; i <= maxDefUnits; i++)
            defDropDown.addItem(i);
        attackPopup.findElementByName("confirmAtkArmiesBtn").enable();
        atkDropDown.enable();
        attackPopup.findElementByName("confirmDefArmiesBtn").disable();
        defDropDown.disable();
    }
    
    public void confirmAtkUnits(){
        attackPopup.findElementByName("confirmAtkArmiesBtn").disable();
        atkDropDown.disable();
        attackPopup.findElementByName("confirmDefArmiesBtn").enable();
        defDropDown.enable();
    }
    
    public void confirmDefUnits(){
        PopupManager.closePopup(n, attackPopup);
        int atkUnits = atkDropDown.getSelection();
        int defUnits = defDropDown.getSelection();
        Battle battle = new Battle(originTerritory.getBackEndTerritory(), destTerritory.getBackEndTerritory(), atkUnits, defUnits);
        battle.attack();
        DiceManager dm = DiceManager.getInstance();
        dm.setBattle(battle);
        parent.setRavenMessage(battle.getAttacker().getOwner().getName()+" está atacando "+battle.getDefender().getOwner().getName()+"!");
        dm.showDices(atkUnits, defUnits);
        dm.setAttackingTerritory(originTerritory);
        dm.setDefendingTerritory(destTerritory);
        ArmyRenderComponent comp = (ArmyRenderComponent) originTerritory.getArmy().getComponent("army-renderer");
        comp.setOrigin(originTerritory);
        comp.setDestiny(destTerritory);
        comp.setMovingQuantity(atkUnits);
        originTerritory = destTerritory = null;
    }
    
    private void showRearrangeInfo(){
        parent.setInfoLabelText("Selecione o território que irá receber exércitos.");
    }
    
    private void showAttackInfo(){
        parent.setInfoLabelText("Selecione o território inimigo que deseja atacar.");
    }
    
    protected void dismissRearrangePopup(){
        PopupManager.closePopup(n, rearrangePopup);
        originTerritory = destTerritory = null;
    }
    
    protected void MenuItemClicked(final String id, final MenuItemActivatedEvent event, final Screen s) {
        byte option = (Byte) event.getItem();
        int availableUnits = currentTemp.getBackEndTerritory().getNumArmies();
        if(option == MENU_ATTACK) {
            if (distributing) {
                parent.showAlert("Você escolheu distribuir seus exércitos! Poderá atacar apenas na próxima rodada!");
            }
            else if(availableUnits > 1) {
                onAtkSequence = true;
                originTerritory = currentTemp;
                showAttackInfo();
            }
            else
                parent.showAlert("Você não possui exércitos suficientes para atacar!");
        }
        else if(option == MENU_DISTRIBUTE){
            if (currentTemp.getBackEndTerritory().getNumArmiesCanMoveThisRound() > 0) {
                onAtkSequence = false;
                originTerritory = currentTemp;
                if (!distributing && mayShowRearrangeConfirmation)
                    PopupManager.showPopup(n, s, rearrangeConfirmationPopup);
                else
                    showRearrangeInfo();
            } else
                parent.showAlert("Não há mais exércitos que podem ser movidos deste território!");
        }
        PopupManager.closePopup(n, contextMenu);
    }
    
    public void rearrangeConfirmed() {
        distributing = true;
        showRearrangeInfo();
        PopupManager.closePopup(n, rearrangeConfirmationPopup);
    }

    public void rearrangeOK() {
        int armiesToMove = selectUnitsDropdown.getSelection().getCount();
        originTerritory.getBackEndTerritory().decreaseArmies(armiesToMove);
        originTerritory.getBackEndTerritory().setMovedArmies(armiesToMove);
        ArmyRenderComponent armyRenderer = (ArmyRenderComponent) originTerritory.getArmy().getComponent("army-renderer");
        armyRenderer.setOrigin(originTerritory);
        armyRenderer.setDestiny(destTerritory);
        armyRenderer.setMovingQuantity(armiesToMove);
        armyRenderer.startDistribution();
        parent.setRavenMessage(Board.getInstance().getCurrentPlayer().getName()+" moveu "+armiesToMove+" territórios.");
        dismissRearrangePopup();
    }
    
    public void dismissRearrangeConfirmation() {
        originTerritory = null;
        PopupManager.closePopup(n, rearrangeConfirmationPopup);
    }
    
    protected void cancelAttackPopup(){
        PopupManager.closePopup(n, attackPopup);
        originTerritory = destTerritory = null;
    }
    
    public void closePopupMenu() {
        PopupManager.closePopup(n, contextMenu);
    }
    
    public void setDistributing(boolean d) {
        distributing = d;
    }
    
    public void mayShowRearrangeConfirmationAgain(boolean show) {
        mayShowRearrangeConfirmation = show;
    }
    
    private static class UnitCount{
        private int count;
        
        public UnitCount(int count){
            this.count = count;
        }
        
        public int getCount(){
            return count;
        }
        
        @Override
        public String toString(){
            String pluralSufix = (count > 1 ? "s" : "");
            return count + " unidade" + pluralSufix;
        }
    }
    
}
