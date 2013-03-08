package main;

import java.util.HashMap;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioManager {
    
    private static AudioManager instance;
    private HashMap audioMap = new HashMap();
    private float musicVolume;
    private boolean musicMuted;
    private float soundVolume;
    private boolean soundMuted;
    private Music currentMusic;
    
    public static final int OPENING = 0;
    public static final int START_GAME = 1;
    public static final int ATTACK_SOUND = 2;
    public static final int SWORD_TURN_SOUND = 3;
    public static final int GAME_RUNNING = 4;
    public static final int FIREWORKS = 5;
    
    public AudioManager() {
        try {
            audioMap.put(OPENING, new Music("resources/sounds/got-opening.ogg"));
            audioMap.put(START_GAME, new Sound("resources/sounds/raio-start.ogg"));
            audioMap.put(ATTACK_SOUND, new Sound("resources/sounds/explosion.ogg"));
            audioMap.put(SWORD_TURN_SOUND, new Sound("resources/sounds/sword-turn.ogg"));
            audioMap.put(GAME_RUNNING, new Music("resources/sounds/game_running.ogg"));
            audioMap.put(FIREWORKS, new Music("resources/sounds/fireworks.ogg"));
        } catch (SlickException ex) {
            System.out.println(ex.getMessage());
        }
        this.musicVolume = 1;
        this.soundVolume = 1;
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
    
    public void stopCurrentMusic() {
        currentMusic.stop();
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
