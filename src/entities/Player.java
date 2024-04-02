package entities;

import management.CollisionManager;
import management.UserInput;
import java.awt.*;

public class Player extends Entity {

    UserInput userInput;
    world.Panel panel;
    private final int centerX;
    private final int centerY;

    CollisionManager collisionManager;

    Zombie testZombie;


    public Player(UserInput userInput, world.Panel panel, Zombie zombie) {
        defaultImagePath = "character/sprite_";
        this.userInput = userInput;
        this.panel = panel;

        speed = 6;
        canMove = true;

        this.x = 45*panel.getTileSide();
        this.y = 45*panel.getTileSide();

        centerX = (panel.getTileSide() * panel.getCol()) / 2 - (panel.getTileSide() / 2);
        centerY = (panel.getTileSide() * panel.getRow()) / 2 - (panel.getTileSide() / 2);

        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
        this.collisionManager = new CollisionManager();

        testZombie = zombie;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(chooseImage(direction, counter), centerX, centerY, null);
    }

    @Override
    public void update() {
        direction = userInput.getDirection();
        if (userInput.isPressed()) {
            canMove = collisionManager.checkTileCollision(this, panel) && !collisionManager.checkEntityCollision(this, testZombie);
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

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }


}
