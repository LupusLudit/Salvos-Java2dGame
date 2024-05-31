package entities;

import management.CollisionManager;
import pathFinding.Node;
import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    protected boolean canMove;
    protected int lives;
    protected int maxLives;
    protected int imageIndex = 0;
    protected BufferedImage currentImage;
    protected boolean canBite = true;
    protected CollisionManager collisionManager;

    // Cache for loaded images
    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    protected int pathUpdateCounter = 0;

    /**
     * Entity constructor.
     *
     * @param panel the application panel
     */
    public Entity(ApplicationPanel panel) {
        this.panel = panel;
    }

    /**
     * Draws an entity.
     * (Also applies to all Overrides)
     *
     * @param g Graphics2D (so the collectable can be drawn on screen)
     */
    public abstract void draw(Graphics2D g);

    /**
     * Updates entity.
     * (Also applies to all Overrides)
     */
    public abstract void update();

    /**
     * Changes current image.
     *
     * @param counter image counter (based on which the image is chosen)
     */
    public abstract void changeCurrentImage(int counter);

    /**
     * Loads the sprite that should be drawn.
     *
     * @param index the index of the sprite
     * @return the selected sprite (BufferedImage)
     */
    public BufferedImage loadSprite(String index) {
        String key = defaultImagePath + direction + "_" + index + ".png";
        if (imageCache.containsKey(key)) {
            return imageCache.get(key);
        }
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + key)));
            imageCache.put(key, image);
            return image;
        } catch (IOException ignored) {
            return null;
        }
    }

    /**
     * Draws a bar.
     * (Also applies to all Overrides)
     *
     * @param g       Graphics2D (so the collectable can be drawn on screen)
     * @param max     the maximum bar "length"
     * @param current the current bar "length"
     * @param y       the y coordinate (There is no need for x coordinate because we calculate it in the method)
     * @param color   the color of the bar
     */
    public abstract void drawBar(Graphics2D g, int max, int current, int y, Color color);

    /**
     * Decreases live.
     */
    public void decreaseLives() {
        if (lives > 0) {
            if (lives == 1){
                panel.getEffectManager().addFlashingEffect(this);
            }
            lives--;
        }
    }

    /**
     * Checks if the entity collided with another entity.
     *
     * @return true if it did collide with another entity.
     */
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

    /**
     * First searches for the path to the goal tile.
     * Then updates the direction.
     *
     * @param goalCol the goal column of the tile
     * @param goalRow the goal row of the tile
     */
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

    /**
     * Changes the direction of the entity after the search has been done.
     * It checks if it is about to hit a solid tile.
     * If so, it updates the direction so the entity will go "around" the solid tile.
     *
     * @param leftX
     * @param rightX
     * @param topY
     * @param bottomY
     * @param pathTileX
     * @param pathTileY
     */

    private void changeDirection(int leftX, int rightX, int topY, int bottomY, int pathTileX, int pathTileY) {

        if (topY > pathTileY && leftX >= pathTileX && rightX < pathTileX + panel.getSquareSide()) {
            direction = 0;
        } else if (topY <= pathTileY && leftX >= pathTileX && rightX < pathTileX + panel.getSquareSide()) {
            direction = 1;
        } else if (topY >= pathTileY && leftX >= pathTileX && bottomY < pathTileY + panel.getSquareSide()) {
            direction = 2;
        } else if (topY >= pathTileY && leftX < pathTileX && bottomY < pathTileY + panel.getSquareSide()) {
            direction = 3;
        } else if (topY > pathTileY && leftX > pathTileX) {
            if (collisionManager.checkTileCollision(this)) {
                direction = 2;
            }
        } else if (topY >= pathTileY && leftX <= pathTileX) {
            if (collisionManager.checkTileCollision(this)){
                direction = 3;
            }
        } else if (leftX >= pathTileX) {
            if (collisionManager.checkTileCollision(this)) {
                direction = 2;
            }
        } else {
            if (collisionManager.checkTileCollision(this)) {
                direction = 3;
            }
        }
    }
    /**
     * Returns x coordinate relative to @param entity.
     *
     * @param entity the entity
     * @return the relative x coordinate
     */
    public int getRelX(Entity entity) {
        return x - entity.getX() + panel.getPlayer().getCenterX();
    }

    /**
     * Returns y coordinate relative to @param entity.
     *
     * @param entity the entity
     * @return the relative y coordinate
     */
    public int getRelY(Entity entity) {
        return y - entity.getY() + panel.getPlayer().getCenterY();
    }
    public void setDirection(int direction) {
        this.direction = direction;
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

    public boolean isCanBite() {
        return canBite;
    }

    public void setCanBite(boolean canBite) {
        this.canBite = canBite;
    }
}