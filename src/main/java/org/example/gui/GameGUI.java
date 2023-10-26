package org.example.gui;

import org.example.GameConfig;
import org.example.client.Client;
import org.example.client.Obstacle;
import org.example.input.KeyInput;
import org.example.input.MouseInput;
import org.example.player.*;
import org.example.input.Audio;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;


public class GameGUI extends JFrame implements MouseMotionListener {
    protected static final int HEIGHT = GameConfig.SCREEN_HEIGHT;
    protected static final int WIDTH = GameConfig.SCREEN_WIDTH;
    public static DrawingComponent drawingComponent; // The component used to draw the game
    private final int MAX_PLAYERS = GameConfig.MAX_PLAYERS;
    PlayerSprite[] players; // Array to hold player sprites
    public KeyInput keyInput;
    private final int playerID;
    private final int idxPlayerID;
    private MouseInput mouseInput;
    public Campass campass;
    ScoreCounter score = new ScoreCounter();
    BulletCounter bulletcounter = new BulletCounter();
    HealthPanel healthBar = new HealthPanel();
    public BulletSprite bullet;
//    private boolean didShoot = Client.didShoot;
    private int bulletCounter;
    Audio soundEffect;

    int[][] obstaclePositions = Obstacle.positions;
//    int damagedPlayerID = Client.damagedPlayerID;

    public GameGUI(PlayerSprite[] players, int playerID, int idxPlayer) {
        this.players = players;
        this.playerID = playerID;
        this.idxPlayerID = idxPlayer;
        setUpGUI();
    }

    private void setUpGUI() {
        setTitle("Robot Worlds - Player #" + playerID);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        getContentPane().setBackground(Color.lightGray);
        createSprites();
        drawingComponent = new DrawingComponent(players[idxPlayerID]);
        add(drawingComponent);
        healthBar.setHealth(150);
        bulletcounter.setBullet(10);
        setUpKeyListener();
        pack();
        setResizable(false); // Make the window not resizable
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);

        // The timer used to update the animation
        Timer animationTimer = new Timer(10, e -> updateAnimation());
        animationTimer.start();
    }

    /**
     * Creates the player sprites for the game.
     */
    public void createSprites() {
        Color[] colors = {Color.BLUE, Color.RED, Color.ORANGE, Color.PINK};
        for (int i = 0; i < MAX_PLAYERS; i++) {
            players[i] = new PlayerSprite(0, 0,50, colors[i]);
        }
    }

    /**
     * Sets up the key listener for the client.
     */
    private void setUpKeyListener() {
        Container contentPane = getContentPane();
        contentPane.setFocusable(true);
        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        contentPane.addKeyListener(keyInput);
        contentPane.addMouseListener(mouseInput);
        contentPane.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Implement mouseDragged logic if needed
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                processMouseMoved(e);
            }
        });
        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        // Load the audio file
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/java/org/example/assets/BulletFire.wav"));

                        // Get the audio format
                        AudioFormat audioFormat = audioInputStream.getFormat();

                        // Create a clip from the audio format
                        DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
                        Clip clip = (Clip) AudioSystem.getLine(info);

                        // Open the audio stream
                        clip.open(audioInputStream);

                        // Create a gain control for the clip
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                        // Increase the volume by 6 decibels (adjust the value as needed)
                        gainControl.setValue(gainControl.getValue() + 6.0f);

                        // Start playing the audio
                        clip.start();

                        // Set the flag for shooting
                        Client.didShoot = true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Processes mouse moved events for the client.
     * @param e the MouseEvent to process
     */
    public void processMouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        // The coordinates of the mouse cursor
        int mouseY = e.getY();
        players[idxPlayerID].rotatePlayer(mouseX, mouseY);
        drawingComponent.repaint();
    }

    /**
     * Processes mouse dragged events for the client.
     * @param e the MouseEvent to process
     */
    public void mouseDragged(MouseEvent e) {
        // Implement mouseDragged logic if needed
    }

    /**
     * Processes mouse moved events for the client.
     * @param e the MouseEvent to process
     */
    public void mouseMoved(MouseEvent e) {
        // Implement mouseMoved logic if needed
    }

    /**
     * This method updates the animation for the client. It checks the current state
     * of the arrow keys, and moves the player object in the corresponding direction with a
     * speed of 5 units per frame. The method then updates the player object, and repaints
     * the drawing component to show the updated position of the player object.
     * Note: This method assumes that the "player" object and the "drawingComponent"
     * object have already been initialized and instantiated before this method is called.
     */
    private void updateAnimation() {
        double speed = 5;
        double bSpeed = 8;

        if (keyInput.up) {
            movePlayerVertical(-speed);
        }

        if (keyInput.down) {
            movePlayerVertical(speed);
        }

        if (keyInput.left) {
            movePlayerHorizontal(-speed);
        }

        if (keyInput.right) {
            movePlayerHorizontal(speed);
        }
        if(keyInput.Repair){
            try {
                Thread.sleep(2000); // Delay for 2 seconds (2000 milliseconds)
                healthBar.setHealth(150);
            } catch (InterruptedException e) {
                // Handle the exception if necessary
                e.printStackTrace();
            }

        }
        if(keyInput.Reload){
            try {
                Thread.sleep(2000); // Delay for 2 seconds (2000 milliseconds)
                bulletcounter.setBullet(10);
            } catch (InterruptedException e) {
                // Handle the exception if necessary
                e.printStackTrace();
            }

        }

        if (Client.didShoot) {
            bullet = new BulletSprite();
            bullet.x = players[idxPlayerID].getX()+50;
            bullet.y = players[idxPlayerID].getY()+25;
            bullet.width = 8;
            bullet.height = 8;
//            didShoot = false;
            int ammo = players[idxPlayerID].getAmmo();
            players[idxPlayerID].setAmmo(ammo-1);
            bulletcounter.getBullet();

        }

        // Move the bullet sprite forward
        if (bullet != null) {
            double angleRad = Math.toRadians(players[idxPlayerID].angle);
            double dx = bSpeed * Math.cos(angleRad);
            double dy = bSpeed * Math.sin(angleRad);
            bullet.update(dx, dy);
            bulletCounter++;

            if (bulletCounter >= 50) {
                bullet = null; // Delete the bullet
                bulletCounter = 0; // Reset the counter
            }

        }

        // Check for collision between bullet and player sprite
        if (bullet != null) {
            Rectangle bulletBounds = new Rectangle((int) bullet.x, (int) bullet.y, (int) bullet.width, (int) bullet.height);

            for (int i = 0; i < players.length; i++) {
                PlayerSprite player = players[i];
                if (player != null && player != players[idxPlayerID]) {
                    Rectangle playerBounds = new Rectangle((int) player.x, (int) player.y, (int) player.size, (int) player.size);

                    if (bulletBounds.intersects(playerBounds)) {
                        int playerHealth = player.getHealth();
                        player.setHealth(playerHealth - 5); // Decrease player health
                        bullet = null; // Delete the bullet
                        System.out.println("Player has been HIT...");
                        Client.damagedPlayerID = i;
                        healthBar.decreaseHealth(); // Decrease health in the HealthPanel
                        break; // Exit the loop since a collision occurred
                    }
                }
            }
        }

        healthBar.setHealth(players[idxPlayerID].getHealth());

        players[idxPlayerID].update();
        drawingComponent.repaint();
    }

    /**
     * Moves the player sprite horizontally by the specified speed.
     * @param speed the speed at which to move the player horizontally.
     */
    private void movePlayerHorizontal(double speed) {
        double newX = players[idxPlayerID].x + speed;

        if (!isCollision(newX, players[idxPlayerID].y, players[idxPlayerID].size)) {
            players[idxPlayerID].x = newX;
        }
    }

    /**
     * Moves the player sprite vertically by the specified speed.
     * @param speed the speed at which to move the player vertically.
     */
    private void movePlayerVertical(double speed) {
        double newY = players[idxPlayerID].y + speed;

        if (!isCollision(players[idxPlayerID].x, newY, players[idxPlayerID].size)) {
            players[idxPlayerID].y = newY;
        }
    }

    /**
     * Checks if the given coordinates and size result in a collision with any obstacles.
     * @param x the x-coordinate of the object
     * @param y the y-coordinate of the object
     * @param size the size of the object
     * @return true if a collision occurs, false otherwise
     */
    public boolean isCollision(double x, double y, double size) {
        Rectangle objectBounds = new Rectangle((int) x, (int) y, (int) size, (int) size);

        for (int[] obstaclePosition : obstaclePositions) {
            int obstacleX = obstaclePosition[0];
            int obstacleY = obstaclePosition[1];
            Rectangle obstacleBounds = new Rectangle(obstacleX, obstacleY, Obstacle.size, Obstacle.size);
            if (objectBounds.intersects(obstacleBounds)) {
                return true;
            }
        }
        return false;
    }


    public class DrawingComponent extends JComponent {
        private final Camera camera;

        public DrawingComponent(PlayerSprite player) {
            this.camera = new Camera(10);
        }

        @Override
        protected void paintComponent(Graphics g) {

            campass = new Campass();
            campass.setAngle(-(int) players[idxPlayerID].angle);
            healthBar.healthBar(g);
            score.Score(g);
            campass.draw(g, 40);
            bulletcounter.bullet(g);

            healthBar.setHealth(players[idxPlayerID].getHealth());

            camera.follow(players[idxPlayerID]);

            camera.update();
            camera.apply((Graphics2D) g);

            for (int[] obstaclePosition : obstaclePositions) {
                int x = obstaclePosition[0];
                int y = obstaclePosition[1];
                g.setColor(Obstacle.color);
                g.fillRect(x, y, Obstacle.size, Obstacle.size);
            }

            for (PlayerSprite player : players) {
                player.drawSprite((Graphics2D) g);
            }

            // Draw the bullet sprite if it exists
            if (bullet != null) {
                bullet.draw(g);
            }
        }
    }
}
