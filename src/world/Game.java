package world;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class Game {



    world.Panel panel;
    private int staminaBonus = 0;
    private int healthBonus = 0;
    private int speedBonus = 0;
    private int bonusCounter = 0;
    private AmmoType selectedAmmo;
    HashMap<AmmoType, String> ammoMap = new HashMap<>();
    HashMap<Integer, Integer> bonusMap = new HashMap<>();

    public Game(Panel panel) {
        this.panel = panel;
        selectedAmmo = AmmoType.FIST;
        initializeMaps();
    }


    private void initializeMaps(){
        ammoMap.put(AmmoType.PISTOL, "9,27,9");
        ammoMap.put(AmmoType.SEMIAUTO, "0,0,8");
        ammoMap.put(AmmoType.ASSAULTRIFLE, "0,0,30");
        ammoMap.put(AmmoType.FIST, "0,0,0");

        bonusMap.put(0, 0);
        bonusMap.put(1, 0);
        bonusMap.put(2, 0);
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
                iterator.remove();
            }
        }
        simulateShooting();
    }

    public void drawEntities(Graphics2D g) {
        for (Entity entity : panel.getEntities()) {
            if (entity != null) {
                entity.draw(g);
            }
        }
    }

    private long lastShootTime = 0;
    private int reloadCounter = 0;

    private int score = 0;
    public void simulateShooting() {
        long currentTime = System.currentTimeMillis();
        Point mousePosition = panel.getMousePosition();

        if(canShoot(mousePosition)){
            int delay = shootingDelay();
            String[] values = ammoMap.get(selectedAmmo).split(",");
            int mag = Integer.parseInt(values[0]);
            int ammo = Integer.parseInt(values[1]);
            int maxCapacity = Integer.parseInt(values[2]);

            if (mag > 0 && panel.getMouseInput().isMouseClicked() && (currentTime - lastShootTime >= delay)) {
                mag--;
                Point clickPoint = new Point(mousePosition.x, mousePosition.y);
                for (Entity entity : panel.getEntities()) {
                    if (entity.getHitBoxArea().contains(clickPoint)) {
                        entity.decreaseLives();
                        score += 5;
                    }
                }
                lastShootTime = currentTime;
                String text = mag + "," + ammo + "," + maxCapacity;
                ammoMap.put(selectedAmmo, text);
            }
            if (mag == 0) {
                reloadCounter++;
                if(reloadCounter == 100){
                    reload(ammo, maxCapacity);
                    reloadCounter = 0;
                }
            }
        }
    }

    private boolean canShoot(Point mousePosition) {
        return mousePosition != null && panel.contains(mousePosition) && panel.getPlayer().getLives() > 0;
    }

    public int shootingDelay(){
        int delay = 0;
        switch (selectedAmmo){
            case PISTOL -> delay = 500;
            case SEMIAUTO -> delay = 200;
            case ASSAULTRIFLE -> delay = 50;
        }
        return delay;
    }


    public void reload(int ammo, int maxCapacity) {
        int mag;
        if(ammo >= maxCapacity){
            ammo -= maxCapacity;
            mag = maxCapacity;
        }
        else {
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
}