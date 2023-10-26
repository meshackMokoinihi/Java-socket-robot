package org.example.test;

import org.example.input.KeyInput;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyInputTest {
    
    @Test
    public void testKeyPressedUp() {
        KeyInput keyInput = new KeyInput();
        JPanel source = new JPanel(); // Create a JPanel as the event source
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP);

        keyInput.keyPressed(event);

        assertTrue(keyInput.up);
    }

    @Test
    public void testKeyPressedDown() {
        KeyInput keyInput = new KeyInput();
        JPanel source = new JPanel(); // Create a JPanel as the event source
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);

        keyInput.keyPressed(event);

        assertTrue(keyInput.down);
    
    }

    @Test
    public void testKeyPressedLeft() {
        KeyInput keyInput = new KeyInput();
        JPanel source = new JPanel(); // Create a JPanel as the event source
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT);

        keyInput.keyPressed(event);

        assertTrue(keyInput.left);
    
    }

    @Test
    public void testKeyPressedRight() {
        KeyInput keyInput = new KeyInput();
        JPanel source = new JPanel(); // Create a JPanel as the event source
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);

        keyInput.keyPressed(event);

        assertTrue(keyInput.right);
    
    
    }


    @Test
    public void testKeyReleasedUp() {
        KeyInput keyInput = new KeyInput();
        JPanel source = new JPanel(); // Create a JPanel as the event source
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP);

        keyInput.keyReleased(event);
    }

    @Test
    public void testKeyReleasedDown() {
        KeyInput keyInput = new KeyInput();
        JPanel source = new JPanel(); // Create a JPanel as the event source
        KeyEvent event = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);

        keyInput.keyReleased(event);
    
    }
    
}
