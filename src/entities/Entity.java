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

/**
 * The type Entity.
 */
public abstract class Entity {

    /**
     * The X.
     */
    protected int x;
    /**
     * The Y.
     */
    protected int y;

    /**
     * The Speed.
     */
    protected double speed;
    /**
     * The Counter.
     */
    protected int counter = 0;
    /**
     * The Direction.
     */
    protected int direction;
    /**
     * The Default image path.
     */
    protected String defaultImagePath;
    /**
     * The Actual area.
     */
    Rectangle actualArea;
    /**
     * The Panel.
     */
    protected ApplicationPanel panel;
    /**
     * The Can move.
     */
    protected boolean canMove;
    /**
     * The Lives.
     */
    protected int lives;
    /**
     * The Max lives.
     */
    protected int maxLives;
    /**
     * The Image index.
     */
    protected int imageIndex = 0;
    /**
     * The Current image.
     */
    protected BufferedImage currentImage;
    /**
     * The Can bite.
     */
    protected boolean canBite = true;
    /**
     * The Collision manager.
     */
    protected CollisionManager collisionManager;

    // Cache for loaded images
    private final Map<String, BufferedImage> imageCache = new HashMap<>();

    /**
     * The Path update counter.
     */
    protected int pathUpdateCounter = 0;

    /**
     * Instantiates a new Entity.
     *
     * @param panel the panel
     */
    public Entity(ApplicationPanel panel) {
        this.panel = panel;
    }

    /**
     * Draw.
     *
     * @param g the g
     */
    public abstract void draw(Graphics2D g);

    /**
     * Update.
     */
    public abstract void update();

    /**
     * Change current image.
     *
     * @param counter the counter
     */
    public abstract void changeCurrentImage(int counter);

    /**
     * Load image buffered image.
     *
     * @param index the index
     * @return the buffered image
     */
    public BufferedImage loadImage(String index) {
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
     * Draw bar.
     *
     * @param g       the g
     * @param max     the max
     * @param current the current
     * @param y       the y
     * @param color   the color
     */
    public abstract void drawBar(Graphics2D g, int max, int current, int y, Color color);

    /**
     * Decrease lives.
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
     * All entities collision boolean.
     *
     * @return the boolean
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
     * Update path.
     *
     * @param goalCol the goal col
     * @param goalRow the goal row
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
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Gets rel x.
     *
     * @param entity the entity
     * @return the rel x
     */
    public int getRelX(Entity entity) {
        return x - entity.getX() + panel.getPlayer().getCenterX();
    }

    /**
     * Gets rel y.
     *
     * @param entity the entity
     * @return the rel y
     */
    public int getRelY(Entity entity) {
        return y - entity.getY() + panel.getPlayer().getCenterY();
    }

    /**
     * Gets hit box area.
     *
     * @return the hit box area
     */
    public Rectangle getHitBoxArea() {
        return new Rectangle(getRelX(panel.getPlayer()) + 8, getRelY(panel.getPlayer()), 32, 48);
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Gets actual area.
     *
     * @return the actual area
     */
    public Rectangle getActualArea() {
        return actualArea;
    }

    /**
     * Gets lives.
     *
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Is can bite boolean.
     *
     * @return the boolean
     */
    public boolean isCanBite() {
        return canBite;
    }

    /**
     * Sets can bite.
     *
     * @param canBite the can bite
     */
    public void setCanBite(boolean canBite) {
        this.canBite = canBite;
    }
}