package effects;

import entities.Entity;
import logic.ApplicationPanel;

import java.awt.*;

public class FlashingEffect extends Effect{

    private final Entity entity;

    /**
     * Flashing effect constructor.
     *
     * @param panel    the application panel
     * @param entity   the entity on which will the effect be drawn
     * @param duration the duration of the effect
     */
    public FlashingEffect(ApplicationPanel panel,Entity entity, int duration) {
        super(panel,duration);
        this.entity = entity;
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        entity.draw(g);
    }
}
