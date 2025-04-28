package eu.ensitech.projects.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;

public class AudioUtils {
    private static AudioInputStream getAudioStream(String name) {
    	String path = "/eu/ensitech/projects/assets/song/" + name + ".wav";
    	InputStream inputStream = AudioUtils.class.getResourceAsStream(path);
        File file = new File(path);
        if (inputStream == null)
            throw new IllegalArgumentException("File not found: " + path);

        try {
            return AudioSystem.getAudioInputStream(inputStream);
        } catch (UnsupportedAudioFileException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void playSound(String name) {
    	try {
            AudioInputStream audioInputStream = getAudioStream(name);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
