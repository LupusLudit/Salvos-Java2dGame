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
    private Weapons currentWeapon;
    HashMap<Weapons, String> weaponAmmoMap = new HashMap<>();
    HashMap<Integer, Integer> bonusMap = new HashMap<>();

    public Game(Panel panel) {
        this.panel = panel;
        currentWeapon = Weapons.PISTOL;
        initializeMaps();
    }


    private void initializeMaps(){
        weaponAmmoMap.put(Weapons.PISTOL, "9,27,9");
        weaponAmmoMap.put(Weapons.SEMIAUTO, "8,32,8");
        weaponAmmoMap.put(Weapons.ASSAULTRIFLE, "30,90,30");

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
    public void simulateShooting() {
        long currentTime = System.currentTimeMillis();
        Point mousePosition = panel.getMousePosition();

        if(canShoot(mousePosition)){
            int delay = shootingDelay();
            String[] values = weaponAmmoMap.get(currentWeapon).split(",");
            int mag = Integer.parseInt(values[0]);
            int ammo = Integer.parseInt(values[1]);
            int maxCapacity = Integer.parseInt(values[2]);

            if (mag > 0 && panel.getMouseInput().isMouseClicked() && (currentTime - lastShootTime >= delay)) {
                mag--;
                Point clickPoint = new Point(mousePosition.x, mousePosition.y);
                for (Entity entity : panel.getEntities()) {
                    if (entity.getHitBoxArea().contains(clickPoint)) {
                        entity.decreaseLives();
                    }
                }
                lastShootTime = currentTime;
                String text = mag + "," + ammo + "," + maxCapacity;
                weaponAmmoMap.put(currentWeapon, text);
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
        switch (currentWeapon){
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
        weaponAmmoMap.put(currentWeapon, text);
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

    public void setCurrentWeapon(Weapons currentWeapon) {
        this.currentWeapon = currentWeapon;
    }public int getHealthBonus() {
        return bonusMap.get(0);
    }


    public int getStaminaBonus() {
        return bonusMap.get(1);
    }

    public int getSpeedBonus() {
        return bonusMap.get(2);
    }

    public String getAmmo() {
        return weaponAmmoMap.get(currentWeapon).split(",")[1];
    }

    public String getMagazine() {
        return weaponAmmoMap.get(currentWeapon).split(",")[0];
    }

    public Weapons getCurrentWeapon() {
        return currentWeapon;
    }
}