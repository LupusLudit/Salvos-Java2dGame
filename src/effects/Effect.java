package effects;

import world.ApplicationPanel;

import java.awt.*;

public abstract class Effect {

    private ApplicationPanel panel;

    protected double x;
    protected double y;
    protected double deltaX;
    protected double deltaY;
    protected double speed;
    protected int duration;
    protected final int sideLength = 5;
    protected Color color;

    public Effect(ApplicationPanel panel, int duration) {
        this.panel = panel;
        this.duration = duration;
    }

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public int getDuration() {
        return duration;
    }
}
