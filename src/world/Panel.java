package world;

import entities.Entity;
import entities.Player;
import management.GameUI;
import management.MouseInput;
import management.UserInput;

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

    public Panel() {
        this.setPreferredSize(new Dimension(SquareSide * col, SquareSide * row));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(userInput);
        this.addMouseListener(mouseInput);
        game.setEntities(1); //temporarily

        player = new Player(userInput, this);
        status = Status.SETUP;
    }


    public void start() {
        tilePainter.setMap();
        timer = new Timer(15, e -> {
            repaint();
            if(status != Status.SETUP && status!= Status.CUSTOMIZATION){
                checkStatus();
                player.update();
                game.updateEntities();
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        ui.draw(g2);
    }

    private long lastShootTime = 0;

    public void simulateShooting(Entity entity) {
        int x;
        int y;

        long currentTime = System.currentTimeMillis();
        int shootingDelay = 800;

        Point mousePosition = this.getMousePosition();
        if (mousePosition == null || !this.contains(mousePosition) || currentTime - lastShootTime < shootingDelay) {
            return;
        }

        if (player.getLives() > 0) {
            x = mousePosition.x;
            y = mousePosition.y;

            Point clickPoint = new Point(x, y);

            if (mouseInput.isMouseClicked() && entity.getHitBoxArea().contains(clickPoint)) {
                entity.decreaseLives();

                lastShootTime = currentTime;
            }
        }
    }

    public void checkStatus(){
        if(player.getLives() > 0){
            status = Status.PLAYING;
        }
        else {
            status = Status.GAMEOVER;
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

    public int getChosenOption() {
        return chosenOption;
    }
}