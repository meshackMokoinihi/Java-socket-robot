package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen {

    private JPanel Panel;
    private JLabel Title;
    private JButton JoinBtn;
    private JButton HostBtn;
    private JButton ExitBtn;
    static JFrame frame = new JFrame("Robot Worlds");

    public WelcomeScreen() {
        ExitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        HostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HostGameScreen.main();
                frame.dispose();
            }
        });
        JoinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JoinGameScreen.main();
                frame.dispose();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        frame.setContentPane(new WelcomeScreen().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false); // Make the window not resizable
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);

    }
}
