package org.example.test;

import static org.junit.Assert.assertTrue;

import org.example.player.PlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class PlayerManagerTest {
    @BeforeEach
    public void setUp() {
        PlayerManager.updatePlayerPosition(1, 100, 100);
        PlayerManager.updatePlayerPosition(2, 200, 200);
        PlayerManager.updatePlayerAngle(1, 45);
        PlayerManager.updatePlayerAngle(2, 90);
        PlayerManager.addPlayerUsername(1, "Player1");
        PlayerManager.addPlayerUsername(2, "Player2");
        PlayerManager.updatePlayerHealth(1, 100);
        PlayerManager.updatePlayerHealth(2, 50);
        PlayerManager.updatePlayerAmmo(1, 30);
        PlayerManager.updatePlayerAmmo(2, 20);
    }

    @Test
    public void testUpdatePlayerPosition() {
        PlayerManager.updatePlayerPosition(1, 150, 150);
        String positions = PlayerManager.getPlayerPositions();
        assertTrue(positions.contains("\"playerId\":1,\"position\":{\"x\":150.0,\"y\":150.0}"));
    }

    @Test
    public void testUpdatePlayerAngle() {
        PlayerManager.updatePlayerAngle(1, 60);
        String angles = PlayerManager.getPlayerAngles();
        assertTrue(angles.contains("\"playerId\":1,\"angle\":60.0"));
    }

    @Test
    public void testAddPlayerUsername() {
        PlayerManager.addPlayerUsername(3, "Player3");
        String usernames = PlayerManager.getPlayerUsernames();
        assertTrue(usernames.contains("\"playerId\":3,\"username\":\"Player3\""));
    }

    @Test
    public void testUpdatePlayerHealth() {
        PlayerManager.updatePlayerHealth(1, 80);
        String healths = PlayerManager.getPlayerHealths();
        assertTrue(healths.contains("\"playerId\":1,\"health\":80"));
    }

    @Test
    public void testUpdatePlayerAmmo() {
        PlayerManager.updatePlayerAmmo(1, 20);
        String ammo = PlayerManager.getPlayerAmmo();
        assertTrue(ammo.contains("\"playerId\":1,\"ammo\":20"));
    }
}
