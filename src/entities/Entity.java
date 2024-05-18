package entities;

import management.CollisionManager;
import pathFinding.Node;
import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {

    protected int x;
    protected int y;

    protected double speed;
    protected int counter = 0;
    protected int direction;
    protected String defaultImagePath;
    Rectangle actualArea;
    protected ApplicationPanel panel;

    CollisionManager collisionManager;
    protected boolean canMove;
    protected int lives;
    protected int maxLives;

    public Entity(ApplicationPanel panel) {
        this.panel = panel;
    }

    public abstract void draw(Graphics2D g);

    public abstract void update();

    public BufferedImage chooseImage(int direction, int counter) {
        BufferedImage image;
        switch (direction) {
            case 0 -> {
                if (counter < 10) {
                    image = loadImage("2");
                    break;
                }
                image = loadImage("3");
            }
            case 1 -> {
                if (counter < 10) {
                    image = loadImage("0");
                    break;
                }
                image = loadImage("1");
            }
            case 2 -> {
                if (counter < 10) {
                    image = loadImage("6");
                    break;
                }
                image = loadImage("7");
            }
            case 3 -> {
                if (counter < 10) {
                    image = loadImage("4");
                    break;
                }
                image = loadImage("5");
            }
            default -> throw new IllegalStateException("Unexpected value for direction: " + direction);
        }
        return image;
    }

    public BufferedImage loadImage(String index) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + defaultImagePath + index + ".png")));
        } catch (IOException e) {
        }
        return image;
    }

    public abstract void drawBar(Graphics2D g, int max, int current, int y, Color color);

    public void decreaseLives() {
        if (lives - 1 >= 0) {
            lives--;
        }
    }

    public boolean allEntitiesCollision() {
        if (collisionManager.checkEntityCollision(this, panel.getPlayer())) {
            return true;
        }
        for (Entity entity : panel.getEntities()) {
            if (entity != this) {
                if (collisionManager.checkEntityCollision(this, entity)) {
                    return true;
                }
            }

        }
        return false;
    }

    public void changeDirection(int goalCol, int goalRow) {
        int startCol = x/48;
        int startRow = y/48;
        panel.getSearch().setNodes(startCol, startRow, goalCol, goalRow);
        panel.getSearch().checkNodes();

        if (panel.getSearch().isPathPossible()) {
            Node nextNode = panel.getSearch().getPath().pop();
            int nextX = nextNode.getCol();
            int nextY = nextNode.getRow();

            // Determine the direction to move based on the next node's position
            if (nextX < x/48) {
                direction = 2; // left
            } else if (nextX > x/48) {
                direction = 3; // right
            } else if (nextY < y/48) {
                direction = 0; // up
            } else if (nextY > y/48) {
                direction = 1; // down
            }

            // Check if the entity is at the correct position to move to the next node
            if (nextX == getRelX(panel.getPlayer()) / panel.getSquareSide() && nextY == getRelY(panel.getPlayer()) / panel.getSquareSide()) {
                panel.getSearch().getPath().pop(); // Pop the node only when moving to it
            }
        }
    }




    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getRelX(Entity entity) { // returns x coordinate relative to player
        return x - entity.getX() + panel.getPlayer().getCenterX();
    }

    public int getRelY(Entity entity) { // returns y coordinate relative to player
        return y - entity.getY() + panel.getPlayer().getCenterY();
    }

    public Rectangle getHitBoxArea() {
        return new Rectangle(getRelX(panel.getPlayer()) + 8, getRelY(panel.getPlayer()) , 32, 48);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public Rectangle getActualArea() {
        return actualArea;
    }

    public int getLives() {
        return lives;
    }
}