package org.example;

import org.example.gui.WelcomeScreen;
import org.example.input.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        WelcomeScreen.main(null);
        String audioFilePath = "src/main/java/org/example/assets/Clown.wav";

        while (true) {
            File audioFile = new File(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            // Create a gain control object
            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);

            // Increase the volume by 6 dB (adjust the value as needed)
            gainControl.setValue(gainControl.getValue() - 4.5f);

            audioClip.start();
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Sleep to allow the audio to play
            Thread.sleep(180000); // Adjust the sleep time as needed

            // Stop the audio and clean up resources
            audioClip.stop();
            audioClip.close();
            audioStream.close();
        }
    }
}
