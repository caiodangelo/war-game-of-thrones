package main;

import java.util.HashMap;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
    
    private static AudioManager instance;
    private HashMap audioMap = new HashMap();
    private static Music opening;
    private static Sound startGame;
    private static Sound attackSound;
    private static Sound changeTurnSound;
    private float musicVolume;
    private boolean musicMuted;
    private float soundVolume;
    private boolean soundMuted;
    private Music currentMusic;
    
    public static final int OPENING = 0;
    public static final int START_GAME = 1;
    public static final int ATTACK_SOUND = 2;
    public static final int SWORD_TURN_SOUND = 3;
    
    public AudioManager() {
        try {
            opening = new Music("resources/sounds/got-opening.ogg");
            startGame = new Sound("resources/sounds/raio-start.ogg");
            attackSound = new Sound("resources/sounds/explosion.ogg");
            changeTurnSound = new Sound("resources/sounds/sword-turn.ogg");
        } catch (SlickException ex) {
            System.out.println(ex.getMessage());
        }
        this.musicVolume = 1;
        this.soundVolume = 1;
        audioMap.put(OPENING, opening);
        audioMap.put(START_GAME, startGame);
        audioMap.put(ATTACK_SOUND, attackSound);
        audioMap.put(SWORD_TURN_SOUND, changeTurnSound);
    }
    
    public static AudioManager getInstance(){
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }
    
    public void playMusic(int key) {
        Music m = (Music) audioMap.get(key);
        if (currentMusic != null)
            currentMusic.stop();
        if (musicMuted)
            m.loop(1, 0);
        else
            m.loop(1, musicVolume);
        currentMusic = m;
    }
    
    public void playSound(int key) {
        Sound s = (Sound) audioMap.get(key);
        if(!soundMuted)
            s.play(1, soundVolume);
    }
    
    public void stopMusic(int key) {
        Music m = (Music) audioMap.get(key);
        m.stop();
        currentMusic = null;
    }
    
    public float getMusicVolume() {
        return this.musicVolume;
    }
    
    public float getSoundVolume() {
        return this.soundVolume;
    }
    
    public void changeMusicVolume(float vol) {
        if (currentMusic != null)
            currentMusic.setVolume(vol);
        this.musicVolume = vol;
    }
    
    public void changeSoundVolume(float vol) {
        this.soundVolume = vol;
    }
    
    public void muteMusic() {
        musicMuted = true;
        if (currentMusic != null)
            currentMusic.setVolume(0);
    }
    
    public void muteSound() {
        soundMuted = true;
    }
    
    public void unmuteMusic() {
        musicMuted = false;
        currentMusic.setVolume(musicVolume);
    }
    
    public void unmuteSound() {
        soundMuted = false;
    }
    
    public boolean musicIsMuted() {
        return musicMuted;
    }
    
    public boolean soundIsMuted() {
        return soundMuted;
    }
    
}
