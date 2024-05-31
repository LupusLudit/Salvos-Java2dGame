package effects;

import logic.ApplicationPanel;

import java.awt.*;
import java.io.IOException;

public abstract class Effect {

    protected  ApplicationPanel panel;
    protected double x;
    protected double y;
    protected double deltaX;
    protected double deltaY;
    protected double speed;
    protected int duration;
    protected final int sideLength = 5;
    protected Color color;

    /**
     * Effect constructor.
     *
     * @param panel    the application panel
     * @param duration the duration of the effect
     */
    public Effect(ApplicationPanel panel, int duration) {
        this.panel = panel;
        this.duration = duration;
    }

    /**
     * Updates the effect.
     * (Also applies to all Overrides)
     */
    public abstract void update();

    /**
     * Draws the effect on the screen.
     * (Also applies to all Overrides)
     *
     * @param g Graphics2D (so the collectable can be drawn on screen)
     * @throws IOException if the program couldn't find the image on the specific address.
     */
    public abstract void draw(Graphics2D g) throws IOException;

    /**
     * Returns the current duration (for how much longer should the program update the effect).
     *
     * @return the value of duration
     */
    public int getDuration() {
        return duration;
    }
}
