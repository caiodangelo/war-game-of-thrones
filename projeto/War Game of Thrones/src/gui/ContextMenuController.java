package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;
import main.DiceManager;
import main.Territory;
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
        
        fewArmiesPopup = n.createPopup("fewArmiesPopup");
    }
    
    protected void handleTerritoryClick(Screen s, Territory t){
        currentTemp = t;
        if(originTerritory == null){
            n.showPopup(s, contextMenu.getId(), null);
        } else {
            destTerritory = t;
            parent.setInfoLabelText(null);
            if(onAtkSequence)
                showAttackPopup(s);
            else
                showRearrangePopup(s);
        }
    }
    
    //territory context menu event handling
    private void showRearrangePopup(Screen screen){
        selectUnitsDropdown.clear();
        int unitsCount = 3;
        for(int i = 1; i <= unitsCount; i++)
            selectUnitsDropdown.addItem(new UnitCount(i));
        PopupManager.showPopup(n, screen, rearrangePopup);
    }
    
    private void showAttackPopup(Screen screen){
        PopupManager.showPopup(n, screen, attackPopup);
        atkDropDown.clear();
        defDropDown.clear();
        
        //set player names colors
        Label atkPlayerName = attackPopup.findNiftyControl("atkPlayerName", Label.class);
        Label defPlayerName = attackPopup.findNiftyControl("atkPlayerName", Label.class);
        //MUST GET THE REAL COLORS!!!
        atkPlayerName.setColor(new Color("#465DC0"));
        defPlayerName.setColor(new Color("#41BA47"));
        
        int maxAtkUnits = 3;//retrieve the number of availabel armies (max 3)
        int maxDefUnits = 3;
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
        n.closePopup(contextMenu.getId());
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
