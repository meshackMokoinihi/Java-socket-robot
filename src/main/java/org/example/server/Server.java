/**

 The Server class represents a server that coordinates communication between two clients playing a game.
 The server listens for incoming connections, accepts two clients, and starts two threads to handle communication
 between the two clients. The server constantly updates the positions of the players and sends the updated positions
 to the clients.
 */

package org.example.server;

import com.google.gson.*;
import org.example.GameConfig;
import org.example.player.PlayerManager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket; // The ServerSocket object used to listen for incoming connections.
    private int numPlayers = 0; // The number of players currently connected to the server
    public static final int MAX_PLAYERS = GameConfig.MAX_PLAYERS; //The maximum number of players that can connect to the server.
    private final Socket[] playerSockets = new Socket[MAX_PLAYERS]; // An array of sockets representing the player connections.
    private final int PORT = GameConfig.PORT;
    /**
     * Constructs a new Server object and creates a ServerSocket to listen for incoming connections.
     */
    public Server() {
        System.out.println("===== GAME SERVER =====");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Started server on IP Address: " + GameConfig.SERVER_IP_ADDRESS + " and PORT: " + PORT);
        } catch (IOException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("IOException from Server");
        }
    }

    /**
     * Listens for incoming connections and accepts two players.
     * Starts two threads to handle communication between the players.
     */
    public void acceptConnections() {

        try {
            System.out.println("Waiting for player connections...");
            while (numPlayers < MAX_PLAYERS) {
                Socket socket = serverSocket.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                playerSockets[numPlayers] = socket;
                numPlayers++;

                String username = in.readUTF();
                PlayerManager.addPlayerUsername(numPlayers-1, username);
                System.out.println("Player #" + numPlayers + " (" + username + ") has connected.");

                out.writeInt(numPlayers);

                if (numPlayers == MAX_PLAYERS) {
                    for (int i = 0; i < MAX_PLAYERS; i++) {
                        // Start the threads for reading and writing to each player
                        DataInputStream playerIn = new DataInputStream(playerSockets[i].getInputStream());
                        DataOutputStream playerOut = new DataOutputStream(playerSockets[i].getOutputStream());

                        new Thread(() -> readFromClient(playerIn)).start();
                        new Thread(() -> writeToClients(playerOut)).start();
                    }
                    // Start a separate thread to handle user input
                    new Thread(this::handleUserInput).start();
                }

            }

            System.out.println("No longer accepting player connections");
        } catch (IOException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("IOException from acceptConnection() in Server");
        }
    }

    /**
     * Reads the position and orientation of a player from the given DataInputStream and updates the corresponding position
     * in the playerPositions array.
     *
     * @param in The DataInputStream to read from.
     */
    private void readFromClient(DataInputStream in) {
        JsonParser parser = new JsonParser();


        try {
            while (true) {
                int playerId = in.readInt();
                double x = in.readDouble();
                double y = in.readDouble();
                double angle = in.readDouble();
                int ammo = in.readInt();

                PlayerManager.updatePlayerPosition(playerId, x, y);
                PlayerManager.updatePlayerAngle(playerId, angle);
                PlayerManager.updatePlayerAmmo(playerId, ammo);

                if (in.readBoolean()) {
                    String playerHealths = PlayerManager.getPlayerHealths();
                    JsonArray healthsArray = parser.parse(playerHealths).getAsJsonArray();
                    int damagedPlayerID = in.readInt();
                    int currentPlayerHealth = healthsArray.get(damagedPlayerID).getAsJsonObject().get("health").getAsInt();
                    PlayerManager.updatePlayerHealth(damagedPlayerID, currentPlayerHealth - 15);
                }

            }
        } catch (IOException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("IOException from RFC run() in Server");
        }
    }


    /**
     * Writes data to a client through a DataOutputStream object.
     * It sends the position and orientation of the other player in the game to the client in a continuous loop.
     *
     * @param out The output stream to write to the client.
     */
    private void writeToClients(DataOutputStream out) {

        JsonParser parser = new JsonParser();

        try {
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("\n===== GAME SERVER =====");
                out.writeUTF("We now have " + numPlayers + " players. GO!");
            }
            while (true) {
                // Retrieve player positions, angles, and usernames from PlayerManager
                String playerPositionsJson = PlayerManager.getPlayerPositions();
                String playerAnglesJson = PlayerManager.getPlayerAngles();
                String playerHealth = PlayerManager.getPlayerHealths();
                String playerAmo = PlayerManager.getPlayerAmmo();


                // Parse the playerPositionsJson and playerAnglesJson
                JsonArray playerPositionsArray = parser.parse(playerPositionsJson).getAsJsonArray();
                JsonArray playerAnglesArray = parser.parse(playerAnglesJson).getAsJsonArray();
                JsonArray playerHealthArray = parser.parse(playerHealth).getAsJsonArray();
                JsonArray playerAmoArray = parser.parse(playerAmo).getAsJsonArray();

                for (int i = 0; i < playerPositionsArray.size(); i++) {
                    JsonElement playerPositionElement = playerPositionsArray.get(i);
                    JsonElement angleElement = playerAnglesArray.get(i);
                    JsonElement playerHealthElement = playerHealthArray.get(i);
                    JsonElement playerAmoElement = playerAmoArray.get(i);

                    if (playerPositionElement != null && angleElement != null) {
                        JsonObject playerPositionObj = playerPositionElement.getAsJsonObject();
                        JsonObject angleObj = angleElement.getAsJsonObject();
                        JsonObject playerHealthObject = playerHealthElement.getAsJsonObject();
                        JsonObject playerAmoObject = playerAmoElement.getAsJsonObject();


                        int playerId = playerPositionObj.get("playerId").getAsInt();

                        if (playerPositionObj.has("position") &&
                                !playerPositionObj.get("position").isJsonNull()) {

                            JsonObject positionObj = playerPositionObj.get("position").getAsJsonObject();
                            double x = positionObj.get("x").getAsDouble();
                            double y = positionObj.get("y").getAsDouble();
                            double angle = angleObj.get("angle").getAsDouble();
                            int health = playerHealthObject.get("health").getAsInt();
                            int ammo = playerAmoObject.get("ammo").getAsInt();

                            out.writeInt(playerId);
                            out.writeDouble(x);
                            out.writeDouble(y);
                            out.writeDouble(angle);
                            out.writeInt(health);
                            out.writeInt(ammo);
                        }
                    }
                }
                out.flush(); // Ensures that the data is sent immediately.
                Thread.sleep(25); // Prevents the loop from running too quickly and using up too much CPU time.

            }
        } catch (IOException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("IOException from WTC run() in Server");
        } catch (InterruptedException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("InterruptedException from WTC run() in Server");
        }
    }

    /**
     * Closes all connections with the connected clients and shuts down the server.
     */
    private void closeConnections() {
        try {
            for (int i = 0; i < numPlayers; i++) {
                playerSockets[i].close();
            }
            serverSocket.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("IOException from closeConnections() in Server");
        }
    }

    /**
     * Reads user input from the terminal
     */
    private void handleUserInput() {
        System.out.println("Server thread started!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        JsonParser parser = new JsonParser();
        try {
            while (true) {
                System.out.print("\nServer Command: ");
                String userInput = reader.readLine();
                if (userInput.equalsIgnoreCase("quit")) {
                    System.out.println("\n===== GAME SERVER =====");
                    System.out.println("Closing server and all connected clients...");
                    closeConnections();
                    break;
                } else if (userInput.equalsIgnoreCase("robots")) {
                    System.out.println("\n===== GAME SERVER =====");
                    System.out.println("Active Robots:");
                    String players = PlayerManager.getPlayerUsernames();
                    JsonArray playerDataArray = parser.parse(players).getAsJsonArray();

                    for (int i = 0; i < playerDataArray.size(); i++) {
                        JsonElement playerData = playerDataArray.get(i);
                        System.out.println(playerData);
                    }
                } else if (userInput.equalsIgnoreCase("dump")) {
                    System.out.println("\n===== GAME SERVER =====");
                    System.out.println("Dump:");

                    String players = PlayerManager.getPlayerUsernames();
                    String positions = PlayerManager.getPlayerPositions();
                    String angles = PlayerManager.getPlayerAngles();
                    String healths = PlayerManager.getPlayerHealths();
                    String ammo = PlayerManager.getPlayerAmmo();

                    JsonArray playersArray = parser.parse(players).getAsJsonArray();
                    JsonArray positionsArray = parser.parse(positions).getAsJsonArray();
                    JsonArray anglesArray = parser.parse(angles).getAsJsonArray();
                    JsonArray healthsArray = parser.parse(healths).getAsJsonArray();
                    JsonArray ammoArray = parser.parse(ammo).getAsJsonArray();

                    JsonArray outputArray = new JsonArray();

                    for (int i = 0; i < playersArray.size(); i++) {
                        JsonObject playerObject = new JsonObject();

                        JsonObject positionObject = positionsArray.get(i).getAsJsonObject();
                        JsonObject angleObject = anglesArray.get(i).getAsJsonObject();
                        JsonObject healthObject = healthsArray.get(i).getAsJsonObject();
                        JsonObject ammoObject = ammoArray.get(i).getAsJsonObject();

                        playerObject.addProperty("playerId", positionObject.get("playerId").getAsInt());
                        playerObject.addProperty("username", playersArray.get(i).getAsJsonObject().get("username").getAsString());
                        playerObject.addProperty("state", "Active");

                        JsonObject position = new JsonObject();
                        position.addProperty("x", positionObject.get("position").getAsJsonObject().get("x").getAsDouble());
                        position.addProperty("y", positionObject.get("position").getAsJsonObject().get("y").getAsDouble());
                        playerObject.add("position", position);

                        playerObject.addProperty("angle", angleObject.get("angle").getAsDouble());
                        playerObject.addProperty("health", healthObject.get("health").getAsInt());
                        playerObject.addProperty("ammo", ammoObject.get("ammo").getAsInt());

                        outputArray.add(playerObject);
                    }
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String outputJson = gson.toJson(outputArray);
                    System.out.println(outputJson);
                } else {
                    System.out.println("Server does not recognize that command");
                }

            }
        } catch (IOException e) {
            System.out.println("\n===== GAME SERVER =====");
            System.out.println("IOException from handleUserInput() in Server");
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.acceptConnections();
    }
}
