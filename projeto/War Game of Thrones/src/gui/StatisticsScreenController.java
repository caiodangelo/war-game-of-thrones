package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import main.WarScenes;
import models.StatisticGameManager;

public class StatisticsScreenController implements ScreenController {
    
    private Nifty n;
    private Screen s;   
    private DropDown optionsDropdown;
    private String selectedId;
    private HashMap<String, Integer> optionIndexes;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        n = nifty;
        s = screen;
        optionsDropdown = screen.findNiftyControl("statisticOptionsDropDown", DropDown.class);
        String[] options = {
            "Número de ataques e porcentagem de sucesso",
            "Número de defesas e porcentagem de sucesso",
            "Média nos dados de ataque",
            "Média nos dados de defesa",
            "Número de trocas de cartas",
            "Exércitos recebidos",
            "Exércitos perdidos",
            "Jogador que mais atacou",
            "Jogador por quem mais foi atacado",
        };
        optionIndexes = new HashMap();
        for (int i = 0; i < options.length; i++) {
            optionIndexes.put(options[i], i+1);
        }
        ArrayList<String> items = new ArrayList(Arrays.asList(options));
        optionsDropdown.addAllItems(items);
        selectedId = "numeroDeTrocasDeCartas";
        s.findElementByName(selectedId).setVisible(true);
    }
    
    public void setGeneralStatistics() {
        StatisticGameManager generalStatistics = StatisticGameManager.getInstance();
        generalStatistics.getPlayTime();
        s.findNiftyControl("tempo-duracao-jogo", Label.class).setText(generalStatistics.getPlayTime());
        //temp
        s.findNiftyControl("maior-numero-ataques", Label.class).setText(generalStatistics.getPlayTime());
        s.findNiftyControl("maior-numero-defesas", Label.class).setText(generalStatistics.getPlayTime());
        s.findNiftyControl("maior-sucesso-ataques", Label.class).setText(generalStatistics.getPlayTime());
        s.findNiftyControl("maior-sucesso-defesas", Label.class).setText(generalStatistics.getPlayTime());
        s.findNiftyControl("territorio-mais-atacado", Label.class).setText(generalStatistics.getPlayTime());
        s.findNiftyControl("territorio-mais-conquistado", Label.class).setText(generalStatistics.getPlayTime());
    }
    
    @Override
    public void onStartScreen() {
        setGeneralStatistics();
    }

    @Override
    public void onEndScreen() {    }
    
    public void backToMainScreen() {
        main.Main.getInstance().enterState(WarScenes.STARTING_SCENE);
    }
    
    public void exitGame() {
        main.Main.getInstance().getGameContainer().exit();
    }
    
    @NiftyEventSubscriber(id="statisticOptionsDropDown")
    public void onDropdownSelectionChanged(String id, DropDownSelectionChangedEvent evt){
        int selected = optionIndexes.get(optionsDropdown.getSelection());
        
        switch (selected) {
            case 1:
                s.findElementByName("numeroDeAtaquesEPorcentagemDeSucesso").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "numeroDeAtaquesEPorcentagemDeSucesso";
                break;
            case 2: 
                s.findElementByName("numeroDeDefesasEPorcentagemDeSucesso").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "numeroDeDefesasEPorcentagemDeSucesso";
                break;
            case 3: 
                s.findElementByName("mediaNosDadosDeAtaque").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "mediaNosDadosDeAtaque";
                break;
            case 4:
                s.findElementByName("mediaNosDadosDeDefesa").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "mediaNosDadosDeDefesa";
                break;
            case 5: 
                s.findElementByName("numeroDeTrocasDeCartas").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "numeroDeTrocasDeCartas";
                break;
            case 6: 
                s.findElementByName("exercitosRecebidos").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "exercitosRecebidos";
                break;
            case 7: 
                s.findElementByName("exercitosPerdidos").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "exercitosPerdidos";
                break;
            case 8: 
                s.findElementByName("jogadorQueMaisAtacou").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "jogadorQueMaisAtacou";
                break;
            case 9: 
                s.findElementByName("jogadorPorQuemMaisFoiAtacado").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "jogadorPorQuemMaisFoiAtacado";
                break;
            default: break;
        }
    }
    
}
