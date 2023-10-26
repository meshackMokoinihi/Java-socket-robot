package org.example.input;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import java.awt.event.*;

public class MouseInput extends JPanel implements ActionListener, MouseListener {

    public int bulletX;
    public int bulletY;
    private Timer timer;

    public int getBulletY() {
        return bulletY;
    }

    public boolean readyToFire;
    public boolean readyToRepair;
    boolean Reload;
    public  boolean shot = false;
    public Rectangle bullet;
    public int bulletCount = 10;
    public double B;
    public double A;
    public boolean click;
//    private final Color color; // The color of the sprite

    int by, bx;

    public void makeBullet(Graphics2D g,int by, int bx) {

        g.setColor(Color.red);
        g.fillOval((int) bx+ 300, by, 10, 10);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
         shot= true;
//        if (bullet == null)
//            readyToFire = true;
//
//
//        if (readyToFire) {
//            bulletX = (int) B;
//            bulletY = (int) A;
//            bullet = new Rectangle(bulletX, bulletY, 8, 10);
//            shot = true;
//        }
//
//
//        if (bulletCount == 0) {
//            System.out.println("Reload");
//            readyToFire = false;
//            Reload = true;
//        }
//
//        if (!Reload) {
//            readyToFire = true;
//        }
//
//        if (readyToRepair) {
//            readyToFire = false;
//        }

    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        click = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
//
//    public void shoot(double speed) {
//
//        if (shot) {
//            bulletX += (int) speed;
//
//
//        }
//    }





}
