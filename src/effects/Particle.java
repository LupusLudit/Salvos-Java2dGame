package effects;

import world.ApplicationPanel;

import java.awt.*;
import java.util.ArrayList;

public class Particle {
    private ApplicationPanel panel;

    private double x;
    private double y;
    private int deltaX;
    private int deltaY;
    private int speed;
    private int duration;
    private final int sideLength = 5;
    private Color color;

    public Particle(ApplicationPanel panel, double x, double y, int deltaX, int deltaY, int speed, int duration, Color color) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        System.out.println("this.x: " + this.x);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.speed = speed;
        this.duration = duration;
        this.color = color;
    }

    public void update(){
        x += deltaX*speed;
        y += deltaY*speed;
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        duration--;
    }
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)x, (int)y, sideLength, sideLength);
        System.out.println("draw");

    }

    public int getDuration() {
        return duration;
    }
}
