package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import main.AudioManager;
import util.PopupManager;
public class MainScreenController implements ScreenController {

    private Nifty n;
    private Screen s;
    private Element exitConfirmPopup, helpPopup, optionsPopup;
    private HashMap<Integer, String> difficulties;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.n = nifty;
        this.s = screen;
        exitConfirmPopup = n.createPopup("quitConfirmationPopup");
        helpPopup = n.createPopup("helpPopup");
        optionsPopup = n.createPopup("optionsPopup");
        this.difficulties = new HashMap();
        difficulties.put(1, "Fácil");
        difficulties.put(2, "Médio");
        difficulties.put(3, "Difícil");
    }

    @Override
    public void onStartScreen() {    }

    @Override
    public void onEndScreen() {    }
    
    //Button click actions
    public void showAddPlayerMenu(){
        n.gotoScreen("addPlayer");
    }
    
    public void showOptions(){
        PopupManager.showPopup(n, s, optionsPopup);
//        n.showPopup(s, optionsPopup.getId(), null);
    }
    
    public void closeOptions(){
        PopupManager.closePopup(n, optionsPopup);
//        n.closePopup(optionsPopup.getId());
    }
    
    public void showHelp(){
        PopupManager.showPopup(n, s, helpPopup);
//        n.showPopup(s, helpPopup.getId(), null);
    }
    
    public void closeHelpPopup(){
        PopupManager.closePopup(n, helpPopup);
//        n.closePopup(helpPopup.getId());
    }
    
    @NiftyEventSubscriber(id="musicSlider")
    public void onMusicSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager.getInstance().changeMusicVolume(newVolume);
        Label musicVolumeValue = optionsPopup.findNiftyControl("musicVolumeValue", Label.class);
        musicVolumeValue.setText(((int) (newVolume*100))+"");
    }
    
    @NiftyEventSubscriber(id="soundSlider")
    public void onSoundSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager.getInstance().changeSoundVolume(newVolume);
        Label soundVolumeValue = optionsPopup.findNiftyControl("soundVolumeValue", Label.class);
        soundVolumeValue.setText(((int) (newVolume*100))+"");
    }
    
    @NiftyEventSubscriber(id="sliderCPUdifficulty")
    public void onCPUDifficultySliderChange(final String id, final SliderChangedEvent event) {
        event.getValue();
        Label CPUDifficultyValue = optionsPopup.findNiftyControl("CPUdifficultyValue", Label.class);
        CPUDifficultyValue.setText(difficulties.get((int) event.getValue()));
    }
    
    @NiftyEventSubscriber(id="musicMute")
    public void onMusicMuteChange(final String id, final CheckBoxStateChangedEvent event) {
        AudioManager am = AudioManager.getInstance();
        if (event.isChecked())
            am.muteMusic();
        else
            am.unmuteMusic();
    }
    
    @NiftyEventSubscriber(id="soundMute")
    public void onSoundMuteChange(final String id, final CheckBoxStateChangedEvent event) {
        AudioManager am = AudioManager.getInstance();
        if (event.isChecked())
            am.muteSound();
        else
            am.unmuteSound();
    }
    
    public void exit(){
        PopupManager.showPopup(n, s, exitConfirmPopup);
//        n.showPopup(s, exitConfirmPopup.getId(), null);
    }
    
    public void exitGame(){
        main.Main.getInstance().getGameContainer().exit();
    }
    
    public void dismissExitConfirmation(){
        PopupManager.closePopup(n, exitConfirmPopup);
//        n.closePopup(exitConfirmPopup.getId());
    }
}
