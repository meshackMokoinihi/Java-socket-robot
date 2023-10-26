package org.example.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.example.player.PlayerSprite;

public class PlayerSpriteTest {
    @Test
    public void testRotatePlayer() {
        PlayerSprite player = new PlayerSprite(0, 0, 50, Color.RED);
        int mouseX = 100;
        int mouseY = 100;

        player.rotatePlayer(mouseX, mouseY);

        double expectedTargetAngle = 45.0;
        double actualTargetAngle = player.targetAngle;

        assertEquals(expectedTargetAngle, actualTargetAngle, 0.01);
    }

    @Test
    public void testUpdate() {
        PlayerSprite player = new PlayerSprite(0, 0, 50, Color.RED);
        player.angle = 0;
        player.targetAngle = 90;

        player.update();

        double expectedAngle = 4.5;  // Calculated based on the rotation speed
        double actualAngle = player.angle;

        assertEquals(expectedAngle, actualAngle, 0.01);
    }

    @Test
    public void testSetAndGetHealth() {
        PlayerSprite player = new PlayerSprite(0, 0, 50, Color.RED);
        int expectedHealth = 75;

        player.setHealth(expectedHealth);
        int actualHealth = player.getHealth();

        assertEquals(expectedHealth, actualHealth);
    }
    
}
