package logic;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class Game {
    ApplicationPanel panel;
    private int bonusCounter = 0;
    private Weapon selectedWeapon;
    HashMap<Weapon, String> ammoMap = new HashMap<>();
    HashMap<Integer, Integer> bonusMap = new HashMap<>();
    private long lastShootTime = 0;
    private int reloadCounter = 0;
    private int score = 0;
    public Game(ApplicationPanel panel) {
        this.panel = panel;
        selectedWeapon = Weapon.FIST;
        initializeMaps();
    }

    /**
     * Initializes HashMaps containing information about players weapons and bonuses.
     */

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
     * (Re)sets the entities ArrayList
     * Explanation: When player kills all hostile entities, new wave will start.
     * Each wave, +3 entities will be spawned unless wave * 3 > 18.
     * If, indeed, wave * 3 > 18, the program will add only 18 entities to the entities ArrayList.
     * This way it is ensured that won't preform too badly
     *
     * @param wave current wave
     */
    public void setEntities(int wave) {
        int maxEntities = Math.min(wave * 3, 18);
        for (int i = 0; i < maxEntities; i++) {
            panel.getEntities().add(new Zombie(panel));
        }
    }

    /**
     * Updates the game.
     * Updates all entities.
     * Simulates players attack (if the player has clicked with his mouse).
     * Simulates reloading of players selected weapon (if specific conditions are met).
     */
    public void updatedGame() {
        Iterator<Entity> iterator = panel.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update();

            if (entity.getLives() == 0) {
                iterator.remove();
            }
        }

        String[] values = ammoMap.get(selectedWeapon).split(",");
        int mag = Integer.parseInt(values[0]);
        int ammo = Integer.parseInt(values[1]);
        int maxCapacity = Integer.parseInt(values[2]);

        if (cursorOnScreen(panel.getMousePosition()) && panel.getMouseInput().isMouseClicked() && reloadCounter == 0) {
            attackEntities(mag, ammo, maxCapacity);
        }

        if ((mag == 0 || panel.getUserInput().isReloadTriggered()) && selectedWeapon != Weapon.FIST && ammo != 0) {
            reloadCounter++;
            if (reloadCounter == 1) {
                panel.getEffectManager().addReloadingEffect();
            }
            if (reloadCounter == 100) {
                reload(mag, ammo, maxCapacity);
                reloadCounter = 0;
            }
        }
    }

    /**
     * Draws all entities on the screen.
     *
     * @param g Graphics2D (so the entity can be drawn on screen)
     */
    public void drawEntities(Graphics2D g) {
        for (Entity entity : panel.getEntities()) {
            if (entity != null) {
                entity.draw(g);
            }
        }
    }

    /**
     * Simulates players attack on one (or more) of the hostile entities.
     * Explanation: If there is any ammo left in the magazine and a specific time has passed (delay), program allows the player to shoot/punch.
     * If player "hit" an entity, the program will automatically decrease the lives of this entity.
     *
     * @param mag         the current number of bullets in magazine of players selected weapon
     * @param ammo        the amount of ammo left
     * @param maxCapacity the maximal capacity of bullets in one magazine (of this specific selected weapon)
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
            }
            if (selectedWeapon == Weapon.FIST) {
                panel.getEffectManager().addPunchingEffect(panel.getPlayer().getDirection());
            }

            Point clickPoint = new Point(mousePosition.x, mousePosition.y);
            for (Entity entity : panel.getEntities()) {
                if (selectedWeapon == Weapon.FIST && panel.getCollisionManager().entityInRange(entity, panel.getPlayer())) {
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

    /**
     * Adds specific particles based on which tile has been hit.
     *
     * @param x cursors x coordinate on the screen
     * @param y cursors y coordinate on the screen
     */

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
     * Changes the direction of player when aiming.
     * Explanation: The program will notionally divide the screen into 4 segments.
     * This division can be imagined as dividing a rectangle (screen) with its diagonals.
     * The direction of the player will change depending on the sector in which the cursor is located.
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
     * Initializes delay based on selected weapon.
     * It returns the number of loops the player will have to "wait" till he can fire his weapon again.
     *
     * @return the specific delay
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
     * Simulates reloading of a weapon.
     *
     * @param mag         the current number of bullets in weapons magazine
     * @param ammo        the ammo left
     * @param maxCapacity the max capacity of one magazine
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
     * Increases the value of the selected bonus.
     *
     * @param type specific bonus to be increased.
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
     * Decreases the value of the selected bonus.
     *
     * @param type specific bonus to be decreased
     */
    public void subtractBonus(int type) {
        int currentBonus = bonusMap.get(type);
        if (currentBonus > 0) {
            bonusMap.put(type, currentBonus - 1);
            bonusCounter--;
        }
    }
    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
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
        return ammoMap.get(selectedWeapon).split(",")[1];
    }
    public String getMagazine() {
        return ammoMap.get(selectedWeapon).split(",")[0];
    }
    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }
    public int getScore() {
        return score;
    }
    public HashMap<Weapon, String> getAmmoMap() {
        return ammoMap;
    }
}