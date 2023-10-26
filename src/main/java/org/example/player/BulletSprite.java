package org.example.player;

import java.awt.*;

public class BulletSprite extends Rectangle {
    public void draw(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect(x+50, y+15, width, height);

    }

    public void update(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
