/**
 * The PlayerManager class is responsible for managing player positions and angles.
 * It provides methods for updating player positions and angles, as well as retrieving the current
 positions and angles of all players.
 */

package org.example.player;

import org.example.GameConfig;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class PlayerManager {

    private static final int MAX_PLAYERS = GameConfig.MAX_PLAYERS; // The maximum number of players in the game.

    /**
     * A list that stores the player information.
     */
    private static final List<Player> players = new ArrayList<>();

    /**
     * Updates the position of a player.
     *
     * @param playerId the ID of the player whose position is being updated.
     * @param x        the new x coordinate of the player's position.
     * @param y        the new y coordinate of the player's position.
     */
    public static synchronized void updatePlayerPosition(int playerId, double x, double y) {
        Player player = getPlayer(playerId);
        if (player == null) {
            player = new Player(playerId);
            players.add(player);
        }
        player.setPosition(new Position(x, y));
    }

    /**
     * Updates the angle of a player.
     *
     * @param playerId the ID of the player whose angle is being updated.
     * @param angle    the new angle of the player.
     */
    public static synchronized void updatePlayerAngle(int playerId, double angle) {
        Player player = getPlayer(playerId);
        if (player == null) {
            player = new Player(playerId);
            players.add(player);
        }
        player.setAngle(angle);
    }



    public static synchronized void addPlayerUsername(int playerId, String username) {
        Player player = getPlayer(playerId);
        if (player == null) {
            player = new Player(playerId);
            players.add(player);
        }
        player.setUsername(username);
    }

    public static synchronized void updatePlayerHealth(int playerId, int health){
        Player player = getPlayer(playerId);
        if (player == null) {
            player = new Player(playerId);
            players.add(player);
        }
        player.setHealth(health);

    }

    public static synchronized void updatePlayerAmmo(int playerId, int ammo){

        Player player = getPlayer(playerId);
        if (player == null) {
            player = new Player(playerId);
            players.add(player);
        }
        player.setAmmo(ammo);
    }

    private static Player getPlayer(int playerId) {
        for (Player player : players) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        return null;
    }

    /**
     * Retrieves the positions of all players.
     * @return a JSON representation of the positions of all players.
     */
    public static synchronized String getPlayerPositions() {
        List<PlayerPosition> playerPositions = new ArrayList<>();
        for (Player player : players) {
            playerPositions.add(new PlayerPosition(player.getId(), player.getPosition()));
        }
        return new Gson().toJson(playerPositions);
    }

    /**
     * Retrieves the angles of all players.
     *
     * @return a JSON representation of the angles of all players.
     */
    public static synchronized String getPlayerAngles() {
        List<PlayerAngle> playerAngles = new ArrayList<>();
        for (Player player : players) {
            playerAngles.add(new PlayerAngle(player.getId(), player.getAngle()));
        }
        return new Gson().toJson(playerAngles);
    }

    public static synchronized String getAmmo(){
        List<PlayerAmmo> playerAmmo = new ArrayList<>();
        for (Player player : players) {
            playerAmmo.add(new PlayerAmmo(player.getId(), player.getAmmo()));
        }
        return new Gson().toJson(playerAmmo);
    }

    public static synchronized String getPlayerUsernames() {
        List<PlayerUsername> playerUsernames = new ArrayList<>();
        for (Player player : players) {
            playerUsernames.add(new PlayerUsername(player.getId(), player.getUsername()));
        }
        return new Gson().toJson(playerUsernames);
    }

    public static synchronized String getPlayerHealths() {
        List<PlayerHealth> playerHealths = new ArrayList<>();
        for (Player player : players) {
            playerHealths.add(new PlayerHealth(player.getId(), player.getHealth()));
        }
        return new Gson().toJson(playerHealths);
    }

    public static synchronized String getPlayerAmmo() {
        List<PlayerAmmo> playerAmmo = new ArrayList<>();
        for (Player player : players) {
            playerAmmo.add(new PlayerAmmo(player.getId(), player.getAmmo()));
        }
        return new Gson().toJson(playerAmmo);
    }

    private static class Player {
        private int id;
        private Position position;
        private double angle;
        private String username;
        private int health;
        private int ammo;

        public Player(int id) {
            this.id = id;
            this.health = 100;
            this.ammo = 30;
        }
        public int getId() {
            return id;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setHealth(int health) {this.health = health;}

        public int getHealth() {
            return health;
        }

        public void setAmmo(int ammo) {this.ammo = ammo;}

        public int getAmmo() {
            return ammo;
        }
    }

    private static class PlayerPosition {
        private int playerId;
        private Position position;

        public PlayerPosition(int playerId, Position position) {
            this.playerId = playerId;
            this.position = position;
        }

        public int getPlayerId() {
            return playerId;
        }

        public Position getPosition() {
            return position;
        }
    }

    private static class PlayerAngle {
        private int playerId;
        private double angle;

        public PlayerAngle(int playerId, double angle) {
            this.playerId = playerId;
            this.angle = angle;
        }

        public int getPlayerId() {
            return playerId;
        }

        public double getAngle() {
            return angle;
        }
    }

    private static class PlayerUsername {
        private int playerId;
        private String username;

        public PlayerUsername(int playerId, String username) {
            this.playerId = playerId;
            this.username = username;
        }

        public int getPlayerId() {
            return playerId;
        }

        public String getUsername() {
            return username;
        }
    }

    private static class PlayerHealth {
        private int playerId;
        private int health;

        public PlayerHealth(int playerId, int health) {
            this.playerId = playerId;
            this.health = health;
        }
    }

    private static class PlayerAmmo {
        private int playerId;
        private int ammo;

        public PlayerAmmo(int playerId, int ammo) {
            this.playerId = playerId;
            this.ammo = ammo;
        }
    }
}
