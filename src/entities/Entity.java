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
    protected int imageIndex = 0;
    protected BufferedImage currentImage;
    public Entity(ApplicationPanel panel) {
        this.panel = panel;
    }

    public abstract void draw(Graphics2D g);

    public abstract void update();
    public abstract void changeCurrentImage(int counter);

    public BufferedImage loadImage(String index) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + defaultImagePath + direction + "_" + index + ".png")));
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

    public void updatePath(int goalCol, int goalRow) {
        int startCol = (x + 24) / panel.getSquareSide();
        int startRow = (y + 32) / panel.getSquareSide();
        panel.getSearch().setNodes(startCol, startRow, goalCol, goalRow);
        panel.getSearch().checkNodes();

        if (panel.getSearch().isPathPossible()) {
            Node nextNode = panel.getSearch().getPath().pop();
            int pathTileX = nextNode.getCol() * panel.getSquareSide();
            int pathTileY = nextNode.getRow() * panel.getSquareSide();

            int leftX = actualArea.x;
            int rightX = actualArea.x + actualArea.width;
            int topY = actualArea.y;
            int bottomY = actualArea.y + actualArea.height;

            changeDirection(leftX, rightX, topY, bottomY, pathTileX, pathTileY);
        }
    }

    private void changeDirection(int leftX, int rightX, int topY, int bottomY, int pathTileX, int pathTileY) {

        boolean entityCollision = false;
        for (Entity entity : panel.getEntities()) {
            if (entity != this && collisionManager.checkEntityCollision(this, entity)) {
                entityCollision = true;
                break;
            }
        }
        if (topY > pathTileY && leftX >= pathTileX && rightX < pathTileX + panel.getSquareSide()) {
            direction = 0;
        } else if (topY <= pathTileY && leftX >= pathTileX && rightX < pathTileX + panel.getSquareSide()) {
            direction = 1;
        } else if (topY >= pathTileY && leftX >= pathTileX && bottomY < pathTileY + panel.getSquareSide()) {
            direction = 2;
        } else if (topY >= pathTileY && leftX < pathTileX && bottomY < pathTileY + panel.getSquareSide()) {
            direction = 3;
        } else if (topY > pathTileY && leftX > pathTileX) {
            if (collisionManager.checkTileCollision(this, panel) || entityCollision) {
                direction = 2;
            }
        } else if (topY >= pathTileY && leftX <= pathTileX) {
            if (collisionManager.checkTileCollision(this, panel) || entityCollision) {
                direction = 3;
            }
        } else if (leftX >= pathTileX) {
            if (collisionManager.checkTileCollision(this, panel) || entityCollision) {
                direction = 2;
            }
        } else {
            if (collisionManager.checkTileCollision(this, panel) || entityCollision) {
                direction = 3;
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
        return new Rectangle(getRelX(panel.getPlayer()) + 8, getRelY(panel.getPlayer()), 32, 48);
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