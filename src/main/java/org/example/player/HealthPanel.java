package org.example.player;

import org.example.GameConfig;

import java.awt.*;
import javax.swing.JPanel;

public class HealthPanel extends JPanel {

    public int health;
    public void healthBar(Graphics g){

        g.setColor(Color.green);
        g.fillRect(20, GameConfig.SCREEN_HEIGHT-80, this.health, 20);
        g.setColor(Color.BLACK);
        g.drawRect(20, GameConfig.SCREEN_HEIGHT-80, 150, 20);

//        if (health == 0){
//            g.setColor(Color.BLACK);
//            g.setFont(new Font("italic",Font.BOLD, 20));
//            g.drawString("NEED REPAIR", GameConfig.SCREEN_HEIGHT-80, 20);
////
//        }

    }
    public void setHealth(int health) {
        this.health = health;
        repaint();

    }

    public void decreaseHealth() {
        if (health > 0) {
            this.health -= 5;
        }
    }

//    public void needRepair(){
//        g.setColor(Color.BLACK);
//        g.setFont(new Font("italic",Font.BOLD, 20));
//        g.drawString("SCORE " + score, 20, 20);
//
//
//    }

}

