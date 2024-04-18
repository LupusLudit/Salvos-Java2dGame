package world;

import entities.Entity;
import entities.Player;
import entities.Zombie;
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

    UserInput userInput = new UserInput();
    MouseInput mouseInput = new MouseInput();
    TilePainter tilePainter = new TilePainter(this);
    GameUI ui = new GameUI(this);
    ArrayList<Entity> entities = new ArrayList<>();

    Game game = new Game(this);
    Player player;
    Timer timer;

    public Panel() {
        this.setPreferredSize(new Dimension(SquareSide * col, SquareSide * row));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(userInput);
        this.addMouseListener(mouseInput);
        game.setEntities(1); //temporarily

        player = new Player(userInput, this);
    }


    public void start() {
        tilePainter.setMap();
        timer = new Timer(15, e -> {
            repaint();
            player.update();
            game.updateEntities();
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (player.getLives() > 0) {
            tilePainter.draw(g2);
            game.drawEntities(g2);
            player.draw(g2);
        } else {
            ui.drawDeathScreen(g2);
        }
    }

    private long lastShootTime = 0;
    public void simulateShooting(Entity entity) {
        int x;
        int y;

        long currentTime = System.currentTimeMillis();
        int shootingDelay = 800;

        Point mousePosition = this.getMousePosition();
        if (mousePosition == null || !this.contains(mousePosition) || currentTime - lastShootTime < shootingDelay ) {
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


}