package world;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.Iterator;

public class Game {

    //spaw delay will be added
    world.Panel panel;
    private int staminaBonus = 0;
    private int healthBonus = 0;
    private int speedBonus = 0;

    private int bonusCounter = 0;

    public Game(Panel panel) {
        this.panel = panel;
    }

    public void setEntities(int wave) {
        for (int i = 0; i < wave * 3; i++) {
            panel.getEntities().add(new Zombie(panel));
        }
    }

    public void updateEntities() {
        Iterator<Entity> iterator = panel.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update();
            simulateShooting(entity);
            if (entity.getLives() == 0) {
                iterator.remove();
            }
        }
    }

    public void drawEntities(Graphics2D g) {
        for (Entity entity : panel.getEntities()) {
            if (entity != null) {
                entity.draw(g);
            }
        }
    }

    private long lastShootTime = 0;

    public void simulateShooting(Entity entity) {
        int x;
        int y;

        long currentTime = System.currentTimeMillis();
        int shootingDelay = 800;

        Point mousePosition = panel.getMousePosition();
        if (mousePosition == null || !panel.contains(mousePosition) || currentTime - lastShootTime < shootingDelay) {
            return;
        }

        if (panel.getPlayer().getLives() > 0) {
            x = mousePosition.x;
            y = mousePosition.y;

            Point clickPoint = new Point(x, y);

            if (panel.getMouseInput().isMouseClicked() && entity.getHitBoxArea().contains(clickPoint)) {
                entity.decreaseLives();

                lastShootTime = currentTime;
            }
        }
    }


    public void addBonus(int type) {
        switch (type) {
            case 0 -> {
                if (healthBonus + 1 < 11 && bonusCounter < 10) {
                    healthBonus++;
                    bonusCounter++;
                }
            }
            case 1 -> {
                if (staminaBonus + 1 < 11 && bonusCounter < 10) {
                    staminaBonus++;
                    bonusCounter++;
                }
            }
            case 2 -> {
                if (speedBonus + 1 < 11 && bonusCounter < 10) {
                    speedBonus++;
                    bonusCounter++;
                }
            }
        }
    }

    public void subtractBonus(int type) {
        switch (type) {
            case 0 -> {
                if (healthBonus - 1 >= 0) {
                    healthBonus--;
                    bonusCounter--;
                }
            }
            case 1 -> {
                if (staminaBonus - 1 >= 0) {
                    staminaBonus--;
                    bonusCounter--;
                }
            }
            case 2 -> {
                if (speedBonus - 1 >= 0) {
                    speedBonus--;
                    bonusCounter--;
                }
            }
        }
    }

    public int getStaminaBonus() {
        return staminaBonus;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }
}