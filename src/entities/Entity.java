package entities;

import management.CollisionManager;

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
    world.Panel panel;

    CollisionManager collisionManager;
    protected boolean canMove;
    protected int lives;
    protected int maxLives;

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


    public int getRelX() { // returns x coordinate relative to player
        return x - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
    }

    public int getRelY() { // returns y coordinate relative to player
        return y - panel.getPlayer().getY() + panel.getPlayer().getCenterY();
    }

    public Rectangle getHitBoxArea() {
        return new Rectangle(getRelX(), getRelY() - 16, 32, 48);
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