/**
 * The KeyInput class provides a way to handle keyboard input for movement keys (up, down, left, right).
 * It listens for key presses and releases and updates flags accordingly.
 */

package org.example.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    public boolean up, down, left, right, Repair, Reload;// Flags for movement keys

    /**
     * Handles key presses and updates flags for movement keys accordingly.
     *
     * @param e the KeyEvent object representing the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_R -> Reload = true;
            case KeyEvent.VK_Q -> Repair = true;

        }
    }


    /**
     * Handles key releases and updates flags for movement keys accordingly.
     *
     * @param e the KeyEvent object representing the key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_R -> Reload = false;
            case KeyEvent.VK_Q -> Repair = false;
        }
    }
}
