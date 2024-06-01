package collectables;

import items.Item;
import logic.ApplicationPanel;

import java.awt.*;
public class Collectable {
    private final Item item;
    private final int x, y;
    private final int baseY;  // Base y position for animation
    private final Rectangle area;
    private final ApplicationPanel panel;
    private double animationOffset;
    private static final double animationSpeed = 0.05;  // Speed of animation
    private static final int maxHeight = 10;  // Amplitude of the up and down movement
    public Collectable(ApplicationPanel panel, Item item, int x, int y) {
        this.panel = panel;
        this.item = item;
        this.x = x * panel.getSquareSide();
        this.y = y * panel.getSquareSide();
        this.baseY = this.y;
        this.area = new Rectangle(this.x, this.y, panel.getSquareSide(), panel.getSquareSide());
        this.animationOffset = 0;
    }

    /**
     * Draws collectable on the screen.
     *
     * @param g the Graphics2D context on which to draw the collectable.
     */
    public void draw(Graphics2D g) {
        int relX = x - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
        int relY = getYByAnimation() - panel.getPlayer().getY() + panel.getPlayer().getCenterY();

        if (Math.abs(panel.getPlayer().getX() - x) < panel.getPlayer().getCenterX() + panel.getSquareSide()
                && Math.abs(panel.getPlayer().getY() - y) < panel.getPlayer().getCenterY() + panel.getSquareSide()) {
            g.drawImage(item.getImage(), relX, relY, null);
        }
    }

    /**
     * Checks whether the collectable intersected the player or not.
     *
     * @return true if collectable intersected the player
     */
    public boolean intersectsPlayer() {
        return panel.getPlayer().getActualArea().intersects(area);
    }

    /**
     * Gets the stored item in this collectable.
     *
     * @return specific item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Changes the y coordinate in time.
     * Creates illusion of bouncing/levitating by getting the value of sine at specific animationOffset.
     * (animationOffset increases with each loop and since the sine periodically repeats, the collectable will appear to go "up and down").
     *
     * @return the y coordinate at a specific time
     */
    public int  getYByAnimation() {
        animationOffset += animationSpeed;
        return baseY + (int)(Math.sin(animationOffset) * maxHeight);
    }
}
