package world;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class Game {


    ApplicationPanel applicationPanel;
    private int staminaBonus = 0;
    private int healthBonus = 0;
    private int speedBonus = 0;
    private int bonusCounter = 0;
    private AmmoType selectedAmmo;
    HashMap<AmmoType, String> ammoMap = new HashMap<>();
    HashMap<Integer, Integer> bonusMap = new HashMap<>();

    private boolean shooting = false;

    private long lastShootTime = 0;
    private int reloadCounter = 0;

    private int score = 0;

    public Game(ApplicationPanel applicationPanel) {
        this.applicationPanel = applicationPanel;
        selectedAmmo = AmmoType.FIST;
        initializeMaps();
    }


    private void initializeMaps() {
        ammoMap.put(AmmoType.FIST, "0,0,0");
        ammoMap.put(AmmoType.REVOLVER, "6,36,6");
        ammoMap.put(AmmoType.PISTOL, "0,0,9");
        ammoMap.put(AmmoType.SEMIAUTO, "0,0,10");
        ammoMap.put(AmmoType.ASSAULTRIFLE, "0,0,30");
        ammoMap.put(AmmoType.SUBMACHINE_GUN, "0,0,50");

        bonusMap.put(0, 0);
        bonusMap.put(1, 0);
        bonusMap.put(2, 0);
    }

    public void setEntities(int wave) {
        for (int i = 0; i < wave * 3; i++) {
            applicationPanel.getEntities().add(new Zombie(applicationPanel));
        }
    }

    public void updateEntities() {
        Iterator<Entity> iterator = applicationPanel.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update();
            if (entity.getLives() == 0) {
                iterator.remove();
            }
        }

        String[] values = ammoMap.get(selectedAmmo).split(",");
        int mag = Integer.parseInt(values[0]);
        int ammo = Integer.parseInt(values[1]);
        int maxCapacity = Integer.parseInt(values[2]);

        if (cursorOnScreen(applicationPanel.getMousePosition()) && applicationPanel.getMouseInput().isMouseClicked()) {
            simulateShooting(mag, ammo, maxCapacity);
        }

        if (mag == 0 || applicationPanel.getUserInput().isReloadTriggered()) {
            reloadCounter++;
            if (reloadCounter == 100) {
                reload(mag, ammo, maxCapacity);
                reloadCounter = 0;
            }
        }
    }

    public void drawEntities(Graphics2D g) {
        for (Entity entity : applicationPanel.getEntities()) {
            if (entity != null) {
                entity.draw(g);
            }
        }
    }
    public void simulateShooting(int mag, int ammo, int maxCapacity) {
        long currentTime = System.currentTimeMillis();
        Point mousePosition = applicationPanel.getMousePosition();
        shooting = false;
        int delay = shootingDelay();
        changeDirection();
        if (mag > 0 && currentTime - lastShootTime >= delay){
            //applicationPanel.getEffectManager().addGroundParticles(mousePosition.x, mousePosition.y);
            shooting = true;
            mag--;
            Point clickPoint = new Point(mousePosition.x, mousePosition.y);
            for (Entity entity : applicationPanel.getEntities()) {
                if (entity.getHitBoxArea().contains(clickPoint)) {
                    applicationPanel.getEffectManager().addHitParticles(mousePosition.getX(), mousePosition.getY());
                    applicationPanel.getEffectManager().addFlashingEffect(entity);
                    entity.decreaseLives();
                    score += 5;
                }
            }
            lastShootTime = currentTime;
            String text = mag + "," + ammo + "," + maxCapacity;
            ammoMap.put(selectedAmmo, text);
        }
    }

    private boolean cursorOnScreen(Point mousePosition) {
        return mousePosition != null && applicationPanel.contains(mousePosition);
    }

    public void changeDirection() {
        Point mousePosition = applicationPanel.getMousePosition();
        double xLeft = mousePosition.getX() * ((double) applicationPanel.getRow() / applicationPanel.getCol());
        double xRight = (applicationPanel.getWidth() - mousePosition.getX()) * ((double) applicationPanel.getRow() / applicationPanel.getCol());

        if (mousePosition.getY() <= applicationPanel.getPlayer().getCenterY()) {
            applicationPanel.getPlayer().setDirection(0);
            if (xLeft <= mousePosition.getY()) {
                applicationPanel.getPlayer().setDirection(2);
            } else if (xRight <= mousePosition.getY()) {
                applicationPanel.getPlayer().setDirection(3);
            }
        } else {
            applicationPanel.getPlayer().setDirection(1);
            if (xLeft <= (applicationPanel.getHeight() - mousePosition.getY())) {
                applicationPanel.getPlayer().setDirection(2);
            } else if (xRight <= (applicationPanel.getHeight() - mousePosition.getY())) {
                applicationPanel.getPlayer().setDirection(3);
            }
        }
    }

    public int shootingDelay() {
        int delay = 0;
        switch (selectedAmmo) {
            case REVOLVER -> delay = 1000;
            case PISTOL -> delay = 300;
            case SEMIAUTO -> delay = 150;
            case ASSAULTRIFLE -> delay = 100;
            case SUBMACHINE_GUN -> delay = 75;
        }
        return delay;
    }


    public void reload(int mag, int ammo, int maxCapacity) {
        if (ammo >= maxCapacity) {
            if (applicationPanel.getUserInput().isReloadTriggered()) {
                ammo += mag;
                applicationPanel.getUserInput().setReloadTriggered(false);
            }
            mag = maxCapacity;
            ammo -= maxCapacity;
        } else {
            mag = ammo;
            ammo = 0;
        }
        String text = mag + "," + ammo + "," + maxCapacity;
        ammoMap.put(selectedAmmo, text);
    }


    public void addBonus(int type) {
        if (bonusCounter < 10) {
            int currentBonus = bonusMap.get(type);
            if (currentBonus < 10) {
                bonusMap.put(type, currentBonus + 1);
                bonusCounter++;
            }
        }
    }

    public void subtractBonus(int type) {
        int currentBonus = bonusMap.get(type);
        if (currentBonus > 0) {
            bonusMap.put(type, currentBonus - 1);
            bonusCounter--;
        }
    }

    public void setSelectedAmmo(AmmoType selectedAmmo) {
        this.selectedAmmo = selectedAmmo;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHealthBonus() {
        return bonusMap.get(0);
    }


    public int getStaminaBonus() {
        return bonusMap.get(1);
    }

    public int getSpeedBonus() {
        return bonusMap.get(2);
    }

    public String getAmmo() {
        return ammoMap.get(selectedAmmo).split(",")[1];
    }

    public String getMagazine() {
        return ammoMap.get(selectedAmmo).split(",")[0];
    }

    public AmmoType getSelectedAmmo() {
        return selectedAmmo;
    }

    public int getScore() {
        return score;
    }

    public HashMap<AmmoType, String> getAmmoMap() {
        return ammoMap;
    }

    public boolean isShooting() {
        return shooting;
    }
}