package gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import main.AudioManager;
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
        n.showPopup(s, optionsPopup.getId(), null);
    }
    
    public void closeOptions(){
        n.closePopup(optionsPopup.getId());
    }
    
    public void showHelp(){
        n.showPopup(s, helpPopup.getId(), null);
    }
    
    public void closeHelpPopup(){
        n.closePopup(helpPopup.getId());
    }
    
    @NiftyEventSubscriber(id="musicSlider")
    public void onMusicSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager am = AudioManager.getInstance();
        am.changeMusicVolume(newVolume);
        Label musicVolumeValue = optionsPopup.findNiftyControl("musicVolumeValue", Label.class);
        musicVolumeValue.setText(((int) (newVolume*100))+"");
    }
    
    @NiftyEventSubscriber(id="soundSlider")
    public void onSoundSliderChange(final String id, final SliderChangedEvent event) {
        float newVolume = 1 - event.getValue(); //Nifty uses inverted sliders
        AudioManager am = AudioManager.getInstance();
        am.changeSoundVolume(newVolume);
        Label soundVolumeValue = optionsPopup.findNiftyControl("soundVolumeValue", Label.class);
        soundVolumeValue.setText(((int) (newVolume*100))+"");
    }
    
    @NiftyEventSubscriber(id="sliderCPUdifficulty")
    public void onCPUDifficultySliderChange(final String id, final SliderChangedEvent event) {
        event.getValue();
        Label CPUDifficultyValue = optionsPopup.findNiftyControl("CPUdifficultyValue", Label.class);
        CPUDifficultyValue.setText(difficulties.get((int) event.getValue()));
    }
    
    public void exit(){
        n.showPopup(s, exitConfirmPopup.getId(), null);
    }
    
    public void exitGame(){
        main.Main.getInstance().getGameContainer().exit();
    }
    
    public void dismissExitConfirmation(){
        n.closePopup(exitConfirmPopup.getId());
    }
}
