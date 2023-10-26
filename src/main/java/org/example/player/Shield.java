
package org.example.player;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


public class Shield {

    private double shield_x; // The x coordinate for the shield object
    private double shield_y; // The y coordinate for the shield object
    // private final double size; // Size of the shield
    private Color color; // Color of the shield
    private double durability; // The amout of damage the shiled can absorb
    private double cooldown_time; // The amount of time that must pass for the shield to be used again
    private int shieldStrength; // The maximum hits the shield can withstand
    private Rectangle2D shape;
    

    // Constructor for the shield class
    public Shield() {
        this.shield_x = shield_x;
        this.shield_y = shield_y;
        this.durability = durability;
        
        // this.size = size;
        this.color = color;
        this.shape = new Rectangle2D.Double(0, 0, 50, 50);
    }

    public void drawShield(Graphics2D g2d, double shield_x, double shield_y, double angle, double size) {
        
        // save current transform of the graphics object
        AffineTransform origin = g2d.getTransform();

        // this.size = size;
        // this.color = color;

        // Object of the sprite class that will give us access to the size of the robot

        //Calculate the position of the shield relative to the player's position and shape
        double shieldX = shield_x - size / 2;
        double shieldY = shield_x - size;

        // Translate the graphics object to the player's position
        g2d.translate(shieldX, shieldY);
        
        // Rotate the graphics object to face the correct direction
        g2d.rotate(angle);


        if( durability > 0.5 * shieldStrength) {
            g2d.setColor(color.GREEN);

        } else if (durability > 0.3 * shieldStrength) {
            g2d.setColor(color.ORANGE);
        } else {
            g2d.setColor(color.RED);

        }
        g2d.fill(new Rectangle2D.Double(-shield_x / 2, - shield_y, shieldX, shieldY));

    }

    // @param
    //    max_durability - maximum heatlh of the shield
    //     durability - the amount of damage the shield has absorbed
    // return:
    //     returns the current strength/health of the shield
    /*
     * Returns the current strength of the shield
     *  @param shield_stren
     */
    

    public double shieldStrength(int shieldStrength, double durability) {
        return durability / shieldStrength;
    }

    /*
     * Check if a robot's armor can absorb an attack of given damage.
     * 
     * @params damage number of hits absorbed per shoot
     * 
     * @returns True if armor survives, False if armor is breached. Assumes positive damage and durability.
     */
    public boolean damageAbsorbed(int damage) {
        
        while(durability > 0 && damage > 0) {
            damage --;
            durability --;

            if (damage == 0 && durability == 0){
                return true;
                // set robot status
                /*
                 * robot.setStatus("")
                 */
            } 
        }
        return false;

    }


}



