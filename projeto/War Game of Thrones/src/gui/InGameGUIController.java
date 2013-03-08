package gui;

import static main.Constants.*;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import main.*;
import models.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import util.PopupManager;

public class InGameGUIController implements ScreenController {

    private StatusPanelControl[] statusPanels;
    private Label playerStatusName, playerStatusCards, playerStatusUnits, playerStatusTerritories, infoTerritories,
            ravenMessage, alert;
    private Screen s;
    private Nifty n;
    private Board b;
    private List<Player> players;
    private Element objectivePopup, exitConfirmPopup, tablesPopup, objectiveLabel, viewCardsLabel,
            optionsPopup, helpPopup, infoPanel, nextTurnConfirmPopup, tablesIcon, infoTerritoriesPopup,
            alertPopup, cardEarnedPopup, emptyPopup;
    private boolean mouseOverObjective = false;
    private ContextMenuController ctxMenuCtrl;
    private CardsController cardsCtrl;
    private static InGameGUIController instance;
    private HashMap<Integer, String> turnsOrder;
    private HashMap<String, String> regionsColors;
    private boolean mayGoToStatistics;

    public InGameGUIController() {
        turnsOrder = new HashMap();
        turnsOrder.put(0, "primeiro");
        turnsOrder.put(1, "segundo");
        turnsOrder.put(2, "terceiro");
        turnsOrder.put(3, "quarto");
        turnsOrder.put(4, "quinto");
        turnsOrder.put(5, "sexto");
        regionsColors = new HashMap();
        regionsColors.put("Além da Muralha", "\\#FFFFFF#");
        regionsColors.put("Cidades Livres", "\\#660066#");
        regionsColors.put("O Norte", "\\#66CCFF#");
        regionsColors.put("O Sul", "\\#66CC00#");
        regionsColors.put("Tridente", "\\#CCCC00#");
        regionsColors.put("O Mar Dothraki", "\\#FF6600#");
        mayGoToStatistics = false;
        instance = this;
    }

    public static InGameGUIController getInstance() {
        if (instance == null)
            instance = new InGameGUIController();
        return instance;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        s = screen;
        n = nifty;
        playerStatusName = screen.findNiftyControl("playerStatusName", Label.class);
        playerStatusCards = screen.findNiftyControl("playerStatusCards", Label.class);
        playerStatusUnits = screen.findNiftyControl("playerStatusUnits", Label.class);
        playerStatusTerritories = screen.findNiftyControl("playerStatusTerritories", Label.class);
        objectivePopup = n.createPopup("objectivePopup");
        exitConfirmPopup = n.createPopup("quitConfirmationPopup");
        tablesPopup = n.createPopup("tablesPopup");
        objectiveLabel = screen.findElementByName("seeObjectiveButton");
        viewCardsLabel = screen.findElementByName("seeCardsButton");
        tablesIcon = screen.findElementByName("tablesIcon");
        optionsPopup = n.createPopup("optionsPopup");
        helpPopup = n.createPopup("helpPopup");

        cardsCtrl = new CardsController(n, s, this);
        infoTerritoriesPopup = n.createPopup("infoTerritoriesPopup");
        infoTerritories = infoTerritoriesPopup.findNiftyControl("infoTerritories", Label.class);
        alertPopup = n.createPopup("alertPopup");
        alert = alertPopup.findNiftyControl("alert", Label.class);
        cardEarnedPopup = n.createPopup("cardEarnedPopup");
        emptyPopup = n.createPopup("emptyPopup");

        ravenMessage = screen.findNiftyControl("ravenMessage", Label.class);
        infoPanel = screen.findElementByName("infoPanel");
        nextTurnConfirmPopup = n.createPopup("nextTurnConfirmationPopup");
    }

    public void setRavenMessage(String msg) {
        ravenMessage.setText(msg);
    }

    public void showAlert(String text) {
        alert.setText(text);
        PopupManager.showPopup(n, s, alertPopup);
    }

    public void mayGoToStatistics() {
        mayGoToStatistics = true;
    }

    @Override
    public void onStartScreen() {
        b = Board.getInstance();
        ctxMenuCtrl = new ContextMenuController(n, s, this);
        List<Player> playersList = b.getPlayers();
        players = playersList;

        retrieveStatusPanels(s);
        updatePlayersData();

        Slider musicSlider = optionsPopup.findNiftyControl("musicSlider", Slider.class);
        musicSlider.setValue(1 - AudioManager.getInstance().getMusicVolume());
        Slider soundSlider = optionsPopup.findNiftyControl("soundSlider", Slider.class);
        soundSlider.setValue(1 - AudioManager.getInstance().getSoundVolume());
        Slider AIMovementsSpeed = optionsPopup.findNiftyControl("sliderCPUMovementSpeed", Slider.class);
        AIMovementsSpeed.setValue(11 - Math.round(Main.getInstance().getAIMapMovementsSpeed() * 10));

        Label musicVolumeValue = optionsPopup.findNiftyControl("musicVolumeValue", Label.class);
        musicVolumeValue.setText(((int) (100 * AudioManager.getInstance().getMusicVolume())) + "");
        Label soundVolumeValue = optionsPopup.findNiftyControl("soundVolumeValue", Label.class);
        soundVolumeValue.setText(((int) (100 * AudioManager.getInstance().getSoundVolume())) + "");
        Label AIMovementsSpeedValue = optionsPopup.findNiftyControl("CPUmovementSpeedValue", Label.class);
        System.out.println(Main.getInstance().getAIMapMovementsSpeed() * 10);
        System.out.println(Math.round(Main.getInstance().getAIMapMovementsSpeed() * 10));
        AIMovementsSpeedValue.setText(Math.round(Main.getInstance().getAIMapMovementsSpeed() * 10) + "");

        CheckBox musicMute = optionsPopup.findNiftyControl("musicMute", CheckBox.class);
        if (AudioManager.getInstance().musicIsMuted())
            musicMute.check();
        CheckBox soundMute = optionsPopup.findNiftyControl("soundMute", CheckBox.class);
        if (AudioManager.getInstance().soundIsMuted())
            soundMute.check();
        CheckBox showTerritories = optionsPopup.findNiftyControl("showTerritories", CheckBox.class);
        if (!Main.isShowingTerritoriesNames())
            showTerritories.uncheck();
    }

    @Override
    public void onEndScreen() {
    }

    public static void handleTerritoryClick(Territory t) {
        instance.ctxMenuCtrl.handleTerritoryClick(instance.s, t);
    }

    public void openEmptyPopup() {
        PopupManager.showPopup(n, s, emptyPopup);
    }

    private void retrieveStatusPanels(Screen s) {
        int playersCount = players.size();
        statusPanels = new StatusPanelControlImpl[playersCount];
        for (int i = 0; i < 6; i++) {
            StatusPanelControl spc = s.findNiftyControl("player" + i + "Status", StatusPanelControl.class);
            boolean visible = i < playersCount;
            if (visible)
                statusPanels[i] = spc;
            spc.getElement().setVisible(visible);
        }
    }

    public void updatePlayersData() {
        for (int i = 0; i < players.size(); i++) {
            StatusPanelControl spc = statusPanels[i];
            Player current = players.get(i);
            spc.updateData(current.getName(), current.numCards(), current.numArmies(), current.numTerritories());
            spc.setNameColor(players.get(i).getHouse().getColor());
        }
        updateCurrentPlayersData();
    }

    private Player getCurrentPlayer() {
        return b.getCurrentPlayer();
    }

    private boolean currentPlayerIsHuman() {
        return !getCurrentPlayer().isAIPlayer();
    }

    private Color getCurrentPlayerColor() {
        return getCurrentPlayer().getHouse().getColor();
    }

    private void updateCurrentPlayersData() {
        boolean skippedStartScreen = JUMP_TO_GAME;
        if (!skippedStartScreen) {
            Player currPlayer = getCurrentPlayer();
            Color currentPlayerColor = getCurrentPlayerColor();
            playerStatusName.setText(currPlayer.getName());
            playerStatusName.setColor(currentPlayerColor);
            StatusPanelControlImpl.setLabel(playerStatusCards, currPlayer.numCards(), "Carta");
            StatusPanelControlImpl.setLabel(playerStatusUnits, currPlayer.numArmies(), "Exército");
            StatusPanelControlImpl.setLabel(playerStatusTerritories, currPlayer.numTerritories(), "Território");
            updateCurrentHouse(currPlayer.getHouse());
        }
    }

    public void showPlayerObjective() {
        if (currentPlayerIsHuman()) {
            resetMouseCursor();
            Label description = objectivePopup.findNiftyControl("objectiveDescLabel", Label.class);
            String objectiveStr = "Seu objetivo é " + getCurrentPlayer().getMission().getDescription();
            description.setText(objectiveStr);
            PopupManager.showPopup(n, s, objectivePopup);
        }
    }

    public void showPlayerCards() {
        if (currentPlayerIsHuman()) {
            resetMouseCursor();
            cardsCtrl.showPopup();
        }
    }

    public void dismissPlayerObjective() {
        PopupManager.closePopup(n, objectivePopup);
    }

    public void dismissPlayerCards() {
        cardsCtrl.dissmissPopup();
    }

    public void tradePlayerCards() {
        cardsCtrl.tradeCards();
    }

    public void nextPlayerTurnConfirm() {
        PopupManager.showPopup(n, s, nextTurnConfirmPopup);
    }

    public void nextPlayerTurn() {
        Player curr = getCurrentPlayer();
        if (!curr.isAIPlayer())
            PopupManager.closePopup(n, nextTurnConfirmPopup);
        int pendingArmies = curr.getTotalPendingArmies();
        if (pendingArmies > 0)
            showAlert("Você ainda possui " + pendingArmies + " exércitos para distribuir!");
        else {
            ctxMenuCtrl.setDistributing(false);
            if (curr.mayReceiveCard()) {
                //sortear carta e colocá-la na popup
                curr.setMayReceiveCard(false);
                Element imgElement = cardEarnedPopup.findElementByName("earnedCardImage");
                ImageRenderer r = imgElement.getRenderer(ImageRenderer.class);
                CardTerritory c = RepositoryCardsTerritory.getInstance().getFirstCardFromDeck();
                curr.addCard(c);
                r.setImage(n.createImage(CardPaths.getPath(c), false));
                if (curr instanceof AIPlayer) {
                    TurnHelper.getInstance().changeTurn();
                    if (cardsCtrl.playerMustSwapCards())
                        if (Board.getInstance().getCurrentPlayer().isAIPlayer())
                            cardsCtrl.tradeCards();
                        else
                            cardsCtrl.showPopup();
                } else
                    PopupManager.showPopup(n, s, cardEarnedPopup);
            } else {
                TurnHelper.getInstance().changeTurn();
                if (cardsCtrl.playerMustSwapCards())
                    if (Board.getInstance().getCurrentPlayer().isAIPlayer())
                        cardsCtrl.tradeCards();
                    else
                        cardsCtrl.showPopup();
            }
            setInfoLabelText(null);
            System.out.println("reset 7");
            ctxMenuCtrl.resetTerritories();
        }
    }

    public void dismissNextTurnConfirmation() {
        PopupManager.closePopup(n, nextTurnConfirmPopup);
    }

    public void dismissCardEarnedPopup() {
        TurnHelper.getInstance().changeTurn();
        PopupManager.closePopup(n, cardEarnedPopup);
        if (cardsCtrl.playerMustSwapCards())
            if (Board.getInstance().getCurrentPlayer().isAIPlayer())
                cardsCtrl.tradeCards();
            else
                cardsCtrl.showPopup();
    }

    //Top Menu event handling
    public void theGameMenuClicked() {
        System.out.println("THE GAME (you just lost it)");
    }

    public void showOptions() {
        PopupManager.showPopup(n, s, optionsPopup);
    }

    public void helpMenuClicked() {
        PopupManager.showPopup(n, s, helpPopup);
    }

    public void exitMenuClicked() {
        PopupManager.showPopup(n, s, exitConfirmPopup);
    }
    //Top Menu event end

    public void closeOptions() {
        PopupManager.closePopup(n, optionsPopup);
    }

    public void closeHelpPopup() {
        PopupManager.closePopup(n, helpPopup);
    }

    //Popups event handling
    public void exitGame() {
        PopupManager.closePopup(n, exitConfirmPopup);
        Main.getInstance().enterState(WarScenes.STARTING_SCENE);
//        Main.getInstance().getGameContainer().exit();
    }

    public void showTables() {
        resetMouseCursor();
        PopupManager.showPopup(n, s, tablesPopup);
    }

    public void dismissExitConfirmation() {
        PopupManager.closePopup(n, exitConfirmPopup);
    }

    public void dismissTablesPopup() {
        PopupManager.closePopup(n, tablesPopup);
    }

    public void dismissAlertPopup() {
        PopupManager.closePopup(n, alertPopup);
    }

    /**
     * Use null to hide the panel.
     */
    public void setInfoLabelText(String text) {
        Label infoLabel = s.findNiftyControl("infoLabel", Label.class);
        if (text != null) {
            infoLabel.setText(text);
            infoPanel.setVisible(true);
        } else
            infoPanel.setVisible(false);
    }

    //Popups event handling end
    private void resetMouseCursor() {
        GameContainer c = Main.getInstance().getContainer();
        c.setDefaultMouseCursor();
    }

    private boolean mouseOverLink(int mouseX, int mouseY) {
        return objectiveLabel.isMouseInsideElement(mouseX, mouseY)
                || viewCardsLabel.isMouseInsideElement(mouseX, mouseY)
                || tablesIcon.isMouseInsideElement(mouseX, mouseY);
    }

    public void mouseMovedOverBottomPanel() {
        Input in = Main.getInstance().getContainer().getInput();
        boolean inside = mouseOverLink(in.getMouseX(), in.getMouseY());
        if (inside != mouseOverObjective) {
            mouseOverObjective = inside;
            if (!inside)
                resetMouseCursor();
            else {
                GameContainer c = Main.getInstance().getContainer();
                try {
                    c.setMouseCursor("resources/cursors/aero_link.png", 8, 1);
                } catch (SlickException ex) {
                    System.out.println("error setting cursor");
                }
            }
        }
    }

    public void dismissRearrangePopup() {
        ctxMenuCtrl.dismissRearrangePopup();
    }

    public void rearrangeConfirmed() {
        ctxMenuCtrl.rearrangeConfirmed();
    }

    public void dismissRearrangeConfirmation() {
        ctxMenuCtrl.dismissRearrangeConfirmation();
    }

    public void rearrangePopupOK() {
        ctxMenuCtrl.rearrangeOK();
    }

    public void confirmAtkUnits() {
        ctxMenuCtrl.confirmAtkUnits();
    }

    public void confirmDefUnits() {
        ctxMenuCtrl.confirmDefUnits();
    }

    public void cancelAttackPopup() {
        ctxMenuCtrl.cancelAttackPopup();
    }

    public void selectVictoriousArmiesToMove(int winners) {
        ctxMenuCtrl.selectVictoriousArmiesToMove(s, winners);
    }

    public void moveVictoriousArmies() {
        ctxMenuCtrl.moveVictoriousArmies();
    }

    public void showInfoTerritories() {
        String content = "\nAtenção, ";
        Board b = Board.getInstance();
        Player currPlayer = b.getCurrentPlayer();
        String turn = turnsOrder.get(b.getPlayerOrder(currPlayer));
        content += currPlayer.getName() + "! Os turnos foram sorteados e você é o " + turn + " a jogar!\n\nSeus territórios são:";
        String colorCode;
        Iterator<String> regionsIterator = regionsColors.keySet().iterator();
        String currIterationRegion;
        String auxRegion;
        String auxContent;
        boolean anyTerritoriesOnRegion = false;
        while (regionsIterator.hasNext()) {
            currIterationRegion = regionsIterator.next();
            colorCode = regionsColors.get(currIterationRegion);
            auxContent = "\n\n" + colorCode + "Região: " + currIterationRegion + colorCode + "\n";
            for (BackEndTerritory t : currPlayer.getTerritories()) {
                auxRegion = t.getRegion().getName();
                if (currIterationRegion.equals(auxRegion)) {
                    anyTerritoriesOnRegion = true;
                    auxContent += "\n" + colorCode + t.getName() + colorCode;
                }
            }
            if (anyTerritoriesOnRegion)
                content += auxContent;
            auxContent = "";
            anyTerritoriesOnRegion = false;
        }
        content += "\n\nATENÇÃO!!!!!! Seu objetivo está logo abaixo! Não deixe que nenhum outro jogador o veja!\n\n\nSEU OBJETIVO:\n\n" + currPlayer.getMission().getDescription();
        infoTerritories.setText(content);
        PopupManager.showPopup(n, s, infoTerritoriesPopup);
    }

    public void startPlayerInitialDistribution() {
        Player curr = b.getCurrentPlayer();
        List<BackEndTerritory> ts = b.getCurrentPlayer().getTerritories();

//        for(BackEndTerritory t : ts){
//            t.setNumArmies(1);
//        }
        showRemainingPendingArmies(curr, curr.getTotalPendingArmies());
        updatePlayersData();

        GameScene.getInstance().showPlayerTurnMsg();
    }

    public void showRemainingPendingArmies(Player p, int count) {
        setRavenMessage("\\#333333ff#" + p.getName() + " ainda possui \\#CC0000#" + count + "\\#333333ff# exército(s) para distribuir.");
    }

    public void closeInfoTerritoriesPopup() {
        PopupManager.closePopup(n, infoTerritoriesPopup);
        startPlayerInitialDistribution();
    }

    public void goToStatistics() {
        if (mayGoToStatistics) {
            Main.getInstance().enterState(WarScenes.STATISTICS_SCENE);
            AudioManager.getInstance().stopCurrentMusic();
        }
    }

    @NiftyEventSubscriber(id = "menuItemid")
    public void menuItemClicked(final String id, final MenuItemActivatedEvent event) {
        ctxMenuCtrl.menuItemClicked(id, event, s);
    }

    @NiftyEventSubscriber(id = "musicSlider")
    public void onMusicSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager.getInstance().changeMusicVolume(newVolume);
        Label musicVolumeValue = optionsPopup.findNiftyControl("musicVolumeValue", Label.class);
        musicVolumeValue.setText(((int) (newVolume * 100)) + "");
    }

    @NiftyEventSubscriber(id = "soundSlider")
    public void onSoundSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager.getInstance().changeSoundVolume(newVolume);
        Label soundVolumeValue = optionsPopup.findNiftyControl("soundVolumeValue", Label.class);
        soundVolumeValue.setText(((int) (newVolume * 100)) + "");
    }

    @NiftyEventSubscriber(id = "sliderCPUMovementSpeed")
    public void onCPUDifficultySliderChange(final String id, final SliderChangedEvent event) {
        float selectedAIMovementsSpeed = event.getValue() / 10f;
        Main.getInstance().setAIMapMovementsSpeed(1.1f - selectedAIMovementsSpeed);
        Label AIMovementsSpeedValue = optionsPopup.findNiftyControl("CPUmovementSpeedValue", Label.class);
        AIMovementsSpeedValue.setText((11 - (Math.round(selectedAIMovementsSpeed * 10))) + "");
    }

    @NiftyEventSubscriber(id = "musicMute")
    public void onMusicMuteChange(final String id, final CheckBoxStateChangedEvent event) {
        AudioManager am = AudioManager.getInstance();
        if (event.isChecked())
            am.muteMusic();
        else
            am.unmuteMusic();
    }

    @NiftyEventSubscriber(id = "soundMute")
    public void onSoundMuteChange(final String id, final CheckBoxStateChangedEvent event) {
        AudioManager am = AudioManager.getInstance();
        if (event.isChecked())
            am.muteSound();
        else
            am.unmuteSound();
    }

    @NiftyEventSubscriber(id = "showTerritories")
    public void onShowTerritoriesChange(final String id, final CheckBoxStateChangedEvent event) {
        Main.showTerritoriesNames(event.isChecked());
    }

    @NiftyEventSubscriber(id = "dontShowRearrangeConfirmationAgain")
    public void onDontShowRearrangeConfirmationAgainChange(final String id, final CheckBoxStateChangedEvent event) {
        ctxMenuCtrl.mayShowRearrangeConfirmationAgain(!event.isChecked());
    }

    private void updateCurrentHouse(House h) {
        Element imgElement = s.findElementByName("currHouseImg");
        ImageRenderer r = imgElement.getRenderer(ImageRenderer.class);
        r.setImage(n.createImage(h.getImgPath(), false));
        Label houseNameLabel = s.findNiftyControl("currHouseName", Label.class);
        houseNameLabel.setText(h.getName());
    }

    @NiftyEventSubscriber(id = "card0Checkbox")
    public void onCheckbox0Clicked(final String id, final CheckBoxStateChangedEvent event) {
        cardsCtrl.onCheckboxClicked(0);
    }

    @NiftyEventSubscriber(id = "card1Checkbox")
    public void onCheckbox1Clicked(final String id, final CheckBoxStateChangedEvent event) {
        cardsCtrl.onCheckboxClicked(1);
    }

    @NiftyEventSubscriber(id = "card2Checkbox")
    public void onCheckbox2Clicked(final String id, final CheckBoxStateChangedEvent event) {
        cardsCtrl.onCheckboxClicked(2);
    }

    @NiftyEventSubscriber(id = "card3Checkbox")
    public void onCheckbox3Clicked(final String id, final CheckBoxStateChangedEvent event) {
        cardsCtrl.onCheckboxClicked(3);
    }

    @NiftyEventSubscriber(id = "card4Checkbox")
    public void onCheckbox4Clicked(final String id, final CheckBoxStateChangedEvent event) {
        cardsCtrl.onCheckboxClicked(4);
    }

    public void cardClicked(String index) {
        cardsCtrl.onCardClick(Integer.parseInt(index));
    }

    public void showPendingArmiesMsg() {
        Player curr = b.getCurrentPlayer();
        setRavenMessage("\\#333333ff#" + curr.getName() + " ainda possui \\#CC0000#" + curr.getTotalPendingArmies() + "\\#333333ff# exército(s) para distribuir.");
    }

    public ContextMenuController getContextMenuController() {
        return ctxMenuCtrl;
    }
}
