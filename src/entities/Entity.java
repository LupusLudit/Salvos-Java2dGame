package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int speed;
    protected int counter = 0;

    protected int direction;

    Rectangle actualArea;

    protected boolean canMove;


    public abstract void draw(Graphics2D g);
    public abstract void update();

    public abstract BufferedImage chooseImage(int direction, int counter);

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public Rectangle getActualArea() {
        return actualArea;
    }


}
