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

    CollisionManager collisionManager;

    Zombie testZombie;



    public Player(UserInput userInput, world.Panel panel, Zombie zombie) {
        defaultImagePath = "character/sprite_";
        this.userInput = userInput;
        this.panel = panel;
        this.collisionManager = new CollisionManager();

        speed = 6;
        canMove = true;
        lives = 10;
        maxLives = 10;
        stamina = 10;
        maxStamina = 10;
        this.x = 45*panel.getTileSide();
        this.y = 45*panel.getTileSide();
        centerX = (panel.getTileSide() * panel.getCol()) / 2 - (panel.getTileSide() / 2);
        centerY = (panel.getTileSide() * panel.getRow()) / 2 - (panel.getTileSide() / 2);
        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);

        testZombie = zombie;  //temporarily added for testing
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(chooseImage(direction, counter), centerX, centerY, null);
        drawHealthBar(g);
        drawStaminaBar(g);
    }
    @Override
    public void drawHealthBar(Graphics2D g){
        double scale = (double)(panel.getTileSide()*4)/maxLives;
        double value = scale*lives;

        int width = panel.getTileSide()*4;
        int height = 15;
        int x = panel.getWidth()/2 - width/2;
        int y = panel.getHeight() - 100;

        g.setColor(new Color(35,35,35));
        g.fillRect(x-2, y-2, width+4,height+4);
        g.setColor(new Color(255, 0, 30));
        g.fillRect(x, y, (int)value, height);
    }

    public void drawStaminaBar(Graphics2D g){
        double scale = (double)(panel.getTileSide()*4)/maxStamina;
        double value = scale*stamina;

        int width = panel.getTileSide()*4;
        int height = 15;
        int x = panel.getWidth()/2 - width/2;
        int y = panel.getHeight() - 70;

        g.setColor(new Color(35,35,35));
        g.fillRect(x-2, y-2, width+4,height+4);
        g.setColor(new Color(60, 0, 255));
        g.fillRect(x, y, (int)value, height);
    }

    @Override
    public void update() {
        direction = userInput.getDirection();

        if (userInput.isPressed()) {
            canMove = !collisionManager.checkTileCollision(this, panel) && !collisionManager.checkEntityCollision(this, testZombie);
            if(collisionManager.checkEntityCollision(this, testZombie) || collisionManager.checkEntityCollision(testZombie, this)){
                lives--;
            }
            if (canMove) {
                switch (direction) {
                    case 0 -> y -= speed;
                    case 1 -> y += speed;
                    case 2 -> x -= speed;
                    case 3 -> x += speed;
                }

                counter++;
                if (counter >= 20) {
                    this.counter = 0;
                }

                actualArea.setRect(x + 8, y + 16, 32, 32);
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
            if(staminaCounter >= 10 && stamina < 10){
                stamina++;
                staminaCounter = 0;
            }
        }

        //testing health bar
        if(collisionManager.checkEntityCollision(testZombie, this)){
            if(lives>0){
                lives--;
            }
        }

    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }


}
