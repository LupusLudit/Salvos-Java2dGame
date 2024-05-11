package world;

import collectables.CollectableManager;
import entities.Entity;
import entities.Player;
import management.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {

    // one tile = 48
    //width, height  = 18, 12 x tile
    private final int SquareSide = 48;
    private final int col = 18;
    private final int row = 12;

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

    public Panel() {
        this.setPreferredSize(new Dimension(SquareSide * col, SquareSide * row));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(userInput);
        this.addMouseListener(mouseInput);
        game.setEntities(wave);

        player = new Player(userInput, this);
        status = Status.SETUP;
        clock = new Clock();
    }


    public void start() {
        tilePainter.setMap();
        timer = new Timer(15, e -> {
            repaint();
            if (status == Status.CUSTOMIZATION) {
                player.setBonuses();
            } else if (status != Status.SETUP && status != Status.GAMEOVER) {
                newWave();
                checkStatus();
                player.update();
                game.updateEntities();
                collectableManager.checkCollectables();
            }
        });
        timer.start();
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
        if (waveTimer == 0 && entities.isEmpty() && !clock.isRunning()) {
            wave++;
            game.setEntities(wave);
            waveTimer = 10;
        }
        else if (entities.isEmpty() && !clock.isRunning()) {
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
        return SquareSide * col;
    }

    public int getHeight() {
        return SquareSide * row;
    }

    public int getSquareSide() {
        return SquareSide;
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
}