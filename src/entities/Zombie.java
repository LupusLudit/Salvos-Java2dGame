package entities;

import management.CollisionManager;
import world.ApplicationPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Zombie extends Entity {

    public Zombie(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        defaultImagePath = "entities/zombie_";
        this.panel = applicationPanel;
        this.collisionManager = panel.getCollisionManager();

        speed = 3;
        canMove = true;
        lives = 5;
        maxLives = 5;
        direction = 0;
        counter = 0;

        chooseSpawnPoint();
        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
        currentImage = loadImage("idle");
    }

    @Override
    public void draw(Graphics2D g) {
        changeCurrentImage(counter);
        if (Math.abs(panel.getPlayer().getX() - x) < panel.getPlayer().getCenterX() + panel.getSquareSide()
                && Math.abs(panel.getPlayer().getY() - y) < panel.getPlayer().getCenterY() + panel.getSquareSide()) { //is in range control
            g.drawImage(currentImage, getRelX(panel.getPlayer()), getRelY(panel.getPlayer()), null);
        }
        drawBar(g, maxLives, lives, getRelY(panel.getPlayer()) - 15, new Color(255, 0, 30));
    }

    @Override
    public void update() {
        pathUpdateCounter++;
        if (pathUpdateCounter % 4 == 0){
            updatePath((panel.getPlayer().getX() + 24)/panel.getSquareSide(), (panel.getPlayer().getY() + 32)/panel.getSquareSide());
        }

        canMove = !collisionManager.checkTileCollision(this) && !allEntitiesCollision();
        if (canMove) {
            switch (direction) {
                case 0 -> y -= speed;
                case 1 -> y += speed;
                case 2 -> x -= speed;
                case 3 -> x += speed;
            }

            actualArea.setRect(x + 8, y + 16, 32, 32);
        }
        counter++;
        if (counter == 29 && canMove) {
            this.counter = 0;
        }
    }
    @Override
    public void changeCurrentImage(int counter) {
        if (!canMove){
            currentImage = loadImage("idle");
        }
        else if (counter != 0 && counter % 7 == 0) {
            imageIndex++;
            if (imageIndex == 4) {
                imageIndex = 0;
            }
            currentImage =  loadImage(String.valueOf(imageIndex));
        }
    }
    @Override
    public void drawBar(Graphics2D g, int max, int current, int y, Color color) {
        double scale = (double) (panel.getSquareSide()) / max;
        double value = scale * current;

        int width = panel.getSquareSide();
        int height = 5;
        int x = getRelX(panel.getPlayer()) + panel.getSquareSide() / 2 - width / 2;

        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 2, 2);
        g.setColor(color);
        g.fillRoundRect(x, y, (int) value, height, 2, 2);
    }

    public void chooseSpawnPoint() {
        Random rn = new Random();
        boolean canSpawn = false;
        while (!canSpawn) {
            int xBound = rn.nextInt(3);
            switch (xBound) {
                case 0 -> {
                    x = (rn.nextInt(5) + 27) * panel.getSquareSide();
                    y = (rn.nextInt(5) + 24) * panel.getSquareSide();
                }
                case 1 -> {
                    x = (rn.nextInt(5) + 63) * panel.getSquareSide();
                    y = (rn.nextInt(5) + 30) * panel.getSquareSide();
                }
                case 2 -> {
                    x = (rn.nextInt(5) + 28) * panel.getSquareSide();
                    y = (rn.nextInt(5) + 68) * panel.getSquareSide();
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
