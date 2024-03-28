package entities;

import management.CollisionManager;
import management.UserInput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    UserInput userInput;
    world.Panel panel;
    private final int centerX;
    private final int centerY;

    CollisionManager collisionManager = new CollisionManager();


    public Player(UserInput userInput, world.Panel panel) {
        this.userInput = userInput;
        this.speed = 6;
        this.panel = panel;
        this.canMove = true;
        this.x = 45*48;
        this.y = 45*48;

        centerX = (panel.getTileSide() * panel.getCol()) / 2 - (panel.getTileSide() / 2);
        centerY = (panel.getTileSide() * panel.getRow()) / 2 - (panel.getTileSide() / 2);
        this.actualArea = new Rectangle(x + 8, y + 16, 48, 48);

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(chooseImage(userInput.getDirection(), counter), centerX, centerY, null);
    }

    @Override
    public void update() {
        direction = userInput.getDirection();
        if (userInput.isPressed()) {
            canMove = collisionManager.checkTileCollision(this, panel);
            if (canMove) {
                switch (direction) {
                    case 0 -> y -= speed;
                    case 1 -> y += speed;
                    case 2 -> x -= speed;
                    case 3 -> x += speed;
                }

                counter++;
                if (counter >= 20) {
                    this.counter = 0;
                }

                actualArea.setRect(x + 8, y + 16, 32, 32);
            }
        }

    }

    @Override
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
            default -> throw new IllegalStateException("Unexpected value for dirrection: " + direction);
        }
        return image;
    }

    public BufferedImage loadImage(String index) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/character/sprite_" + index + ".png")));
        } catch (IOException e) {
            System.out.println("current index = " + index);
            e.printStackTrace();
        }
        return image;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }


}
