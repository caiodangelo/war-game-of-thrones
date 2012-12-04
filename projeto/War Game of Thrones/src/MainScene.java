import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import util.Scene;

public class MainScene extends Scene{

    private Nifty n;
    
    @Override
    public int getID() {
        return WarScenes.STARTING_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("addPlayer");
        
        DropDown dp = n.getCurrentScreen().findNiftyControl("dropDown2", DropDown.class);
        String houses[] = {"Stark", "Targaryen", "Lannister", "Greyjoy", "Baratheon", "Free Folk"};
        for(String house : houses)
            dp.addItem(house);
    }
}
