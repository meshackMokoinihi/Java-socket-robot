/**
    The Client class creates a client-side program to connect to a server and communicate with it.
    The client sends the player's coordinates and angle to the
    server every 25ms and receives the other player's coordinates and angle from the server.
    The class contains methods to connect to the server, set up the graphical user interface, and
    create Runnable objects to read and write to the server.
*/

package org.example.client;

import javax.swing.*;

import org.example.GameConfig;
import org.example.gui.GameGUI;
import org.example.player.PlayerSprite;

import java.io.*;
import java.net.*;

public class Client {

    public Socket socket; // The socket used to communicate with the server
    public int playerID; // The ID of the player controlled by this client
    public int idxPlayerID;
    public ReadFromServer rfsRunnable; // The runnable ble used to read data from the server
    public WriteToServer wtsRunnable; // The runnable used to write data to the server
    public int MAX_PLAYERS = GameConfig.MAX_PLAYERS;
    public final PlayerSprite[] players = new PlayerSprite[MAX_PLAYERS]; // Array to hold player sprites
    private final int PORT = GameConfig.PORT;
    public static boolean didShoot = false;
    public static int damagedPlayerID = 0;

    /**
     * Starts the threads for reading and writing to the server.
     */
    public void connectToServer() {
        try {
            this.socket = new Socket(GameConfig.SERVER_IP_ADDRESS, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Prompt the user for a username
            String username = promptUsername();
            out.writeUTF(username); // Send the username to the server

            playerID = in.readInt();
            idxPlayerID = playerID-1;
            System.out.println("\n==== CLIENT =====");
            System.out.println("Welcome to Robot Worlds, " + username + "!");
            if (playerID != MAX_PLAYERS) {
                System.out.println("Waiting for player other players to connect...");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();
        } catch (IOException e) {
            System.out.println("IOException from connectToServer in Client.");
        }
    }

    private String promptUsername() {
        return JOptionPane.showInputDialog("Enter your username:");
    }

    /**
    * The WriteToServer class implements the Runnable interface to handle the writing of player
    * data to the server. It receives a DataOutputStream object during construction, which is used
    * to send player data to the server in the form of x, y, and angle values. The class continuously
    * checks if the player object is not null, and if so, writes the player data to the output stream
    * and flushes it. It also sleeps for 25 milliseconds before checking again to reduce the amount
    * of data being sent to the server.
    */

    private class WriteToServer implements Runnable {
        private final DataOutputStream outputStream;

        /**
        * Constructs a WriteToServer object with a DataOutputStream object.
        * @param out the DataOutputStream object used to send player data to the server.
        */
        public WriteToServer(DataOutputStream out) {
            this.outputStream = out;
            System.out.println("WTS Runnable created!");
        }

        /**
        * The run method of the WriteToServer class handles the continuous writing of player data
        *to the server. It checks if the player object is not null, and if so, writes the x, y,
        * and angle values of the player to the output stream and flushes it. It also sleeps for
        * 25 milliseconds to reduce the amount of data being sent to the server.
        */
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (players[idxPlayerID] != null) {
                        outputStream.writeInt(idxPlayerID);
                        outputStream.writeDouble(players[idxPlayerID].getX());
                        outputStream.writeDouble(players[idxPlayerID].getY());
                        outputStream.writeDouble(players[idxPlayerID].getAngle());
                        outputStream.writeInt(players[idxPlayerID].getAmmo());

                        // Check if didShoot flag is true
                        if (didShoot) {
                            outputStream.writeBoolean(true);
                            outputStream.writeInt(damagedPlayerID);
                            didShoot = false;
                        } else {
                            outputStream.writeBoolean(false);
                        }

                        outputStream.flush();
                    }
                    Thread.sleep(25);
                }
            } catch (IOException e) {
                // Handle IO exception gracefully
                System.out.println("\n==== CLIENT =====");
                System.out.println("IOException from WTS run() in Client: " + e.getMessage());
            } catch (InterruptedException e) {
                // Restore interrupted status and terminate the thread
                Thread.currentThread().interrupt();
                System.out.println("\n==== CLIENT =====");
                System.out.println("InterruptedException from WTS run() in Client: " + e.getMessage());
            } finally {
                closeGUI();
            }
        }

    }


    /**
    * The ReadFromServer class implements the Runnable interface to handle the reading of other
    * player data from the server. It receives a DataInputStream object during construction, which
    * is used to read other player data in the form of x, y, and angle values. The class continuously
    * checks if the otherPlayer object is not null, and if so, sets the x, y, and angle values of
    * the other player object to the values received from the input stream.
    */
    public class ReadFromServer implements Runnable {
        private final DataInputStream inputStream;

        /**
        * Constructs a ReadFromServer object with a DataInputStream object.
        * @param in the DataInputStream object used to receive other player data from the server.
        */
        public ReadFromServer(DataInputStream in) {
            this.inputStream = in;
            System.out.println("RFS Runnable created!");
        }

        /**
        * The run method of the ReadFromServer class handles the continuous reading of other player
        * data from the server. It checks if the otherPlayer object is not null, and if so, reads
        * the x, y, and angle values of the other player from the input stream and sets the x, y,
        * and angle values of the other player object to those values.
        */
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int playerId = inputStream.readInt();
                    if (playerId >= 0 && playerId < MAX_PLAYERS) {
                        double x = inputStream.readDouble();
                        double y = inputStream.readDouble();
                        double angle = inputStream.readDouble();
                        int health = inputStream.readInt();
                        int ammo = inputStream.readInt();
                        if (playerId != idxPlayerID && players[playerId] != null) {
                            players[playerId].setPosition(x, y);
                            players[playerId].setAngle(angle);
                            players[playerId].setHealth(health);
                            players[playerId].setAmmo(ammo);
                        }
                    }
                }
            } catch (IOException e) {
                // Handle IO exception gracefully
                System.out.println("\n==== CLIENT =====");
                System.out.println("IOException from RFS run() in Client: " + e.getMessage());
            } finally {
                closeGUI();
            }
        }

        /**
        * The waitForStartMsg method is used to wait for a start message from the server and start
        * the reading and writing threads once the message is received. It receives no parameters and
        * returns void.
        * Once called, the method reads a start message from the input stream, prints the message to
        * the console, and starts the reading and writing threads by creating new Thread objects with
        * the rfsRunnable and wtsRunnable objects, respectively. The reading and writing threads are
        * started by calling their start() methods.
        * If an IOException occurs while reading the start message, an error message is printed to the
        * console.
        */
        public void waitForStartMsg() {
            try {
                String msg = inputStream.readUTF();
                System.out.println("\n==== CLIENT =====");
                System.out.println("Message from server: " + msg);

                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);

                readThread.start();
                writeThread.start();
            } catch (IOException e) {
                System.out.println("\n==== CLIENT =====");
                System.out.println("IOException from waitForStartMsg() in Client");
                closeGUI();
            }
        }
    }

    /**
     * Closes client's GUI
     */
    private void closeGUI() {
        System.out.println("\n==== CLIENT =====");
        System.out.println("Server has been shutdown...");
        SwingUtilities.invokeLater(() -> {
            JFrame topLevelFrame = (JFrame) SwingUtilities.getWindowAncestor(GameGUI.drawingComponent);
            topLevelFrame.dispose();
        });
        System.exit(0);
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.connectToServer();
        new GameGUI(client.players, client.playerID, client.idxPlayerID);
    }
}
