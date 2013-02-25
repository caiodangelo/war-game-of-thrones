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
import models.Player;
import util.PopupManager;

public class ContextMenuController {

    private DropDown<UnitCount> selectUnitsDropdown;
    private DropDown<Integer> atkDropDown, defDropDown;
    private Element contextMenu, rearrangePopup, attackPopup, fewArmiesPopup;
    
    private Nifty n;
    private InGameGUIController parent;
    
    private static final byte MENU_ATTACK = 0, MENU_DISTRIBUTE = 1, MENU_CANCEL = 2;
    
    private Territory originTerritory, destTerritory, currentTemp;
    
    private boolean onAtkSequence;
    
    public ContextMenuController(Nifty n, InGameGUIController parent){
        this.n = n;
        this.parent = parent;
        
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
        
        fewArmiesPopup = n.createPopup("fewArmiesPopup");
    }
    
    protected void handleTerritoryClick(Screen s, Territory t){
        currentTemp = t;
        if(originTerritory == null){
            PopupManager.showPopup(n, s, contextMenu);
        } else {
            destTerritory = t;
            parent.setInfoLabelText(null);
            if(onAtkSequence)
                showAttackPopup(s);
            else
                showRearrangePopup(s);
            //Map.selectedTerritory = null;
        }
    }
    
    //territory context menu event handling
    private void showRearrangePopup(Screen screen){
        selectUnitsDropdown.clear();
        Player owner = originTerritory.getBackEndTerritory().getOwner();
        models.Territory origin = originTerritory.getBackEndTerritory();
//        int unitsCount = origin.getNumArmiesCanMoveThisRound();
//        System.out.println("UNITS THAT CAN BE MOVED " + unitsCount);
        int unitsCount = 3;
        for(int i = 1; i <= unitsCount; i++)
            selectUnitsDropdown.addItem(new UnitCount(i));
        PopupManager.showPopup(n, screen, rearrangePopup);
    }
    
    private void showAttackPopup(Screen screen){
        PopupManager.showPopup(n, screen, attackPopup);
        atkDropDown.clear();
        defDropDown.clear();
        
        //set player names and colors
        Label atkPlayerName = attackPopup.findNiftyControl("atkPlayerName", Label.class);
        Label defPlayerName = attackPopup.findNiftyControl("defPlayerName", Label.class);
        models.Territory backAtkTer = originTerritory.getBackEndTerritory(), 
                backDefTer = destTerritory.getBackEndTerritory();
        Player attacker = backAtkTer.getOwner();
        Player defender = backDefTer.getOwner();
        atkPlayerName.setColor(attacker.getHouse().getColor());
        atkPlayerName.setText(attacker.getName());
        defPlayerName.setColor(defender.getHouse().getColor());
        defPlayerName.setText(defender.getName());
        
        int maxAtkUnits = Math.min(3, backAtkTer.getNumArmies());
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
        //chamar o ataque aqui
        //blablalba
        DiceManager dm = DiceManager.getInstance();
        dm.showDices(atkUnits, defUnits);
        dm.setAttackingTerritory(originTerritory);
        ArmyRenderComponent comp = (ArmyRenderComponent) originTerritory.getArmy().getComponent("army-renderer");
        comp.setOrigin(originTerritory.getArmy().getPosition());
        comp.setDestiny(destTerritory.getArmy().getPosition());
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
    
    protected void dismissFewArmiesPopup(){
        PopupManager.closePopup(n, fewArmiesPopup);
    }
    
    protected void MenuItemClicked(final String id, final MenuItemActivatedEvent event, final Screen s) {
        byte option = (Byte) event.getItem();
        if(option == MENU_ATTACK){
            //TODO: get available units from back-end
            int availableUnits = 3;
            if(availableUnits > 1){
                onAtkSequence = true;
                originTerritory = currentTemp;
                showAttackInfo();
            } else
                PopupManager.showPopup(n, s, fewArmiesPopup);
        }
        else if(option == MENU_DISTRIBUTE){
            onAtkSequence = false;
            originTerritory = currentTemp;
            showRearrangeInfo();
        }
        PopupManager.closePopup(n, contextMenu);
    }

    public void rearrangeOK() {
        int armiesToMove = selectUnitsDropdown.getSelection().getCount();
        System.out.println("[Front-End] requesting from back end to move " + armiesToMove +
                " from " + originTerritory + " to " + destTerritory);
        dismissRearrangePopup();
    }
    
    protected void cancelAttackPopup(){
        PopupManager.closePopup(n, attackPopup);
        originTerritory = destTerritory = null;
    }
    
    public void closePopupMenu() {
        PopupManager.closePopup(n, contextMenu);
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
