package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import main.Territory;

public class ContextMenuController {

    private DropDown<String> selectUnitsDropdown;
    private Element contextMenu, rearrangePopup;
    
    private Nifty n;
    
    private static final byte MENU_ATACK = 0, MENU_DISTRIBUTE = 1, MENU_CANCEL = 2;
    
    public ContextMenuController(Nifty n){
        this.n = n;
        
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
    
    protected void _openTerritoryMenu(Screen s, Territory t){
        n.showPopup(s, contextMenu.getId(), null);
    }
    
    //territory context menu event handling
    private void showRearrangePopup(Screen screen){
        selectUnitsDropdown.clear();
        int unitsCount = 3;
        for(int i = 1; i <= unitsCount; i++){
            String pluralSufix = (i > 1 ? "s" : "");
            selectUnitsDropdown.addItem(i + " unidade" + pluralSufix);
        }
        n.showPopup(screen, rearrangePopup.getId(), null);
    }
    
    protected void dismissRearrangePopup(){
        n.closePopup(rearrangePopup.getId());
    }
    
    protected void rearrangePopupOK(){
        n.closePopup(rearrangePopup.getId());
    }
            
    protected void MenuItemClicked(final String id, final MenuItemActivatedEvent event, final Screen s) {
        byte option = (Byte) event.getItem();
        if(option == MENU_ATACK)
            System.out.println("atack");
        else if(option == MENU_DISTRIBUTE)
            showRearrangePopup(s);
        n.closePopup(contextMenu.getId()); 
    }
}
