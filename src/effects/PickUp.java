package effects;

import items.Item;
import world.ApplicationPanel;

import java.awt.*;

public class PickUp extends Effect {
    public PickUp(ApplicationPanel applicationPanel, int duration) {
        super(applicationPanel, duration);
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(panel.getUi().getMedium());
        g.drawString("You´ve picked up an item.", 10, panel.getUi().textHeight(g, "You´ve picked up an item") + panel.getSquareSide());
        System.out.println("You´ve picked up an item.");
    }

}
