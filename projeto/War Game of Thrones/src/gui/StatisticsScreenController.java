package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsScreenController implements ScreenController {
    
    private Nifty n;
    private Screen s;   
    private DropDown optionsDropdown;
    private String selectedId;
    private HashMap<String, Integer> options;
    
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.n = nifty;
        this.s = screen;
        optionsDropdown = screen.findNiftyControl("statisticOptionsDropDown", DropDown.class);
        options = new HashMap();
        options.put("Número de ataques e porcentagem de sucesso", 1);
        options.put("Número de defesas e porcentagem de sucesso", 2);
        options.put("Média nos dados de ataque", 3);
        options.put("Média nos dados de defesa", 4);
        options.put("Número de trocas de cartas", 5);
        options.put("Exércitos recebidos", 6);
        options.put("Exércitos perdidos", 7);
        options.put("Jogador que mais atacou", 8);
        options.put("Jogador por quem mais foi atacado", 9);
        ArrayList items = new ArrayList();
        items.add("");
        items.addAll(options.keySet());
        optionsDropdown.addAllItems(items);
    }
    
    @Override
    public void onStartScreen() {    }

    @Override
    public void onEndScreen() {    }
    
    @NiftyEventSubscriber(id="statisticOptionsDropDown")
    public void onDropdownSelectionChanged(String id, DropDownSelectionChangedEvent evt){
        int selected = options.get(optionsDropdown.getSelection());
        if (selectedId != null)
            s.findElementByName(selectedId).setVisible(false);
        else
            optionsDropdown.removeItem("");
        switch (selected) {
            case 1:
                s.findElementByName("numeroDeAtaquesEPorcentagemDeSucesso").setVisible(true);
                selectedId = "numeroDeAtaquesEPorcentagemDeSucesso";
                break;
            case 2: 
                s.findElementByName("numeroDeDefesasEPorcentagemDeSucesso").setVisible(true);
                selectedId = "numeroDeDefesasEPorcentagemDeSucesso";
                break;
            case 3: 
                s.findElementByName("mediaNosDadosDeAtaque").setVisible(true);
                selectedId = "mediaNosDadosDeAtaque";
                break;
            case 4: s.findElementByName("mediaNosDadosDeDefesa").setVisible(true);
                selectedId = "mediaNosDadosDeDefesa";
                break;
            case 5: 
                s.findElementByName("numeroDeTrocasDeCartas").setVisible(true);
                selectedId = "numeroDeTrocasDeCartas";
                break;
            case 6: 
                s.findElementByName("exercitosRecebidos").setVisible(true);
                selectedId = "exercitosRecebidos";
                break;
            case 7: 
                s.findElementByName("exercitosPerdidos").setVisible(true);
                selectedId = "exercitosPerdidos";
                break;
            case 8: 
                s.findElementByName("jogadorQueMaisAtacou").setVisible(true);
                selectedId = "jogadorQueMaisAtacou";
                break;
            case 9: 
                s.findElementByName("jogadorPorQuemMaisFoiAtacado").setVisible(true);
                selectedId = "jogadorPorQuemMaisFoiAtacado";
                break;
            default: break;
        }
    }
    
}
