package world;

import collectables.CollectableManager;
import entities.Entity;
import entities.Player;
import items.Revolver;
import management.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel implements Runnable{
    private final int squareSide = 48;
    private final int col = 24;
    private final int row = 14;

    UserInput userInput = new UserInput(this);
    MouseInput mouseInput = new MouseInput();
    TilePainter tilePainter = new TilePainter(this);
    GameUI ui = new GameUI(this);
    ArrayList<Entity> entities = new ArrayList<>();
    Game game = new Game(this);
    Player player;
    Timer timer;

    private Status status;

    private int chosenOption = 0;
    private int wave = 1;
    private int waveTimer = 10;
    Clock clock;
    CollectableManager collectableManager = new CollectableManager(this);

    Shop shop = new Shop(this);

    Thread gameThread;

    public Panel() {

        System.out.println(col);
        System.out.println(row);

        this.setPreferredSize(new Dimension(squareSide * col, squareSide * row));

        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(userInput);
        this.addMouseListener(mouseInput);
        game.setEntities(wave);

        player = new Player(userInput, this);
        status = Status.SETUP;
        clock = new Clock();

        Revolver revolver = new Revolver(this);
        revolver.collect();
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
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
                repaint();
                update();
            } else {
                try {
                    Thread.sleep((desiredFrameTime - deltaTime) / 1_000_000); //sleep for the remaining time left
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update() {
        if (status == Status.CUSTOMIZATION) {
            player.setBonuses();
        } else if (status != Status.SETUP && status != Status.GAMEOVER) {
            if (entities.isEmpty() && !clock.isRunning()){
                newWave();
            }
            checkStatus();
            player.update();
            game.updateEntities();
            collectableManager.checkCollectables();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        ui.draw(g2);
    }

    public void checkStatus() {

        if (player.getLives() <= 0) {
            status = Status.GAMEOVER;
        }
    }
    public void newWave() {
        if (waveTimer == 0) {
            wave++;
            game.setEntities(wave);
            waveTimer = 10;
        }
        else {
            clock = new Clock();
            clock.start(10, this, Mode.WAVE_COUNTER);
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

    public ArrayList<Entity> getEntities() {
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
}