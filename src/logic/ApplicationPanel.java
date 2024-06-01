package logic;

import collectables.CollectableManager;
import effects.EffectManager;
import entities.Entity;
import entities.Player;
import items.Revolver;
import management.*;
import pathFinding.Search;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationPanel extends JPanel implements Runnable{
    private final int squareSide = 48;
    private final int col = 24;
    private final int row = 14;
    MouseInput mouseInput = new MouseInput();
    TilePainter tilePainter;
    UserInput userInput = new UserInput(this);
    GameUI ui = new GameUI(this);
    CollisionManager collisionManager = new CollisionManager(this);
    private List<Entity> entities;
    Game game;
    Player player;
    CollectableManager collectableManager;
    Shop shop = new Shop(this);
    EffectManager effectManager;
    private Status status;
    private int chosenOption = 0;
    private int wave = 1;
    private int waveTimer = 10;
    Clock clock = new Clock();
    Search search = new Search(this);
    Thread gameThread;

    /**
     * Application panel constructor.
     */
    public ApplicationPanel() {
        setPreferredSize(new Dimension(squareSide * col, squareSide * row));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(userInput);
        addMouseListener(mouseInput);
        changeCursor();

        restart();
    }

    /**
     * Starts new game thread.
     */
    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * (re)initializes panels properties.
     */
    public void restart(){
        tilePainter = new TilePainter(this);
        entities = new ArrayList<>();
        game = new Game(this);
        player = new Player(userInput, this);
        collectableManager = new CollectableManager(this);
        effectManager = new EffectManager(this);
        status = Status.SETUP;
        wave = 1;
        game.setEntities(wave);
        Revolver revolver = new Revolver(this);
        revolver.collect();
    }


    /**
     * Overrides the default JPanel run method.
     * Ensures that the program doesn't update too quickly (60 FPS).
     * Uses delta time algorithm.
     */

    @Override
    public void run() {
        long desiredFrameTime = 16_666_666; // 60 FPS
        long lastFrameTime = System.nanoTime();
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - lastFrameTime;
            if (deltaTime >= desiredFrameTime) {
                lastFrameTime = currentTime;
                update();
                repaint();
            } else {
                try {
                    Thread.sleep((desiredFrameTime - deltaTime) / 1_000_000); //sleep for the remaining time left
                } catch (InterruptedException ignored) {}
            }
        }
    }

    /**
     * Updates the whole code.
     */
    public void update() {
        checkStatus();
        if (status == Status.CUSTOMIZATION) {
            player.setBonuses();
        } else if (status != Status.SETUP && status != Status.GAMEOVER) {
            if (entities.isEmpty() && !clock.isRunning()){
                newWave();
            }
            player.update();
            game.updatedGame();
            collectableManager.checkCollectables();
            effectManager.update();
        }
    }

    /**
     * Draws everything on the screen.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        try {
            ui.draw(g2);
            if (status == Status.PLAYING) {
                effectManager.drawEffects(g2);
            }
        }catch (IOException ignored){}

    }

    /**
     * Changes the default cursor to a custom one.
     */
    public void changeCursor() {
        BufferedImage cursorImg = null;
        try {
            cursorImg = ImageIO.read(new File("resources/cursor/circle_cursor.png"));
        } catch (IOException ignored) {}

        assert cursorImg != null;
        int centerX = cursorImg.getWidth()/4;
        int centerY = cursorImg.getHeight()/4;
        Point hotSpot = new Point(centerX, centerY);

        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, hotSpot, "Custom Cursor");
        this.setCursor(customCursor);
    }

    /**
     * Checks whether player is dead or not.
     * If so, it changes the game status.
     */
    public void checkStatus() {
        if (player.getLives() <= 0) {
            status = Status.GAMEOVER;
        }
    }

    /**
     * "Starts" new wave of enemies if the entities ArrayList is empty.
     */
    public void newWave() {
        if (waveTimer == 0) {
            wave++;
            game.setEntities(wave);
            waveTimer = 10;
        }
        else {
            clock = new Clock();
            clock.start(10, this, ClockMode.WAVE_COUNTER);
            collectableManager.addRandomCollectables();
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void setChosenOption(int chosenOption) {
        this.chosenOption = chosenOption;
    }
    public int getWidth() {
        return squareSide * col;
    }
    public int getHeight() {
        return squareSide * row;
    }
    public int getSquareSide() {
        return squareSide;
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
    public Player getPlayer() {
        return player;
    }
    public TilePainter getTilePainter() {
        return tilePainter;
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public Status getStatus() {
        return status;
    }
    public Game getGame() {
        return game;
    }
    public MouseInput getMouseInput() {
        return mouseInput;
    }
    public int getChosenOption() {
        return chosenOption;
    }
    public GameUI getUi() {
        return ui;
    }
    public int getWaveTimer() {
        return waveTimer;
    }
    public void setWaveTimer(int waveTimer) {
        this.waveTimer = waveTimer;
    }
    public CollectableManager getCollectableManager() {
        return collectableManager;
    }
    public Shop getShop() {
        return shop;
    }
    public UserInput getUserInput() {
        return userInput;
    }
    public EffectManager getEffectManager() {
        return effectManager;
    }
    public Search getSearch() {
        return search;
    }
    public CollisionManager getCollisionManager() {
        return collisionManager;
    }
}