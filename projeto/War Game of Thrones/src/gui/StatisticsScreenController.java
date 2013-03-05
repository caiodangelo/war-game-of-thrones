package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import main.WarScenes;
import models.Board;
import models.Player;
import models.StatisticGameManager;

public class StatisticsScreenController implements ScreenController {
    
    private static final String[] OPTIONS = {
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
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("###.##");
    
    private Screen s;   
    private DropDown optionsDropdown;
    private String selectedId;
    private HashMap<String, Integer> optionIndexes;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        s = screen;
        optionsDropdown = screen.findNiftyControl("statisticOptionsDropDown", DropDown.class);
        optionIndexes = new HashMap();
        for (int i = 0; i < OPTIONS.length; i++) {
            optionIndexes.put(OPTIONS[i], i+1);
        }
        ArrayList<String> items = new ArrayList(Arrays.asList(OPTIONS));
        optionsDropdown.addAllItems(items);
        selectedId = "numeroDeAtaquesEPorcentagemDeSucesso";
        s.findElementByName(selectedId).setVisible(true);
    }
    
    public void setGeneralStatistics() {
        StatisticGameManager generalStatistics = StatisticGameManager.getInstance();
        generalStatistics.getPlayTime();
        s.findNiftyControl("tempo-duracao-jogo", Label.class).setText(generalStatistics.getPlayTime());
        s.findNiftyControl("maior-numero-ataques", Label.class).setText(generalStatistics.getMoreAttacker().get(0).getName());
        s.findNiftyControl("maior-numero-defesas", Label.class).setText(generalStatistics.getMoreDefender().get(0).getName());
        s.findNiftyControl("maior-sucesso-ataques", Label.class).setText(generalStatistics.getMostWinnerAttacks().get(0).getName());
        s.findNiftyControl("maior-sucesso-defesas", Label.class).setText(generalStatistics.getMostWinnerDefences().get(0).getName());
        s.findNiftyControl("territorio-mais-atacado", Label.class).setText(generalStatistics.getTerritoryMoreAttacked().get(0).getName());
        s.findNiftyControl("territorio-mais-conquistado", Label.class).setText(generalStatistics.getTerritoryMoreConquered().get(0).getName());
    }
    
    public void setPlayerStatistics() {
        LinkedList<Player> players = Board.getInstance().getPlayers();
        //attacks and success percentage
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("ataques-porcentagem-sucesso-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("ataques"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getNumberOfAttacks()+"");
            s.findNiftyControl("atk-porcentagem-sucesso"+(i+1), Label.class).setText(DECIMAL_FORMATTER.format(players.get(i).getStatisticPlayerManager().getSuccessfulAttackPercentage())+"%");
        }
        
        //defences and success percentage
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("defesas-porcentagem-sucesso-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("defesas"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getNumberOfDefences()+"");
            s.findNiftyControl("def-porcentagem-sucesso"+(i+1), Label.class).setText(DECIMAL_FORMATTER.format(players.get(i).getStatisticPlayerManager().getSuccessfulDefencePercentage())+"%");
        }
        
        //attack dices average
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("media-dados-ataque-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("media-dados-atk"+(i+1), Label.class).setText(DECIMAL_FORMATTER.format(players.get(i).getStatisticPlayerManager().getDicesOfAttackAverage()));
        }
        
        //defence dices average
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("media-dados-defesa-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("media-dados-def"+(i+1), Label.class).setText(DECIMAL_FORMATTER.format(players.get(i).getStatisticPlayerManager().getDicesOfDefenceAverage()));
        }
        
        //card tradings times
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("card-trading-times-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("card-trading-times"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getNumberOfCardsSwapped()+"");
        }
        
        //received armies
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("received-armies-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("received-armies"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getReceivedArmies()+"");
        }
        
        //lost armies
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("lost-armies-player"+(i+1), Label.class).setText(players.get(i).getName());
            s.findNiftyControl("lost-armies"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getLostArmies()+"");
        }
        
        //who the given player most attacked
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("who-you-attacked-most-player"+(i+1), Label.class).setText(players.get(i).getName());
            ArrayList<Player> mostAttackedPlayers = players.get(i).getStatisticPlayerManager().getYouAttackMore();
            if (mostAttackedPlayers.size() > 0)
                s.findNiftyControl("who-you-attacked-most"+(i+1), Label.class).setText(mostAttackedPlayers.get(0).getName());
            s.findNiftyControl("who-you-attacked-most-attacks"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getMostAttacks()+"");
        }
        
        //who most attacked the given player
        for (int i = 0; i < players.size(); i++) {
            s.findNiftyControl("who-attacked-you-most-player"+(i+1), Label.class).setText(players.get(i).getName());
            ArrayList<Player> mostAttackerPlayers = players.get(i).getStatisticPlayerManager().getMoreEnemy();
            if (mostAttackerPlayers.size() > 0)
                s.findNiftyControl("who-attacked-you-most"+(i+1), Label.class).setText(mostAttackerPlayers.get(0).getName());
            s.findNiftyControl("who-attacked-you-most-attacks"+(i+1), Label.class).setText(players.get(i).getStatisticPlayerManager().getMostDefences()+"");
        }
    }
    
    @Override
    public void onStartScreen() {
        setGeneralStatistics();
        setPlayerStatistics();
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
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("numeroDeAtaquesEPorcentagemDeSucesso").setVisible(true);
                selectedId = "numeroDeAtaquesEPorcentagemDeSucesso";
                break;
            case 2: 
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("numeroDeDefesasEPorcentagemDeSucesso").setVisible(true);
                selectedId = "numeroDeDefesasEPorcentagemDeSucesso";
                break;
            case 3: 
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("mediaNosDadosDeAtaque").setVisible(true);
                selectedId = "mediaNosDadosDeAtaque";
                break;
            case 4:
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("mediaNosDadosDeDefesa").setVisible(true);
                selectedId = "mediaNosDadosDeDefesa";
                break;
            case 5: 
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("numeroDeTrocasDeCartas").setVisible(true);
                selectedId = "numeroDeTrocasDeCartas";
                break;
            case 6: 
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("exercitosRecebidos").setVisible(true);
                selectedId = "exercitosRecebidos";
                break;
            case 7: 
                s.findElementByName("exercitosPerdidos").setVisible(true);
                s.findElementByName(selectedId).setVisible(false);
                selectedId = "exercitosPerdidos";
                break;
            case 8: 
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("jogadorQueMaisAtacou").setVisible(true);
                selectedId = "jogadorQueMaisAtacou";
                break;
            case 9: 
                s.findElementByName(selectedId).setVisible(false);
                s.findElementByName("jogadorPorQuemMaisFoiAtacado").setVisible(true);
                selectedId = "jogadorPorQuemMaisFoiAtacado";
                break;
            default: break;
        }
    }
    
}
