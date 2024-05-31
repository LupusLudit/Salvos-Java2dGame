package logic;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The type Game.
 */
public class Game {
    /**
     * The Panel.
     */
    ApplicationPanel panel;
    private int bonusCounter = 0;
    private Weapon selectedWeapon;
    /**
     * The Ammo map.
     */
    HashMap<Weapon, String> ammoMap = new HashMap<>();
    /**
     * The Bonus map.
     */
    HashMap<Integer, Integer> bonusMap = new HashMap<>();
    private long lastShootTime = 0;
    private int reloadCounter = 0;

    private int score = 0;

    /**
     * Instantiates a new Game.
     *
     * @param applicationPanel the application panel
     */
    public Game(ApplicationPanel applicationPanel) {
        this.panel = applicationPanel;
        selectedWeapon = Weapon.FIST;
        initializeMaps();
    }


    private void initializeMaps() {
        ammoMap.put(Weapon.FIST, "1,0,0");
        ammoMap.put(Weapon.REVOLVER, "6,36,6");
        ammoMap.put(Weapon.PISTOL, "0,0,9");
        ammoMap.put(Weapon.SEMIAUTO, "0,0,10");
        ammoMap.put(Weapon.ASSAULTRIFLE, "0,0,30");
        ammoMap.put(Weapon.SUBMACHINE_GUN, "0,0,50");

        bonusMap.put(0, 0);
        bonusMap.put(1, 0);
        bonusMap.put(2, 0);
    }

    /**
     * Sets entities.
     *
     * @param wave the wave
     */
    public void setEntities(int wave) {
        int maxEntities = Math.min(wave * 3, 18);
        for (int i = 0; i < maxEntities; i++) {
            panel.getEntities().add(new Zombie(panel));
        }
    }

    /**
     * Update entities.
     */
    public void updateEntities() {
        String[] values = ammoMap.get(selectedWeapon).split(",");
        int mag = Integer.parseInt(values[0]);
        int ammo = Integer.parseInt(values[1]);
        int maxCapacity = Integer.parseInt(values[2]);

        Iterator<Entity> iterator = panel.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update();

            if (entity.getLives() == 0) {
                iterator.remove();
            }
        }

        if (cursorOnScreen(panel.getMousePosition()) && panel.getMouseInput().isMouseClicked() && reloadCounter == 0) {
            attackEntities(mag, ammo, maxCapacity);
        }

        if ((mag == 0 || panel.getUserInput().isReloadTriggered()) && selectedWeapon != Weapon.FIST && ammo!= 0) {
            reloadCounter++;
            if (reloadCounter == 1){
                panel.getEffectManager().addReloadingEffect();
            }
            if (reloadCounter == 100) {
                reload(mag, ammo, maxCapacity);
                reloadCounter = 0;
            }
            System.out.println(reloadCounter);

        }
    }

    /**
     * Draw entities.
     *
     * @param g the g
     */
    public void drawEntities(Graphics2D g) {
        for (Entity entity : panel.getEntities()) {
            if (entity != null) {
                entity.draw(g);
            }
        }
    }

    /**
     * Attack entities.
     *
     * @param mag         the mag
     * @param ammo        the ammo
     * @param maxCapacity the max capacity
     */
    public void attackEntities(int mag, int ammo, int maxCapacity) {
        long currentTime = System.currentTimeMillis();
        Point mousePosition = panel.getMousePosition();
        int delay = shootingDelay();
        changeDirection();

        if (mag > 0 && currentTime - lastShootTime >= delay) {
            if (selectedWeapon != Weapon.FIST) {
                panel.getEffectManager().addBlastingEffect(panel.getPlayer().getDirection());
                addParticles(mousePosition.x, mousePosition.y);
                mag--;
            }if (selectedWeapon == Weapon.FIST ){
                panel.getEffectManager().addPunchingEffect(panel.getPlayer().getDirection());
            }
            Point clickPoint = new Point(mousePosition.x, mousePosition.y);
            for (Entity entity : panel.getEntities()) {
                if (selectedWeapon == Weapon.FIST && panel.getCollisionManager().checkAdjutantTiles(entity, panel.getPlayer())) {
                    entity.decreaseLives();
                    score += 5;
                } else if (selectedWeapon != Weapon.FIST && entity.getHitBoxArea().contains(clickPoint)) {
                    entity.decreaseLives();
                    score += 5;
                    panel.getEffectManager().addHitParticles(mousePosition.getX(), mousePosition.getY());
                }
            }
            lastShootTime = currentTime;
            String text = mag + "," + ammo + "," + maxCapacity;
            ammoMap.put(selectedWeapon, text);
        }
    }
    private void addParticles(int x, int y) {
        int col = (x + panel.getPlayer().getX() - panel.getPlayer().getCenterX()) / panel.getSquareSide();
        int row = (y + panel.getPlayer().getY() - panel.getPlayer().getCenterY()) / panel.getSquareSide();
        int index = panel.getTilePainter().getTile(col, row).getImageIndex();

        if (index == 14 || index == 18 || index == 19) {
            panel.getEffectManager().addRadioactiveParticles(x, y);
        } else if (index < 14 || index == 15 || index == 16) {
            panel.getEffectManager().addRockParticles(x, y);
        } else {
            panel.getEffectManager().addGroundParticles(x, y);
        }
    }

    private boolean cursorOnScreen(Point mousePosition) {
        return mousePosition != null && panel.contains(mousePosition);
    }

    /**
     * Change direction.
     */
    public void changeDirection() {
        Point mousePosition = panel.getMousePosition();
        double xLeft = mousePosition.getX() * ((double) panel.getRow() / panel.getCol());
        double xRight = (panel.getWidth() - mousePosition.getX()) * ((double) panel.getRow() / panel.getCol());

        if (mousePosition.getY() <= panel.getPlayer().getCenterY()) {
            panel.getPlayer().setDirection(0);
            if (xLeft <= mousePosition.getY()) {
                panel.getPlayer().setDirection(2);
            } else if (xRight <= mousePosition.getY()) {
                panel.getPlayer().setDirection(3);
            }
        } else {
            panel.getPlayer().setDirection(1);
            if (xLeft <= (panel.getHeight() - mousePosition.getY())) {
                panel.getPlayer().setDirection(2);
            } else if (xRight <= (panel.getHeight() - mousePosition.getY())) {
                panel.getPlayer().setDirection(3);
            }
        }
    }

    /**
     * Shooting delay int.
     *
     * @return the int
     */
    public int shootingDelay() {
        int delay = 0;
        switch (selectedWeapon) {
            case FIST -> delay = 750;
            case REVOLVER -> delay = 1000;
            case PISTOL -> delay = 300;
            case SEMIAUTO -> delay = 150;
            case ASSAULTRIFLE -> delay = 100;
            case SUBMACHINE_GUN -> delay = 75;
        }
        return delay;
    }


    /**
     * Reload.
     *
     * @param mag         the mag
     * @param ammo        the ammo
     * @param maxCapacity the max capacity
     */
    public void reload(int mag, int ammo, int maxCapacity) {
        if (ammo >= maxCapacity) {
            if (panel.getUserInput().isReloadTriggered()) {
                ammo += mag;
                panel.getUserInput().setReloadTriggered(false);
            }
            mag = maxCapacity;
            ammo -= maxCapacity;
        } else {
            mag = ammo;
            ammo = 0;
        }
        String text = mag + "," + ammo + "," + maxCapacity;
        ammoMap.put(selectedWeapon, text);
    }


    /**
     * Add bonus.
     *
     * @param type the type
     */
    public void addBonus(int type) {
        if (bonusCounter < 10) {
            int currentBonus = bonusMap.get(type);
            if (currentBonus < 10) {
                bonusMap.put(type, currentBonus + 1);
                bonusCounter++;
            }
        }
    }

    /**
     * Subtract bonus.
     *
     * @param type the type
     */
    public void subtractBonus(int type) {
        int currentBonus = bonusMap.get(type);
        if (currentBonus > 0) {
            bonusMap.put(type, currentBonus - 1);
            bonusCounter--;
        }
    }

    /**
     * Sets selected weapon.
     *
     * @param selectedWeapon the selected weapon
     */
    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets health bonus.
     *
     * @return the health bonus
     */
    public int getHealthBonus() {
        return bonusMap.get(0);
    }


    /**
     * Gets stamina bonus.
     *
     * @return the stamina bonus
     */
    public int getStaminaBonus() {
        return bonusMap.get(1);
    }

    /**
     * Gets speed bonus.
     *
     * @return the speed bonus
     */
    public int getSpeedBonus() {
        return bonusMap.get(2);
    }

    /**
     * Gets ammo.
     *
     * @return the ammo
     */
    public String getAmmo() {
        return ammoMap.get(selectedWeapon).split(",")[1];
    }

    /**
     * Gets magazine.
     *
     * @return the magazine
     */
    public String getMagazine() {
        return ammoMap.get(selectedWeapon).split(",")[0];
    }

    /**
     * Gets selected weapon.
     *
     * @return the selected weapon
     */
    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets ammo map.
     *
     * @return the ammo map
     */
    public HashMap<Weapon, String> getAmmoMap() {
        return ammoMap;
    }
}