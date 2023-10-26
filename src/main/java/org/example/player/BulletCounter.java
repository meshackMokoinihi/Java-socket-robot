package org.example.player;

import java.awt.*;

import javax.swing.*;
import org.example.GameConfig;

public class BulletCounter extends JPanel {
    int bullet;

    public void bullet(Graphics g){

        g.setColor(Color.BLACK);
        g.setFont(new Font("italic",Font.BOLD, 20));
        g.drawString("Amo: "+ bullet , GameConfig.SCREEN_HEIGHT-80, 580);

    }

    public void setBullet(int bullet) {
        this.bullet = bullet;
        repaint();
    }

    public void getBullet() {
        bullet--;
    }
}
