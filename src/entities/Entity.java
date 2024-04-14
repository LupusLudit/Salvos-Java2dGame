package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {

    protected int x;
    protected int y;

    protected int speed;
    protected int counter = 0;
    protected int direction;
    protected String defaultImagePath;
    Rectangle actualArea;
    world.Panel panel;
    protected boolean canMove;
    protected int lives;
    protected int maxLives;



    public abstract void draw(Graphics2D g);
    public abstract void update();
    public BufferedImage chooseImage(int direction, int counter) {
        BufferedImage image;
        switch (direction) {
            case 0 -> {
                if (counter < 10) {
                    image = loadImage("2");
                    break;
                }
                image = loadImage("3");
            }
            case 1 -> {
                if (counter < 10) {
                    image = loadImage("0");
                    break;
                }
                image = loadImage("1");
            }
            case 2 -> {
                if (counter < 10) {
                    image = loadImage("6");
                    break;
                }
                image = loadImage("7");
            }
            case 3 -> {
                if (counter < 10) {
                    image = loadImage("4");
                    break;
                }
                image = loadImage("5");
            }
            default -> throw new IllegalStateException("Unexpected value for direction: " + direction);
        }
        return image;
    }

    public BufferedImage loadImage(String index) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + defaultImagePath + index + ".png")));
        } catch (IOException e) {
            System.out.println("current index = " + index);
            e.printStackTrace();
        }
        return image;
    }

    public void drawHealthBar(Graphics2D g){
        double scale = (double)(panel.getSquareSide())/maxLives;
        double value = scale*lives;

        int width = panel.getSquareSide();
        int height = 5;
        int x = getRelX() + panel.getSquareSide()/2 - width/2;
        int y = getRelY() - 15;

        g.setColor(new Color(35,35,35));
        g.fillRect(x-2, y-2, width+4,height+4);
        g.setColor(new Color(255, 0, 30));
        g.fillRect(x, y, (int)value, height);
    }

    public void decreaseLives(){
        if(lives -1 >= 0){
            lives--;
        }
    }


    public int getRelX(){ // returns x coordinate relative to player
        return x - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
    }

    public int getRelY(){ // returns y coordinate relative to player
        return y - panel.getPlayer().getY() + panel.getPlayer().getCenterY();
    }

    public Rectangle getRelativeArea(){
        return new Rectangle(getRelX(), getRelY(), 32, 32);
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanMove() {
        return canMove;
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

    public int getLives() {
        return lives;
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
