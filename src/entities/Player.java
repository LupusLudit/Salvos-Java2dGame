package entities;

import management.Clock;
import management.CollisionManager;
import management.UserInput;
import world.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Player extends Entity {

    UserInput userInput;
    world.Panel panel;
    private final int centerX;
    private final int centerY;

    private int stamina;
    private int maxStamina;

    private int staminaCounter = 0;
    private int hitCounter = 0;
    boolean isASRunning = false;
    HashMap<Item, Integer> inventory = new HashMap<>();

    public Player(UserInput userInput, world.Panel panel) {
        defaultImagePath = "character/sprite_";
        this.userInput = userInput;
        this.panel = panel;
        this.collisionManager = new CollisionManager();

        canMove = true;
        setBonuses();

        this.x = 45 * panel.getSquareSide();
        this.y = 45 * panel.getSquareSide();
        centerX = (panel.getSquareSide() * panel.getCol()) / 2 - (panel.getSquareSide() / 2);
        centerY = (panel.getSquareSide() * panel.getRow()) / 2 - (panel.getSquareSide() / 2);
        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);

        addToInventory(Item.BANDAGE);
        addToInventory(Item.BANDAGE);
        addToInventory(Item.ENERGYDRINK);
        addToInventory(Item.BANDAGE);
        addToInventory(Item.ENERGYDRINK);
    }

    public void addToInventory(Item item) {
        if (item != null) {
            if (inventory.get(item) == null) {
                inventory.put(item, 1);
            } else {
                int count = inventory.get(item);
                inventory.put(item, count + 1);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(chooseImage(direction, counter), centerX, centerY, null);
        drawHealthBar(g);
        drawStaminaBar(g);
    }

    @Override
    public void drawHealthBar(Graphics2D g) {
        double scale = (double) (panel.getSquareSide() * 4) / maxLives;
        double value = scale * lives;

        int width = panel.getSquareSide() * 4;
        int height = 15;
        int x = panel.getWidth() / 2 - width / 2;
        int y = panel.getHeight() - 60;

        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 10, 10);
        g.setColor(new Color(255, 0, 30));
        g.fillRoundRect(x, y, (int) value, height, 10, 10);
    }

    public void drawStaminaBar(Graphics2D g) {
        double scale = (double) (panel.getSquareSide() * 4) / maxStamina;
        double value = scale * stamina;

        int width = panel.getSquareSide() * 4;
        int height = 15;
        int x = panel.getWidth() / 2 - width / 2;
        int y = panel.getHeight() - 30;

        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 15, 15);
        g.setColor(new Color(60, 0, 255));
        g.fillRoundRect(x, y, (int) value, height, 15, 15);
    }

    @Override
    public void update() {
        direction = userInput.getDirection();
        if (userInput.isPressed()) {
            canMove = !collisionManager.checkTileCollision(this, panel) && !allEntitiesCollision();
            if (canMove) {
                switch (direction) {
                    case 0 -> y -= speed;
                    case 1 -> y += speed;
                    case 2 -> x -= speed;
                    case 3 -> x += speed;
                }

                counter++;
                if (counter >= 15) {
                    this.counter = 0;
                }

                actualArea.setRect(x + 8, y + 16, 32, 32);
            }
        }

        //testing health bar
        if (entityHitPlayer()) {
            hitCounter++;
            if (hitCounter == 20) {
                decreaseLives();
                hitCounter = 0;
            }
        }

        //stamina options
        if (userInput.isShiftPressed()) {
            staminaCounter++;
            if (staminaCounter >= 5 && stamina > 0) {
                stamina--;
                staminaCounter = 0;
            }
            setSpeed();
            if (stamina > 0) {
                speed += 2;
            }

        } else {
            setSpeed();
            staminaCounter++;
            if (staminaCounter >= 15 && stamina < maxStamina) {
                stamina++;
                staminaCounter = 0;
            }
        }
    }

    @Override
    public boolean allEntitiesCollision() {
        for (Entity entity : panel.getEntities()) {
            if (collisionManager.checkEntityCollision(this, entity)) {
                return true;
            }
        }
        return false;
    }

    public boolean entityHitPlayer() {
        for (Entity entity : panel.getEntities()) {
            if (collisionManager.checkEntityCollision(entity, this)) {
                return true;
            }
        }
        return false;
    }

    public void setSpeed() {
        speed = (5 + (panel.getGame().getSpeedBonus() * 0.25));
        if (isASRunning) {
            speed = (6 + (panel.getGame().getSpeedBonus() * 0.25));
        }
    }

    public void setBonuses() {
        setSpeed();
        maxLives = 10 + panel.getGame().getHealthBonus();
        maxStamina = 30 + panel.getGame().getStaminaBonus();

        lives = maxLives;
        stamina = maxStamina;
    }

    public void increaseLives() {
        if (lives + 1 <= maxLives) {
            lives++;
        }
    }

    private int time = 0;
    public void addStamina(int durationInSeconds) {
        Clock clock = new Clock(panel);
        clock.start(durationInSeconds);
        setASRunning(true);
    }

    public int getCenterX() {
        return centerX;
    }
    public int getCenterY() {
        return centerY;
    }
    public Image getDefaultImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + defaultImagePath + "0.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public HashMap<Item, Integer> getInventory() {
        return inventory;
    }

    public boolean isASRunning() {
        return isASRunning;
    }

    public void setASRunning(boolean ASRunning) {
        isASRunning = ASRunning;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
