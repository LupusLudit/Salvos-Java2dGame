package collectables;

import items.Item;
import world.ApplicationPanel;

import java.awt.*;

public class Collectable {
    private Item item;
    private int x, y;
    private int baseY;  // Base y position for animation
    private Rectangle area;
    private ApplicationPanel applicationPanel;
    private double animationOffset;
    private static final double animationSpeed = 0.05;  // Speed of animation
    private static final int maxHeight = 10;  // Amplitude of the up and down movement

    public Collectable(ApplicationPanel applicationPanel, Item item, int x, int y) {
        this.applicationPanel = applicationPanel;
        this.item = item;
        this.x = x * applicationPanel.getSquareSide();
        this.y = y * applicationPanel.getSquareSide();
        this.baseY = this.y;  // Set base y position
        this.area = new Rectangle(this.x, this.y, applicationPanel.getSquareSide(), applicationPanel.getSquareSide());
        this.animationOffset = 0;
    }

    public void draw(Graphics2D g) {
        int relX = x - applicationPanel.getPlayer().getX() + applicationPanel.getPlayer().getCenterX();
        int relY = getYByAnimation() - applicationPanel.getPlayer().getY() + applicationPanel.getPlayer().getCenterY();

        if (Math.abs(applicationPanel.getPlayer().getX() - x) < applicationPanel.getPlayer().getCenterX() + applicationPanel.getSquareSide()
                && Math.abs(applicationPanel.getPlayer().getY() - y) < applicationPanel.getPlayer().getCenterY() + applicationPanel.getSquareSide()) {
            g.drawImage(item.getImage(), relX, relY, null);
        }
    }

    public boolean intersectsPlayer() {
        return applicationPanel.getPlayer().getActualArea().intersects(area);
    }

    public Item getItem() {
        return item;
    }

    public int  getYByAnimation() {
        animationOffset += animationSpeed;
        return baseY + (int)(Math.sin(animationOffset) * maxHeight);
    }
}
