package org.example.player;


import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ScoreCounter extends JPanel {
    int score = 0;

    public void Score(Graphics g){

        g.setColor(Color.BLACK);
        g.setFont(new Font("italic",Font.BOLD, 20));
        g.drawString("SCORE " + score, 20, 20);

    }


    public int getScore() {
        return score ++;
    }

//    public void setScore(int score) {
//        this.score = score;
//    }
}
