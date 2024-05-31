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
     * Update.
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
            game.updateEntities();
            collectableManager.checkCollectables();
            effectManager.update();
        }
    }
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
     * Change cursor.
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
     * Check status.
     */
    public void checkStatus() {
        if (player.getLives() <= 0) {
            status = Status.GAMEOVER;
        }
    }

    /**
     * New wave.
     */
    public void newWave() {
        if (waveTimer == 0) {
            wave++;
            game.setEntities(wave);
            waveTimer = 10;
        }
        else {
            clock = new Clock();
            clock.start(10, this, Mode.WAVE_COUNTER);
            collectableManager.addRandomCollectables();
        }
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Sets chosen option.
     *
     * @param chosenOption the chosen option
     */
    public void setChosenOption(int chosenOption) {
        this.chosenOption = chosenOption;
    }

    public int getWidth() {
        return squareSide * col;
    }

    public int getHeight() {
        return squareSide * row;
    }

    /**
     * Gets square side.
     *
     * @return the square side
     */
    public int getSquareSide() {
        return squareSide;
    }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets tile painter.
     *
     * @return the tile painter
     */
    public TilePainter getTilePainter() {
        return tilePainter;
    }

    /**
     * Gets entities.
     *
     * @return the entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets mouse input.
     *
     * @return the mouse input
     */
    public MouseInput getMouseInput() {
        return mouseInput;
    }

    /**
     * Gets chosen option.
     *
     * @return the chosen option
     */
    public int getChosenOption() {
        return chosenOption;
    }

    /**
     * Gets ui.
     *
     * @return the ui
     */
    public GameUI getUi() {
        return ui;
    }

    /**
     * Gets wave timer.
     *
     * @return the wave timer
     */
    public int getWaveTimer() {
        return waveTimer;
    }

    /**
     * Sets wave timer.
     *
     * @param waveTimer the wave timer
     */
    public void setWaveTimer(int waveTimer) {
        this.waveTimer = waveTimer;
    }

    /**
     * Gets collectable manager.
     *
     * @return the collectable manager
     */
    public CollectableManager getCollectableManager() {
        return collectableManager;
    }

    /**
     * Gets shop.
     *
     * @return the shop
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * Gets user input.
     *
     * @return the user input
     */
    public UserInput getUserInput() {
        return userInput;
    }

    /**
     * Gets effect manager.
     *
     * @return the effect manager
     */
    public EffectManager getEffectManager() {
        return effectManager;
    }

    /**
     * Gets search.
     *
     * @return the search
     */
    public Search getSearch() {
        return search;
    }

    /**
     * Gets collision manager.
     *
     * @return the collision manager
     */
    public CollisionManager getCollisionManager() {
        return collisionManager;
    }
}