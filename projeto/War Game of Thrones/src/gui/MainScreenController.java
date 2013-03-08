package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.text.DecimalFormat;
import java.util.HashMap;
import main.AudioManager;
import main.Main;
import util.PopupManager;
public class MainScreenController implements ScreenController {
    
    private Nifty n;
    private Screen s;
    private Element exitConfirmPopup, helpPopup, optionsPopup;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.n = nifty;
        this.s = screen;
        exitConfirmPopup = n.createPopup("quitConfirmationPopup");
        helpPopup = n.createPopup("helpPopup");
        optionsPopup = n.createPopup("optionsPopup");
    }
    
    @Override
    public void onStartScreen() {    }

    @Override
    public void onEndScreen() {    }
    
    //Button click actions
    public void showAddPlayerMenu(){
        System.out.println("show add player menu");
        n.gotoScreen("addPlayer");
    }
    
    public void showOptions(){
        PopupManager.showPopup(n, s, optionsPopup);
    }
    
    public void closeOptions(){
        PopupManager.closePopup(n, optionsPopup);
    }
    
    public void showHelp(){
        PopupManager.showPopup(n, s, helpPopup);
    }
    
    public void closeHelpPopup(){
        PopupManager.closePopup(n, helpPopup);
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
    
    @NiftyEventSubscriber(id="sliderCPUMovementSpeed")
    public void onCPUDifficultySliderChange(final String id, final SliderChangedEvent event) {
        float selectedAIMovementsSpeed = event.getValue()/10f;
        Main.getInstance().setAIMapMovementsSpeed(1.1f - selectedAIMovementsSpeed);
        System.out.println(Main.getInstance().getAIMapMovementsSpeed());
        Label AIMovementsSpeedValue = optionsPopup.findNiftyControl("CPUmovementSpeedValue", Label.class);
        AIMovementsSpeedValue.setText((11 - (Math.round(selectedAIMovementsSpeed * 10)))+"");
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
    
    @NiftyEventSubscriber(id="showTerritories")
    public void onShowTerritoriesChange(final String id, final CheckBoxStateChangedEvent event) {
        main.Main.showTerritoriesNames(event.isChecked());
    }
    
    public void exit(){
        PopupManager.showPopup(n, s, exitConfirmPopup);
    }
    
    public void exitGame(){
        main.Main.getInstance().getGameContainer().exit();
    }
    
    public void dismissExitConfirmation(){
        PopupManager.closePopup(n, exitConfirmPopup);
    }
}
