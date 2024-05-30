package management;

import entities.Entity;
import world.ApplicationPanel;

import java.awt.*;

public class CollisionManager {

    private ApplicationPanel panel;

    public CollisionManager(ApplicationPanel panel) {
        this.panel = panel;
    }

    public boolean checkTileCollision(Entity entity) {

        int leftCol = (entity.getActualArea().x) / panel.getSquareSide();
        int rightCol = (entity.getActualArea().x + entity.getActualArea().width) / panel.getSquareSide();
        int topRow = entity.getActualArea().y / panel.getSquareSide();
        int bottomRow = (entity.getActualArea().y + entity.getActualArea().height) / panel.getSquareSide();

        boolean firstTile = false;
        boolean secondTile = false;

        switch (entity.getDirection()) {
            case 0 -> {
                topRow = (int) (entity.getActualArea().y - entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + topRow)).isSolid();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + topRow)).isSolid();
            }
            case 1 -> {
                bottomRow = (int) (entity.getActualArea().y + entity.getActualArea().height + entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isSolid();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isSolid();
            }
            case 2 -> {
                leftCol = (int) (entity.getActualArea().x - entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + topRow)).isSolid();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isSolid();
            }
            case 3 -> {
                rightCol = (int) (entity.getActualArea().x + entity.getActualArea().width + entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + topRow)).isSolid();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isSolid();
            }
        }
        return firstTile || secondTile;
    }

    public boolean checkEntityCollision(Entity first, Entity second) {
        int startingX = first.getActualArea().x;
        int startingY = first.getActualArea().y;
        boolean collision;

        switch (first.getDirection()) {
            case 0 -> first.getActualArea().y -= first.getSpeed();
            case 1 -> first.getActualArea().y += first.getSpeed();
            case 2 -> first.getActualArea().x -= first.getSpeed();
            case 3 -> first.getActualArea().x += first.getSpeed();
        }
        collision = first.getActualArea().intersects(second.getActualArea());

        first.getActualArea().x = startingX;
        first.getActualArea().y = startingY;

        return collision;
    }


    public boolean checkAdjutantTiles(Entity defender, Entity invader){
        Rectangle defenderArea = new Rectangle();
        Rectangle invaderArea = invader.getActualArea();
        switch (defender.getDirection()){
            case 0 -> defenderArea = new Rectangle(defender.getActualArea().x - 16, defender.getActualArea().y - 16, 64,16);
            case 1 -> defenderArea = new Rectangle(defender.getActualArea().x - 16, defender.getActualArea().y + 32, 64,16);
            case 2 -> defenderArea = new Rectangle(defender.getActualArea().x - 16, defender.getActualArea().y -16, 16,64);
            case 3 -> defenderArea = new Rectangle(defender.getActualArea().x + 32, defender.getActualArea().y - 16, 16,48);
        }
        return invaderArea.intersects(defenderArea);
    }
}