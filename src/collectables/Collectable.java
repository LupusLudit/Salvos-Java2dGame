package collectables;

import items.Item;

import java.awt.*;

public class Collectable {

    private Item item;
    private int x;
    private int y;
    private Rectangle area;

    world.Panel panel;

    public Collectable(world.Panel panel,Item item, int x, int y) {
        this.panel = panel;
        this.item = item;
        this.x = x * panel.getSquareSide();
        this.y = y * panel.getSquareSide();

        area = new Rectangle(this.x,this.y, panel.getSquareSide(), panel.getSquareSide());
    }


    public void draw(Graphics2D g) {
        int relX = x - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
        int relY = y - panel.getPlayer().getY() + panel.getPlayer().getCenterY();

        if (Math.abs(panel.getPlayer().getX() - x) < panel.getPlayer().getCenterX() + panel.getSquareSide()
                && Math.abs(panel.getPlayer().getY() - y) < panel.getPlayer().getCenterY() + panel.getSquareSide()) { //is in range control
            g.drawImage(item.getImage(), relX, relY, null);
        }
    }

    public boolean intersectsPlayer() {
        return panel.getPlayer().getActualArea().intersects(area);
    }

    public Item getItem() {
        return item;
    }
}
