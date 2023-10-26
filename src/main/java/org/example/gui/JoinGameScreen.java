package org.example.gui;

import org.example.GameConfig;
import org.example.client.Client;
import org.example.server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGameScreen {
    private JPanel Panel;
    private JLabel Title;
    private JTextField PortField;
    private JButton ExitBtn;
    private JTextField IPAddress;
    private JLabel PortText;
    private JLabel IPText;
    private JLabel Type;
    private JButton JoinGameBtn;
    private JTextField numPlayersField;
    private JLabel ConfirmText;
    static JFrame frame = new JFrame("Robot Worlds");

    public JoinGameScreen() {
        ExitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JoinGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameConfig.PORT = Integer.parseInt(PortField.getText());
                GameConfig.SERVER_IP_ADDRESS = IPAddress.getText();
                GameConfig.MAX_PLAYERS = Integer.parseInt(numPlayersField.getText());

                frame.dispose();
                new Thread(() -> {
                    Client.main(null);
                }).start();
            }
        });
    }

    public static void main() {
        frame.setContentPane(new JoinGameScreen().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false); // Make the window not resizable
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}
