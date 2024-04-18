package world;

import entities.Entity;
import entities.Zombie;

import java.awt.*;
import java.util.Iterator;

public class Game {

    //spaw delay will be added
    world.Panel panel;
    public Game(Panel panel) {
        this.panel = panel;
    }

    public void setEntities(int wave){
        for (int i = 0; i < wave*3; i++){
            panel.getEntities().add(new Zombie(panel));
        }
    }

    public void updateEntities() {
        Iterator<Entity> iterator = panel.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update();
            panel.simulateShooting(entity);
            if (entity.getLives() == 0) {
                iterator.remove();
            }
        }
    }

    public void drawEntities(Graphics2D g){
        for (Entity entity: panel.getEntities()){
            if(entity != null){
                entity.draw(g);
            }
        }
    }
}