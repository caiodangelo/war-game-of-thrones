package main;

import de.lessvoid.nifty.Nifty;
import util.Scene;

public class GUITest extends Scene{

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("inGameScreen");
    }
}
