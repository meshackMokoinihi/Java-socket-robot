package org.example.player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class PlayerSprite extends JPanel {
    public double x; // The x-coordinate of the sprite's top-left corner
    public double y; // The y-coordinate of the sprite's top-left corner
    public double A;
    public double B;

    public final double size; // The size of the sprite
    private final Color color; // The color of the sprite
    public double angle; // The angle of the player
    public double targetAngle; // The target angle of the player


    private int health;
    private int ammo;

    public Rectangle playerRect;


    /**
     * Constructs a PlayerSprite object with the specified coordinates, size, and color.
     * @param x the x-coordinate of the sprite's top-left corner
     * @param y the y-coordinate of the sprite's top-left corner
     * @param size the size of the sprite
     * @param color the color of the sprite
     */


    public PlayerSprite(double x, double y, double size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.B = (int) x;
        this.A = (int) y;
        this.health = 100;
        this.ammo = 30;
    }


    /**
     * Draws the sprite using the specified Graphics2D object.
     * @param g the Graphics2D object used to draw the sprite
     */
    public void drawSprite(Graphics2D g) {

        // Translate to the center of the rectangle
        g.translate(x + size / 2, y + size / 2);
        // Rotate the graphics context by the angle
        g.rotate(Math.toRadians(angle));

        // Draw the rectangle at the origin using Rectangle2D.Double
        playerRect = new Rectangle((int) (-size / 2), (int) (-size / 2), (int) size, (int) size);
        g.setColor(color);
        g.draw(playerRect);
        g.fill(playerRect);

        // Draw the weapon rectangle at the origin, but move it to the right of the player using Rectangle2D.Double
        int weaponWidth = 30, weaponHeight = 15;
        Rectangle2D.Double weaponRect = new Rectangle2D.Double(size / 2, (double) -weaponHeight / 2, weaponWidth, weaponHeight);
        g.setColor(Color.BLACK);
        g.draw(weaponRect);
        g.fill(weaponRect);

        g.setColor(Color.BLACK);
        g.getFontRenderContext();


        // Reset the transform
        g.rotate(-Math.toRadians(angle));
        g.translate(-(x + size / 2), -(y + size / 2));

    }

    /**
     * Rotates the player towards the given mouse position
     * @param mouseX The x-coordinate of the mouse position
     * @param mouseY The y-coordinate of the mouse position
     */
    public void rotatePlayer(int mouseX, int mouseY) {
        // Calculate the delta x and delta y of the mouse movement
        double dx = mouseX - (x + size / 2);
        double dy = mouseY - (y + size / 2);

        // Calculate the angle between the mouse position and the rectangle center
        targetAngle = Math.toDegrees(Math.atan2(dy, dx));
    }



    /**
     * Updates the player's angle towards the target angle. (Linear equation)
     * The method first calculates the difference between the target angle and the current angle,
     * taking into account the angle wrap around (due to the angle being normalized between -180
     * and 180 degrees). The method then rotates the current angle towards the target angle based
     * on the rotationSpeed. Finally, the method normalizes the angle to be between -180 and 180 degrees.
     */
    public void update() {
        // gradually rotate the current angle towards the target angle
        double angleDiff = targetAngle - angle;
        if (angleDiff > 180) {
            angleDiff -= 360;
        } else if (angleDiff < -180) {
            angleDiff += 360;
        }

        // The speed of rotation in radians per frame
        double rotationSpeed = 0.05;
        angle += angleDiff * rotationSpeed;

        // normalize the angle to be between 0 and 360 degrees
        if (angle < 0) {
            angle += 360;
        } else if (angle >= 360) {
            angle -= 360;
        }
    }



    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth(){
        return  this.health;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public int getAmmo(){
        return this.ammo;
    }

    /**
     * Sets the x-coordinate of the sprite's top-left corner to the specified value.
     * @param x the value to set the x-coordinate to.
     */

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the sprite's top-left corner.
     *
     * @return the x-coordinate of the sprite's top-left corner.
     */
    public int getX() {
        return (int) this.x;
    }


    /**
     * Returns the y-coordinate of the sprite's top-left corner.
     *
     * @return the y-coordinate of the sprite's top-left corner.
     */
    public int getY() {
        return (int) this.y;
    }

    /**
     * Sets the angle value of a sprite.
     * @param n a double representing the new angle value to be set.
     */
    public void setAngle(double n) {angle = n;}



    public void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

    /**
     * Returns the angle of the sprite.
     * @return the angle of the sprite.
     */
    public double getAngle() {return this.angle;}

}
