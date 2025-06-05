package view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private Map<String, Clip> clips = new HashMap<>();


    public void loadSound(String key, String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clips.put(key, clip);
    }

    public void play(String key) {
        Clip clip = clips.get(key);
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playOneTime(String key){
        Clip clip = clips.get(key);
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.loop(0);
        }
    }
    public void playBgm(String key){
        Clip clip = clips.get(key);
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


    public void stop(String key) {
        Clip clip = clips.get(key);
        if (clip != null && clip.isRunning()) {
            clip.stop(); // 停止指定音效
        }
    }

    public void closeAll() {
        for (Clip clip : clips.values()) {
            if (clip != null && clip.isOpen()) {
                clip.close(); // 关闭所有音效
            }
        }
    }




}
