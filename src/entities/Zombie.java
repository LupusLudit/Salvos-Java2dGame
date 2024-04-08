package entities;

import management.CollisionManager;

import java.awt.*;

public class Zombie extends Entity {

    CollisionManager collisionManager;

    world.Panel panel;
    private int updateDelay; //so the zombie doesn't update too quickly

    public Zombie(world.Panel panel) {
        defaultImagePath = "zombie/zombie-sprite_";
        this.panel = panel;

        speed = 9;
        canMove = true;
        lives = 5;
        maxLives = 5;
        direction = 0;
        counter = 0;
        x = 47 * panel.getTileSide();
        y = 47 * panel.getTileSide();

        this.actualArea = new Rectangle(x + 8, y + 16, 32, 32);
        this.collisionManager = new CollisionManager();
    }

    public void updateDirection() {
        int dx = panel.getPlayer().getX() - x;
        int dy = panel.getPlayer().getY() - y;

        if (Math.abs(dx) >= Math.abs(dy)) {
            direction = 2;
            if (dx > 0) {
                direction = 3;
            }
        } else {
            direction = 0;
            if (dy > 0) {
                direction = 1;
            }
        }

    }

    @Override
    public void draw(Graphics2D g) {
        if (Math.abs(panel.getPlayer().getX() - x) < panel.getPlayer().getCenterX() + panel.getTileSide()
                && Math.abs(panel.getPlayer().getY() - y) < panel.getPlayer().getCenterY() + panel.getTileSide()) { //is in range control
            g.drawImage(chooseImage(direction, counter), getRelX(), getRelY(), null);
        }
        drawHealthBar(g);
    }

    @Override
    public void update() {
        updateDelay++;
        if (updateDelay == 3) {
            updateDirection();
            canMove = !collisionManager.checkTileCollision(this, panel) && !collisionManager.checkEntityCollision(this, panel.getPlayer()); //temporarily added for testing
            if (canMove) {
                switch (direction) {
                    case 0 -> y -= speed;
                    case 1 -> y += speed;
                    case 2 -> x -= speed;
                    case 3 -> x += speed;
                }

                actualArea.setRect(x + 8, y + 16, 32, 32);
            }
            updateDelay = 0;
        }
        counter++;
        if (counter >= 20 && canMove) {
            this.counter = 0;
        }
        //testing health bar
        if(collisionManager.checkEntityCollision(this, panel.getPlayer())){
            if(lives>0){
                lives--;
                System.out.println("zombie lives:" + lives);
            }
        }


    }
    @Override
    public void drawHealthBar(Graphics2D g){
        double scale = (double)(panel.getTileSide())/maxLives;
        double value = scale*lives;

        int width = panel.getTileSide();
        int height = 5;
        int x = getRelX() + panel.getTileSide()/2 - width/2;
        int y = getRelY() - 15;

        g.setColor(new Color(35,35,35));
        g.fillRect(x-2, y-2, width+4,height+4);
        g.setColor(new Color(255, 0, 30));
        g.fillRect(x, y, (int)value, height);
        System.out.println("has been drawn");
    }


    public void chooseSpawnPoint() {
    }

    public int getRelX(){ // returns relative x coordinate position
        return x - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
    }

    public int getRelY(){ // returns relative y coordinate position
        return y - panel.getPlayer().getY() + panel.getPlayer().getCenterY();
    }
}
