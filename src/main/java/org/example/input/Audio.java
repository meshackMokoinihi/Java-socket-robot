package org.example.input;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {


    public static void playAudio(String filePath) {
        try {
            // Obtain an audio input stream from the specified file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            // Get the audio format of the audio input stream
            AudioFormat format = audioInputStream.getFormat();

            // Create a DataLine.Info object with the format
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // Obtain a Clip instance by opening the DataLine
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);

            // Start playing the audio clip
            clip.start();

            // Close the audio input stream when the clip has finished playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    try {
                        audioInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

