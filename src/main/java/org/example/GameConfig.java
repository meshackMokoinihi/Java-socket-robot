package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {
    public static int SCREEN_WIDTH = 720;
    public static int SCREEN_HEIGHT = 640;
    public static int MAX_PLAYERS = 4;
    public static int PORT = 4545;
    public static String SERVER_IP_ADDRESS = "localhost";
    private static final String CONFIG_FILE = ".config/config.properties";
    private Properties properties;
//    public static Obstacles obstacles = new Obstacles();

    public GameConfig() {
        properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(CONFIG_FILE);
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Example usage
    public static void main(String[] args) {
        GameConfig config = new GameConfig();
        String windowTitle = config.getProperty("window.title");
        int playerHealth = Integer.parseInt(config.getProperty("player.health"));
        boolean soundEnabled = Boolean.parseBoolean(config.getProperty("sound.enabled"));

        System.out.println("Window Title: " + windowTitle);
        System.out.println("Player Health: " + playerHealth);
        System.out.println("Sound Enabled: " + soundEnabled);
    }
}
