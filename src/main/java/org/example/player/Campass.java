package org.example.player;


import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Campass extends JPanel {

    private int angle;

    public Campass() {
        this.angle = 0;
//        setPreferredSize(new Dimension(20, 20));
    }


    public void setAngle(int angle) {
        this.angle = angle;
        repaint();
    }

//    @Override
public void draw(Graphics g, int radius) {
    int centerX = 630;
    int centerY = 50;

    Graphics2D g2d = (Graphics2D) g;

    // Draw the outer circle
    g2d.setColor(Color.BLACK);
    g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

    // Draw the compass needle
    double needleAngle = Math.toRadians(angle);
    int needleX = centerX + (int) (radius * Math.cos(needleAngle));
    int needleY = centerY - (int) (radius * Math.sin(needleAngle));

    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(2));
    g2d.drawLine(centerX, centerY, needleX, needleY);

    // Draw the labels
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 12));
    g2d.drawString("N", centerX - 5, centerY - radius + 15);
    g2d.drawString("S", centerX - 5, centerY + radius - 5);
    g2d.drawString("E", centerX + radius - 15, centerY + 5);
    g2d.drawString("W", centerX - radius + 5, centerY + 5);
}

}
