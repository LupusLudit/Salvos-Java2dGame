package entities;

import management.Clock;
import management.CollisionManager;
import management.Mode;
import management.UserInput;
import world.Weapon;
import world.ApplicationPanel;
import world.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    UserInput userInput;
    private final int centerX;
    private final int centerY;
    private int stamina;
    private int maxStamina;
    private int staminaCounter = 0;
    private int hitCounter = 0;
    Clock clock = new Clock();

    Inventory inventory = new Inventory();

    public Player(UserInput userInput, ApplicationPanel applicationPanel) {
        super(applicationPanel);
        defaultImagePath = "entities/sprite_";
        this.userInput = userInput;
        this.collisionManager = new CollisionManager();

        canMove = true;
        setBonuses();
        currentImage = loadImage("idle");

        this.x = 59 * applicationPanel.getSquareSide();
        this.y = 64 * applicationPanel.getSquareSide();
        centerX = (applicationPanel.getSquareSide() * applicationPanel.getCol()) / 2 - (applicationPanel.getSquareSide() / 2);
        centerY = (applicationPanel.getSquareSide() * applicationPanel.getRow()) / 2 - (applicationPanel.getSquareSide() / 2);
        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
    }


    @Override
    public void draw(Graphics2D g) {
        changeCurrentImage(counter);
        g.drawImage(currentImage, centerX, centerY, null);
        if (panel.getGame().getSelectedWeapon() != Weapon.FIST && panel.getMouseInput().isMouseClicked() && direction != 0) {
            try {
                g.drawImage(weaponOverlay(), centerX, centerY, null);
            } catch (IOException ignored) {
            }
        }
        drawBar(g, maxLives, lives, panel.getHeight() - 80, new Color(255, 0, 30));
        drawBar(g, maxStamina, stamina, panel.getHeight() - 50, new Color(60, 0, 255));
    }

    public void drawBar(Graphics2D g, int max, int current, int y, Color color) {
        double scale = (double) (panel.getSquareSide() * 4) / max;
        double value = scale * current;

        int width = panel.getSquareSide() * 4;
        int height = 15;
        int x = panel.getWidth() / 2 - width / 2;

        g.setColor(new Color(35, 35, 35));
        g.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 10, 10);
        g.setColor(color);
        g.fillRoundRect(x, y, (int) value, height, 10, 10);
    }

    @Override
    public void update() {
        if (userInput.isPressed() && !panel.getMouseInput().isMouseClicked()) {
            direction = userInput.getDirection();
            canMove = !collisionManager.checkTileCollision(this, panel) && !allEntitiesCollision();
            if (canMove) {
                move();
                actualArea.setRect(x + 8, y + 16, 32, 32);

                counter++;
                if (counter == 29) {
                    counter = 0;
                }
            }
        }
        handleHealth();
        handleStamina();
    }

    @Override
    public void changeCurrentImage(int counter) {
        if (counter != 0 && counter % 7 == 0 && canMove && userInput.isPressed() && !panel.getMouseInput().isMouseClicked()) {
            imageIndex++;
            if (imageIndex == 4) {
                imageIndex = 0;
            }
            currentImage = loadImage(String.valueOf(imageIndex));
        } else if (!userInput.isPressed() && !panel.getMouseInput().isMouseClicked()) {
            currentImage = loadImage("idle");
        } else if (panel.getMouseInput().isMouseClicked() && panel.getGame().getSelectedWeapon() != Weapon.FIST) {
            currentImage = loadImage("nohands");
        }
    }

    public BufferedImage weaponOverlay() throws IOException {
        String path = "";
        switch (panel.getGame().getSelectedWeapon()) {
            case REVOLVER -> path = "/hands/hands_revolver_" + direction + ".png";
            case PISTOL -> path = "/hands/hands_pistol_" + direction + ".png";
            case SEMIAUTO -> path = "/hands/hands_semiRifle_" + direction + ".png";
            case ASSAULTRIFLE -> path = "/hands/hands_ak_" + direction + ".png";
            case SUBMACHINE_GUN -> path = "/hands/hands_tommy_" + direction + ".png";
        }
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
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
        for (Entity entity : panel.getEntities()) {
            if (collisionManager.checkEntityCollision(entity, this)) {
                hitCounter++;
                if (hitCounter == 15) {
                    decreaseLives();
                    hitCounter = 0;
                }
                if (entity.isCanBite()) {
                    panel.getEffectManager().addBitingEffect(entity);
                }
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
        for (Entity entity : panel.getEntities()) {
            if (collisionManager.checkEntityCollision(this, entity)) {
                return true;
            }
        }
        return false;
    }

    public void setSpeed() {
        speed = (5 + (panel.getGame().getSpeedBonus() * 0.25));
        if (clock.isRunning()) {
            speed = (6 + (panel.getGame().getSpeedBonus() * 0.25));
        }
    }

    public void setBonuses() {
        setSpeed();
        maxLives = 30 + panel.getGame().getHealthBonus();
        maxStamina = 30 + panel.getGame().getStaminaBonus();

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
        clock.start(durationInSeconds, panel, Mode.STAMINA_COUNTER);
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
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + defaultImagePath + "1_idle.png")));
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
