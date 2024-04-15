package entities;

import management.CollisionManager;

import java.awt.*;

public class Zombie extends Entity {

    CollisionManager collisionManager;
    private int updateDelay; //so the zombie doesn't update too quickly

    public Zombie(world.Panel panel) {
        defaultImagePath = "zombie/zombie-sprite_";
        this.panel = panel;

        speed = 7;
        canMove = true;
        lives = 5;
        maxLives = 5;
        direction = 0;
        counter = 0;
        x = 47 * panel.getSquareSide();
        y = 47 * panel.getSquareSide();

        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
        this.collisionManager = new CollisionManager();
    }

    public void updateDirection() {
        int dx = panel.getPlayer().getX() - x;
        int dy = panel.getPlayer().getY() - y;

        if (Math.abs(dx) >= Math.abs(dy)) {
            direction = 2;
            if (dx > 0) {
                direction = 3;
            }
        } else {
            direction = 0;
            if (dy > 0) {
                direction = 1;
            }
        }

    }

    @Override
    public void draw(Graphics2D g) {
        if (Math.abs(panel.getPlayer().getX() - x) < panel.getPlayer().getCenterX() + panel.getSquareSide()
                && Math.abs(panel.getPlayer().getY() - y) < panel.getPlayer().getCenterY() + panel.getSquareSide()) { //is in range control
            g.drawImage(chooseImage(direction, counter), getRelX(), getRelY(), null);
        }
        drawHealthBar(g);
    }

    @Override
    public void update() {
        updateDelay++;
        if (updateDelay == 3) {
            updateDirection();
            canMove = !collisionManager.checkTileCollision(this, panel) && !collisionManager.checkEntityCollision(this, panel.getPlayer()); //temporarily added for testing
            if (canMove) {
                switch (direction) {
                    case 0 -> y -= speed;
                    case 1 -> y += speed;
                    case 2 -> x -= speed;
                    case 3 -> x += speed;
                }

                actualArea.setRect(x + 8, y + 16, 32, 32);
            }
            updateDelay = 0;
        }
        counter++;
        if (counter >= 15 && canMove) {
            this.counter = 0;
        }

    }
    public void chooseSpawnPoint() {
    }

}
