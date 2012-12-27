package gui;

import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.tools.Color;

public interface StatusPanelControl extends NiftyControl{
    public void updateData(String playerName, int cards, int units, int territories);
    public void setNameColor(Color c);
}
