package world;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.Iterator;

public class Game {

    world.Panel panel;
    private int staminaBonus = 0;
    private int healthBonus = 0;
    private int speedBonus = 0;

    private int bonusCounter = 0;

    private int magazine = 9;
    private int ammo = 27;

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
            if (entity.getLives() == 0) {
                iterator.remove(); // Odstranit entitu, pokud nemá životy
            }
        }

        // Simulace střelby na všechny entity
        simulateShooting();
    }

    public void drawEntities(Graphics2D g) {
        for (Entity entity : panel.getEntities()) {
            if (entity != null) {
                entity.draw(g); // Kreslit každou entitu
            }
        }
    }

    private long lastShootTime = 0;

    public void simulateShooting() {
        long currentTime = System.currentTimeMillis();

        Point mousePosition = panel.getMousePosition();
        if (mousePosition == null || !panel.contains(mousePosition) || panel.getPlayer().getLives() <= 0) {
            return;
        }

        if (magazine > 0 && panel.getMouseInput().isMouseClicked() && (currentTime - lastShootTime >= 500)) {
            magazine--;
            Point clickPoint = new Point(mousePosition.x, mousePosition.y);
            for (Entity entity : panel.getEntities()) {
                if (entity.getHitBoxArea().contains(clickPoint)) {
                    entity.decreaseLives();
                }
            }
            lastShootTime = currentTime;
        }
        if (magazine == 0) {
            reload();
        }
    }

    public void reload() {
        if (ammo >= 9) {
            ammo -= 9;
            magazine = 9;
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

    public int getAmmo() {
        return ammo;
    }

    public int getMagazine() {
        return magazine;
    }
}
