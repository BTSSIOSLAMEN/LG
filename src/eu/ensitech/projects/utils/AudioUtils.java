package eu.ensitech.projects.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class AudioUtils {
    private static AudioInputStream getAudioStream(String path) {
        File file = new File(path);
        if (!file.exists())
            throw new InvalidPathException(path, "File not found");

        try {
            return AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void playSound(String name) {
        AudioInputStream audioInputStream = getAudioStream("assets/" + name + ".wav");
        if (audioInputStream == null)
            return;

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
