package effects;


import logic.ApplicationPanel;

import java.awt.*;
public class Particle extends Effect{

    /**
     * Particle constructor
     *
     * @param panel    the application panel
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param deltaX   the change in x
     * @param deltaY   the change in y
     * @param speed    the speed of the particle
     * @param duration the duration of the effect
     * @param color    the color of the particle
     */
    public Particle(ApplicationPanel panel, double x, double y, double deltaX, double deltaY, double speed, int duration, Color color) {
        super(panel,duration);
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.speed = speed;
        this.color = color;
    }

    @Override
    public void update(){
        duration--;
        x += deltaX*speed;
        y += deltaY*speed + 2;
    }
    @Override
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)x, (int)y, sideLength, sideLength);
    }
}
