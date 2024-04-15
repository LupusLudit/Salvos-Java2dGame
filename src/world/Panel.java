package world;

import entities.Entity;
import entities.Player;
import entities.Zombie;
import management.GameUI;
import management.MouseInput;
import management.UserInput;

import javax.swing.*;
import java.awt.*;

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
    Player player;
    Zombie zombie;
    Timer timer;

    public Panel() {
        this.setPreferredSize(new Dimension(SquareSide * col, SquareSide * row));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(userInput);
        this.addMouseListener(mouseInput);

        zombie = new Zombie(this); //eventually more entities will be added, temporarily added for testing
        player = new Player(userInput, this, zombie);
    }


    public void start() {
        tilePainter.setMap();
        timer = new Timer(15, e -> {
            repaint();
            player.update();
            zombie.update();
            simulateShooting(zombie);
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (player.getLives() > 0) {
            tilePainter.draw(g2);
            zombie.draw(g2);
            player.draw(g2);
        } else {
            ui.drawDeathScreen(g2);
        }
    }

    private final int shootingDelay = 250;
    private long lastShootTime = 0;
    public void simulateShooting(Entity entity) {
        int x;
        int y;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime < shootingDelay) {
            return;
        }

        if (this.getMousePosition() != null && player.getLives() > 0) {
            x = this.getMousePosition().x;
            y = this.getMousePosition().y;

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
}