package entities;

import management.CollisionManager;
import management.UserInput;
import java.awt.*;

public class Player extends Entity {

    UserInput userInput;
    world.Panel panel;
    private final int centerX;
    private final int centerY;

    private int stamina;
    private final int maxStamina;

    private int staminaCounter = 0;
    private int hitCounter = 0;

    public Player(UserInput userInput, world.Panel panel) {
        defaultImagePath = "character/sprite_";
        this.userInput = userInput;
        this.panel = panel;
        this.collisionManager = new CollisionManager();

        speed = 6;
        canMove = true;
        maxLives = 10;
        maxStamina = 30;

        lives = maxLives;
        stamina = maxStamina;

        this.x = 45*panel.getSquareSide();
        this.y = 45*panel.getSquareSide();
        centerX = (panel.getSquareSide() * panel.getCol()) / 2 - (panel.getSquareSide() / 2);
        centerY = (panel.getSquareSide() * panel.getRow()) / 2 - (panel.getSquareSide() / 2);
        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(chooseImage(direction, counter), centerX, centerY, null);
        drawHealthBar(g);
        drawStaminaBar(g);
    }
    @Override
    public void drawHealthBar(Graphics2D g){
        double scale = (double)(panel.getSquareSide()*4)/maxLives;
        double value = scale*lives;

        int width = panel.getSquareSide()*4;
        int height = 15;
        int x = panel.getWidth()/2 - width/2;
        int y = panel.getHeight() - 60;

        g.setColor(new Color(35,35,35));
        g.fillRect(x-2, y-2, width+4,height+4);
        g.setColor(new Color(255, 0, 30));
        g.fillRect(x, y, (int)value, height);
    }

    public void drawStaminaBar(Graphics2D g){
        double scale = (double)(panel.getSquareSide()*4)/maxStamina;
        double value = scale*stamina;

        int width = panel.getSquareSide()*4;
        int height = 15;
        int x = panel.getWidth()/2 - width/2;
        int y = panel.getHeight() - 30;

        g.setColor(new Color(35,35,35));
        g.fillRect(x-2, y-2, width+4,height+4);
        g.setColor(new Color(60, 0, 255));
        g.fillRect(x, y, (int)value, height);
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
        if(entityHitPlayer()){
            hitCounter++;
            if(hitCounter == 20){
                decreaseLives();
                hitCounter = 0;
            }
        }

        //stamina options
        if(userInput.isShiftPressed()){
            staminaCounter++;
            if(staminaCounter >= 5 && stamina > 0) {
                stamina--;
                staminaCounter = 0;
            }
            speed = 6;
            if(stamina > 0){
                speed = 8;
            }

        }else {
            speed = 6;
            staminaCounter++;
            if(staminaCounter >= 15 && stamina < maxStamina){
                stamina++;
                staminaCounter = 0;
            }
        }
    }
    @Override
    public boolean allEntitiesCollision(){
        for (Entity entity: panel.getEntities()){
            if(collisionManager.checkEntityCollision(this, entity)){
                return true;
            }
        }
        return false;
    }

    public boolean entityHitPlayer(){
        for (Entity entity: panel.getEntities()){
            if(collisionManager.checkEntityCollision(entity, this)){
                return true;
            }
        }
        return false;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }


}
