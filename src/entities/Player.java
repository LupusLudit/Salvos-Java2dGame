package entities;

import management.Clock;
import management.CollisionManager;
import management.Mode;
import management.UserInput;
import world.ApplicationPanel;
import world.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    UserInput userInput;
    ApplicationPanel applicationPanel;
    private final int centerX;
    private final int centerY;

    private int stamina;
    private int maxStamina;

    private int staminaCounter = 0;
    private int hitCounter = 0;
    Clock clock = new Clock();

    Inventory inventory = new Inventory();

    public Player(UserInput userInput, ApplicationPanel applicationPanel) {
        defaultImagePath = "character/sprite_";
        this.userInput = userInput;
        this.applicationPanel = applicationPanel;
        this.collisionManager = new CollisionManager();

        canMove = true;
        setBonuses();

        this.x = 45 * applicationPanel.getSquareSide();
        this.y = 45 * applicationPanel.getSquareSide();
        centerX = (applicationPanel.getSquareSide() * applicationPanel.getCol()) / 2 - (applicationPanel.getSquareSide() / 2);
        centerY = (applicationPanel.getSquareSide() * applicationPanel.getRow()) / 2 - (applicationPanel.getSquareSide() / 2);
        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
    }


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(chooseImage(direction, counter), centerX, centerY, null);

        drawBar(g, maxLives, lives, applicationPanel.getHeight() - 80, new Color(255, 0, 30));
        drawBar(g, maxStamina, stamina, applicationPanel.getHeight() - 50, new Color(60, 0, 255));
    }
    public void drawBar(Graphics2D g, int max, int current, int y, Color color) {
        double scale = (double) (applicationPanel.getSquareSide() * 4) / max;
        double value = scale * current;

        int width = applicationPanel.getSquareSide() * 4;
        int height = 15;
        int x = applicationPanel.getWidth() / 2 - width / 2;

        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 10, 10);
        g.setColor(color);
        g.fillRoundRect(x, y, (int) value, height, 10, 10);
    }
    @Override
    public void update() {
        direction = userInput.getDirection();
        if (userInput.isPressed()) {
            canMove = !collisionManager.checkTileCollision(this, applicationPanel) && !allEntitiesCollision();
            if (canMove) {
                move();
                actualArea.setRect(x + 8, y + 16, 32, 32);

                counter++;
                if (counter == 15) {
                    counter = 0;
                }
            }
        }
        handleHealth();
        handleStamina();
    }

    private void move() {
        switch (direction) {
            case 0 -> y -= speed;
            case 1 -> y += speed;
            case 2 -> x -= speed;
            case 3 -> x += speed;
        }
    }

    private void handleHealth() {
        if (entityHitPlayer()) {
            hitCounter++;
            if (hitCounter == 15) {
                decreaseLives();
                hitCounter = 0;
            }
        }
    }

    private void handleStamina() {
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
        for (Entity entity : applicationPanel.getEntities()) {
            if (collisionManager.checkEntityCollision(this, entity)) {
                return true;
            }
        }
        return false;
    }

    public boolean entityHitPlayer() {
        for (Entity entity : applicationPanel.getEntities()) {
            if (collisionManager.checkEntityCollision(entity, this)) {
                return true;
            }
        }
        return false;
    }

    public void setSpeed() {
        speed = (5 + (applicationPanel.getGame().getSpeedBonus() * 0.25));
        if (clock.isRunning()) {
            speed = (6 + (applicationPanel.getGame().getSpeedBonus() * 0.25));
        }
    }

    public void setBonuses() {
        setSpeed();
        maxLives = 30 + applicationPanel.getGame().getHealthBonus();
        maxStamina = 30 + applicationPanel.getGame().getStaminaBonus();

        lives = maxLives;
        stamina = maxStamina;
    }

    public void increaseLives() {
        if (lives + 1 <= maxLives) {
            lives++;
        }
    }

    private int time = 30;
    public void addStamina(int durationInSeconds) {
        clock = new Clock();
        clock.start(durationInSeconds, applicationPanel, Mode.STAMINA_COUNTER);
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Clock getClock() {
        return clock;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
