package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import de.lessvoid.nifty.tools.Color;
import java.util.Properties;

public class StatusPanelControlImpl extends AbstractController implements Controller, StatusPanelControl{

    private Label nameLabel, cardsLabel, unitsLabel, territoriesLabel;
    
    @Override
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        bind(element);
        nameLabel = element.findNiftyControl("#playerStatusName", Label.class);
        cardsLabel = element.findNiftyControl("#playerStatusCards", Label.class);
        unitsLabel = element.findNiftyControl("#playerStatusUnits", Label.class);
        territoriesLabel = element.findNiftyControl("#playerStatusTerritories", Label.class);
    }

    private void updateLabel(Label l, Properties parameter, String propName, String defaultValue){
        String value = parameter.getProperty(propName);
        if(value == null)
            value = defaultValue;
        l.setText(value);
    }
    
    @Override
    public void onStartScreen() {
    }

    @Override
    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }
    
    @Override
    public void updateData(String playerName, int cards, int units, int territories){
        nameLabel.setText(playerName);
        setLabel(cardsLabel, cards, "Carta");
        setLabel(unitsLabel, units, "Exército");
        setLabel(territoriesLabel, territories, "Território");
    }
    
    public static void setLabel(Label l, int count, String unitName){
        l.setText(count + " " + unitName + (count > 1 ? "s" : ""));
    }
    
    @Override
    public void setNameColor(Color c){
        nameLabel.setColor(c);
    }
}
