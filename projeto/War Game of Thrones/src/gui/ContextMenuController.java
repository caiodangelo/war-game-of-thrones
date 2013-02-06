package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import main.Territory;

public class ContextMenuController {

    private DropDown<UnitCount> selectUnitsDropdown;
    private Element contextMenu, rearrangePopup;
    
    private Nifty n;
    private InGameGUIController parent;
    
    private static final byte MENU_ATACK = 0, MENU_DISTRIBUTE = 1, MENU_CANCEL = 2;
    
    private Territory moveOrigin, moveDestiny, currentTemp;
    
    public ContextMenuController(Nifty n, InGameGUIController parent){
        this.n = n;
        this.parent = parent;
        
        rearrangePopup = n.createPopup("rearrangePopup");
        selectUnitsDropdown = rearrangePopup.findNiftyControl("dropDownSelectArmies", DropDown.class);
        
        //create context menu
        contextMenu = n.createPopup("niftyPopupMenu");
        Menu<Byte> popupMenu = contextMenu.findNiftyControl("#menu", Menu.class);
        popupMenu.setWidth(new SizeValue("15%"));
        popupMenu.addMenuItem("Atacar daqui", "resources/images/icons/atacar.png", MENU_ATACK);
        popupMenu.addMenuItem("Distribuir exércitos", "resources/images/icons/distribuir.png", MENU_DISTRIBUTE);
        popupMenu.addMenuItem("Cancelar", MENU_CANCEL);
        popupMenu.setId("menuItemid");
    }
    
    protected void handleTerritoryClick(Screen s, Territory t){
        currentTemp = t;
        if(moveOrigin == null){
            n.showPopup(s, contextMenu.getId(), null);
        } else {
            moveDestiny = t;
            parent.setInfoLabelText(null);
            showRearrangePopup(s);
        }
    }
    
    //territory context menu event handling
    private void showRearrangePopup(Screen screen){
        selectUnitsDropdown.clear();
        int unitsCount = 3;
        for(int i = 1; i <= unitsCount; i++)
            selectUnitsDropdown.addItem(new UnitCount(i));
        n.showPopup(screen, rearrangePopup.getId(), null);
    }
    
    private void showRearrangeInfo(){
        parent.setInfoLabelText("Selecione o território que irá receber exércitos.");
    }
    
    protected void dismissRearrangePopup(){
        n.closePopup(rearrangePopup.getId());
        moveOrigin = moveDestiny = null;
    }
    
    protected void MenuItemClicked(final String id, final MenuItemActivatedEvent event, final Screen s) {
        byte option = (Byte) event.getItem();
        if(option == MENU_ATACK)
            System.out.println("atack");
        else if(option == MENU_DISTRIBUTE){
            moveOrigin = currentTemp;
            showRearrangeInfo();
        }
        n.closePopup(contextMenu.getId()); 
    }

    public void rearrangeOK() {
        int armiesToMove = selectUnitsDropdown.getSelection().getCount();
        System.out.println("[Front-End] requesting from back end to move " + armiesToMove +
                " from " + moveOrigin + " to " + moveDestiny);
        dismissRearrangePopup();
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
