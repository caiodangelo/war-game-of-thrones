package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyMethodInvoker;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.effects.impl.Show;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import main.ArmyRenderComponent;
import main.DiceManager;
import main.GameScene;
import main.Territory;
import models.BackEndTerritory;
import models.Battle;
import models.Board;
import models.Player;
import util.PopupManager;

public class ContextMenuController {

    private DropDown<UnitCount> selectUnitsDropdown;
    private DropDown<UnitCount> dropDownSelectVictoriousArmies;
    private DropDown<Integer> atkDropDown, defDropDown;
    private Element contextMenu, rearrangeConfirmationPopup, rearrangePopup, attackPopup, takeToConqueredTerritoryPopup;
    
    private Nifty n;
    private InGameGUIController parent;
    
    private static final byte MENU_ATTACK = 0, MENU_DISTRIBUTE = 1, MENU_CANCEL = 2;
    
    private Territory originTerritory, destTerritory, currentTemp;
    private Battle currentBattle;
    
    private boolean onAtkSequence;
    private boolean distributing;
    private static boolean mayShowRearrangeConfirmation;
    private Screen s;
    
    public ContextMenuController(Nifty n, Screen s, InGameGUIController parent){
        this.n = n;
        this.s = s;
        this.parent = parent;
        
        rearrangeConfirmationPopup = n.createPopup("rearrangeConfirmationPopup");
        rearrangePopup = n.createPopup("rearrangePopup");
        selectUnitsDropdown = rearrangePopup.findNiftyControl("dropDownSelectArmies", DropDown.class);
        
        attackPopup = n.createPopup("attackPopup");
        takeToConqueredTerritoryPopup = n.createPopup("takeToConqueredTerritoryPopup");
        dropDownSelectVictoriousArmies = takeToConqueredTerritoryPopup.findNiftyControl("dropDownSelectVictoriousArmies", DropDown.class);
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
    
    /**
     * Faz o aviso sobre distribuição de exércitos ficar visível novamente
     */
    public static void reset(){
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
                    System.out.println("reset 1");
                    resetTerritories();
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
                    System.out.println("reset 2");
                    resetTerritories();
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
        for(int i = maxAtkUnits; i >= 1; i--)
            atkDropDown.addItem(i);
        for(int i = maxDefUnits; i >= 1; i--)
            defDropDown.addItem(i);
        attackPopup.findElementByName("confirmAtkArmiesBtn").enable();
        atkDropDown.enable();
        attackPopup.findElementByName("confirmDefArmiesBtn").disable();
        defDropDown.disable();
    }
    
    public void showIAAttackPopup(Territory origin, Territory dest, int count){
        System.out.println("show IA Attack popup from " + origin + " to " + dest);
        this.originTerritory = origin;
        this.destTerritory = dest;
        Button cancelBtn = attackPopup.findNiftyControl("cancelAtkBtn", Button.class);
        cancelBtn.setEnabled(false);
        System.out.println("origin territory " + this.originTerritory);
        System.out.println("dest territory " + this.destTerritory);
        showAttackPopup(s);
        atkDropDown.selectItem(count);
        Player defender = dest.getBackEndTerritory().getOwner();
        
        if(defender.isAIPlayer()){
            confirmDefUnits();
        } else {
            confirmAtkUnits();
        }
    }
    
    public void confirmAtkUnits(){
        attackPopup.findElementByName("confirmAtkArmiesBtn").disable();
        atkDropDown.disable();
        attackPopup.findElementByName("confirmDefArmiesBtn").enable();
        defDropDown.enable();
        
        BackEndTerritory backDefTer = destTerritory.getBackEndTerritory();
        Player defender = backDefTer.getOwner();
        if(defender.isAIPlayer())
            confirmDefUnits();
    }
    
    public void confirmDefUnits(){
        try{
            System.out.println("confirm def units");
            PopupManager.closePopup(n, attackPopup);
            System.out.println("close atk popup");
            int atkUnits = atkDropDown.getSelection();
            int defUnits = defDropDown.getSelection();
            BackEndTerritory originBack = originTerritory.getBackEndTerritory();
            BackEndTerritory destBack = destTerritory.getBackEndTerritory();
            currentBattle = new Battle(originBack, destBack, atkUnits, defUnits);
            currentBattle.attack();
            DiceManager dm = DiceManager.getInstance();
            dm.setBattle(currentBattle);
            parent.setRavenMessage(currentBattle.getAttacker().getOwner().getName()+" está atacando "+currentBattle.getDefender().getOwner().getName()+"!");
            dm.showDices(atkUnits, defUnits);
            dm.setAttackingTerritory(originTerritory);
            dm.setDefendingTerritory(destTerritory);
            ArmyRenderComponent comp = (ArmyRenderComponent) originTerritory.getArmy().getComponent("army-renderer");
            comp.setMovementTo(destTerritory);
            comp.setMovingQuantity(atkUnits);
            System.out.println("reset 3");
            resetTerritories();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void showRearrangeInfo(){
        parent.setInfoLabelText("Selecione o território que irá receber exércitos.");
    }
    
    private void showAttackInfo(){
        parent.setInfoLabelText("Selecione o território inimigo que deseja atacar.");
    }
    
    protected void dismissRearrangePopup(){
        PopupManager.closePopup(n, rearrangePopup);
        System.out.println("reset 4");
        resetTerritories();
    }
    
    protected void menuItemClicked(final String id, final MenuItemActivatedEvent event, final Screen s) {
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
        System.out.println("reset 5");
        resetTerritories();
        PopupManager.closePopup(n, rearrangeConfirmationPopup);
    }

    public void rearrangeOK() {
//        int armiesToMove = selectUnitsDropdown.getSelection().getCount();
//        originTerritory.getBackEndTerritory().decreaseArmies(armiesToMove);
//        originTerritory.getBackEndTerritory().setMovedArmies(armiesToMove);
//        ArmyRenderComponent armyRenderer = (ArmyRenderComponent) originTerritory.getArmy().getComponent("army-renderer");
//        armyRenderer.setMovingQuantity(armiesToMove);
//        armyRenderer.setMovementTo(destTerritory);
//        armyRenderer.startDistribution();
//        parent.setRavenMessage(Board.getInstance().getCurrentPlayer().getName()+" moveu "+armiesToMove+" territórios.");
        performeRearrange(originTerritory, destTerritory, selectUnitsDropdown.getSelection().getCount());
        dismissRearrangePopup();
//        if (Board.getInstance().hasGameEnded()) {
//            GameScene.getInstance().startGameEndingAnimation();
//        }
    }
    
    public void performeRearrange(Territory origin, Territory dest, int count){
        int armiesToMove = count;
        origin.getBackEndTerritory().decreaseArmies(armiesToMove);
        origin.getBackEndTerritory().setMovedArmies(armiesToMove);
        ArmyRenderComponent armyRenderer = (ArmyRenderComponent) origin.getArmy().getComponent("army-renderer");
        armyRenderer.setMovingQuantity(armiesToMove);
        armyRenderer.setMovementTo(dest);
        armyRenderer.startDistribution();
        parent.setRavenMessage(Board.getInstance().getCurrentPlayer().getName()+" moveu "+armiesToMove+" territórios.");
//        dismissRearrangePopup();
        if (Board.getInstance().hasGameEnded()) {
            GameScene.getInstance().startGameEndingAnimation();
        }
    }
    
    public void dismissRearrangeConfirmation() {
        originTerritory = null;
        System.out.println("dismiss rearrange, set origin to null");
        PopupManager.closePopup(n, rearrangeConfirmationPopup);
    }
    
    protected void cancelAttackPopup(){
        PopupManager.closePopup(n, attackPopup);
        System.out.println("reset 6");
        resetTerritories();
    }
    
    public void selectVictoriousArmiesToMove(Screen s, int winners) {
        dropDownSelectVictoriousArmies.clear();
        int unitsCount = winners;
        for(int i = 1; i <= unitsCount; i++)
            dropDownSelectVictoriousArmies.addItem(new UnitCount(i));
        PopupManager.showPopup(n, s, takeToConqueredTerritoryPopup);
    }
    
    public void moveVictoriousArmies() {
        PopupManager.closePopup(n, takeToConqueredTerritoryPopup);
        int armiesToMove = dropDownSelectVictoriousArmies.getSelection().getCount();
        currentBattle.moveVictoriousArmies(armiesToMove);
        currentBattle.getAttacker().setMovedArmies(armiesToMove);
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
    
    public void resetTerritories() {
        originTerritory = destTerritory = null;
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
