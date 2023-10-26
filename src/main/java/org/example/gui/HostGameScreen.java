package org.example.gui;

import org.example.GameConfig;
import org.example.client.Client;
import org.example.server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostGameScreen {
    private JPanel Panel;
    private JLabel Title;
    private JTextField PortField;
    private JButton ExitBtn;
    private JLabel PortText;
    private JTextField IPAddress;
    private JLabel IPText;
    private JLabel Type;
    private JButton StartGameBtn;
    private JRadioButton fourNumPlayerBtn;
    private JLabel NumPlayersText;
    private JRadioButton threeNumPlayerBtn;
    private JRadioButton twoNumPlayerBtn;
    static JFrame frame = new JFrame("Robot Worlds");

    public HostGameScreen() {
        ExitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        StartGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig.PORT = Integer.parseInt(PortField.getText());
                GameConfig.SERVER_IP_ADDRESS = IPAddress.getText();
                // Start the server in a separate thread
                frame.dispose();
                new Thread(() -> {
                    Server.main(null);
                }).start();

                // Start the client in a separate thread
                new Thread(() -> {
                    Client.main(null);
                }).start();
            }
        });
        twoNumPlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig.MAX_PLAYERS = 2;
            }
        });
        threeNumPlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig.MAX_PLAYERS = 3;
            }
        });
        fourNumPlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig.MAX_PLAYERS = 4;
            }
        });
    }

    public static void main() {
        frame.setContentPane(new HostGameScreen().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(new Dimension(500, 500));
        frame.pack();
        frame.setResizable(false); // Make the window not resizable
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}
