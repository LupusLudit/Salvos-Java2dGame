package entities;

import management.CollisionManager;

import java.awt.*;
import java.util.Random;

public class Zombie extends Entity {
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

        chooseSpawnPoint();
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
        drawBar(g, maxLives, lives, getRelY() - 15, new Color(255, 0, 30));
    }

    @Override
    public void update() {
        updateDelay++;
        if (updateDelay == 3) {
            updateDirection();
            canMove = !collisionManager.checkTileCollision(this, panel) && !allEntitiesCollision(); //temporarily added for testing
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

    @Override
    public void drawBar(Graphics2D g, int max, int current, int y, Color color) {
        double scale = (double) (panel.getSquareSide()) / max;
        double value = scale * current;

        int width = panel.getSquareSide();
        int height = 5;
        int x = getRelX() + panel.getSquareSide() / 2 - width / 2;

        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 2, 2);
        g.setColor(color);
        g.fillRoundRect(x, y, (int) value, height, 2, 2);
    }

    public void chooseSpawnPoint() {
        Random rn = new Random();
        boolean canSpawn = false;
        while (!canSpawn) {
            int xBound = rn.nextInt(4);
            switch (xBound) {
                case 0 -> {
                    x = (rn.nextInt(6) + 40) * panel.getSquareSide();
                    y = (rn.nextInt(6) + 35) * panel.getSquareSide();
                }
                case 1 -> {
                    x = (rn.nextInt(6) + 45) * panel.getSquareSide();
                    y = (rn.nextInt(6) + 40) * panel.getSquareSide();
                }
                case 2 -> {
                    x = (rn.nextInt(6) + 40) * panel.getSquareSide();
                    y = (rn.nextInt(6) + 45) * panel.getSquareSide();
                }
                case 3 -> {
                    x = (rn.nextInt(6) + 35) * panel.getSquareSide();
                    y = (rn.nextInt(6) + 40) * panel.getSquareSide();
                }
            }

            canSpawn = true;
            for (Entity entity : panel.getEntities()) {
                if (entity != this) {
                    if (x / panel.getSquareSide() == entity.getX() / panel.getSquareSide() && y / panel.getSquareSide() == entity.getY() / panel.getSquareSide()) {
                        canSpawn = false;
                        break;
                    }
                }
            }
        }
    }

}
