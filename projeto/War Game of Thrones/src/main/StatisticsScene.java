package main;

import de.lessvoid.nifty.Nifty;
import util.Scene;

public class StatisticsScene extends Scene{
    
    @Override
    public int getID() {
        return WarScenes.STATISTICS_SCENE.ordinal();
    }

    @Override
    public void setupNifty(Nifty n) {
        n.gotoScreen("statisticsScreen");
    }

}
