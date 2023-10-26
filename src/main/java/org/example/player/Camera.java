/**
 * The Camera class provides a way to follow and display a specific area of a game world.
 * It manages the position of the camera and applies the appropriate translation to graphics objects.
*/

package org.example.player;

import org.example.player.PlayerSprite;

import java.awt.Graphics2D;

public class Camera {
    private double x; //The x-coordinate of the camera position
    private double y; //The y-coordinate of the camera position
    private double targetX; //The target x-coordinate of the camera
    private double targetY; //The target y-coordinate of the camera
    private double speed; //The speed at which the camera moves

    /**
     * Creates a new Camera object with the specified speed.
     *
     * @param speed the speed at which the camera moves
     */
    public Camera(double speed) {
        this.speed = speed;
    }


    /**
     * Sets the camera's target position to follow the specified sprite.
     *
     * @param sprite the PlayerSprite object to follow
     */
    public void follow(PlayerSprite sprite) {
        targetX = sprite.getX();
        targetY = sprite.getY();
    }


    /**
     * Updates the camera's position based on its target position and speed.
     */
    public void update() {
        double dx = targetX - x;
        double dy = targetY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            double amount = Math.min(dist, speed);
            x += dx / dist * amount;
            y += dy / dist * amount;
        }
    }


    /**
     * Applies the camera's translation to the specified Graphics2D object.
     *
     * @param g2d the Graphics2D object to apply the camera's translation to
     */
    public void apply(Graphics2D g2d) {
        g2d.translate(-x + getWidth() / 2, -y + getHeight() / 2);
    }


    /**
     * Returns the width of the camera's view.
     *
     * @return the width of the camera's view
     */
    private int getWidth() {
        return 640;
    }

    /**
     * Returns the height of the camera's view.
     *
     * @return the height of the camera's view
     */
    private int getHeight() {
        return 480;
    }
}
