package world;

import entities.Player;
import entities.Zombie;
import management.UserInput;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{

    // one tile = 48
    //width, height  = 18, 12 x tile
    private final int tileSide = 48;
    private final int col = 18;
    private final int row = 12;

    UserInput userInput= new UserInput();
    TilePainter tilePainter;
    Player player;
    Zombie zombie;
    Timer timer;


    public Panel() {
        this.setPreferredSize(new Dimension(tileSide * col, tileSide * row));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(userInput);
        zombie = new Zombie(this); //eventually more entities will be added
        player = new Player(userInput, this, zombie);
        tilePainter = new TilePainter(this);
    }


    public void start(){
        tilePainter.setMap();
        timer = new Timer(15, e -> {
            repaint();
            player.update();
            zombie.update();
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tilePainter.draw(g2);
        zombie.draw(g2);
        player.draw(g2);
    }

    public int getTileSide() {
        return tileSide;
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
