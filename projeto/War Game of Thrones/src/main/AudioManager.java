package main;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
    
    private static AudioManager instance;
    private HashMap audioMap = new HashMap();
    private static Music opening;
    private static Sound startGame;
    private static Sound attack;
    private float musicVolume;
    private float soundVolume;
    private Music currentMusic;
    
    public static final int OPENING = 0;
    public static final int START_GAME = 1;
    public static final int ATTACK = 2;
    
    public AudioManager() {
        try {
            opening = new Music("resources/sounds/got-opening.ogg");
            startGame = new Sound("resources/sounds/start-game.ogg");
        } catch (SlickException ex) {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.musicVolume = 1;
        this.soundVolume = 1;
        audioMap.put(OPENING, opening);
        audioMap.put(START_GAME, startGame);
        audioMap.put(ATTACK, attack);
    }
    
    public static AudioManager getInstance(){
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }
    
    public void playSound(int key) {
        Sound s = (Sound) audioMap.get(key);
        s.play(1, soundVolume);
    }
    
    public void playMusic(int key) {
        Music m = (Music) audioMap.get(key);
        if (currentMusic != null)
            currentMusic.stop();
        m.loop(1, musicVolume);
        currentMusic = m;
    }
    
    public void stopMusic(int key) {
        Music m = (Music) audioMap.get(key);
        m.stop();
        currentMusic = null;
    }
    
    public void changeMusicVolume(float vol) {
        currentMusic.setVolume(vol);
        this.musicVolume = vol;
    }
    
    public void changeSoundVolume(float vol) {
        this.soundVolume = vol;
    }
    
}
