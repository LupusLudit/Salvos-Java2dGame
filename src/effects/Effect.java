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

    public Effect(ApplicationPanel panel, int duration) {
        this.panel = panel;
        this.duration = duration;
    }

    public abstract void update();

    public abstract void draw(Graphics2D g) throws IOException;

    public int getDuration() {
        return duration;
    }
}
