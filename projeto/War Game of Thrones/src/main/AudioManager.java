package main;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
    
    private static AudioManager instance;
    private HashMap hm = new HashMap();
    private static Music opening;
    private static Sound startGame;
    private static Sound attack;
    private float volume;
    
    public static final int OPENING = 0;
    public static final int START_GAME = 1;
    public static final int ATTACK = 2;
    
    public AudioManager() {
        try {
            opening = new Music("resources/got-opening.ogg");
            startGame = new Sound("resources/start-game.ogg");
        } catch (SlickException ex) {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.volume = 1;
        hm.put(OPENING, opening);
        hm.put(START_GAME, startGame);
        hm.put(ATTACK, attack);
    }
    
    public static AudioManager getInstance(){
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }
    
    public void playSound(int key) {
        Sound s = (Sound) hm.get(key);
        s.play();
    }
    
    public void playMusic(int key) {
        Music m = (Music) hm.get(key);
        m.loop();
        m.setVolume(volume);
    }
    
    public void stopMusic(int key) {
        Music m = (Music) hm.get(key);
        m.stop();
    }
    
    public void changeMusicVolume(int key, float vol) {
        Music m = (Music) hm.get(key);
        m.setVolume(vol);
    }
    
}
