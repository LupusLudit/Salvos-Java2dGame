package management;

import entities.Entity;
import logic.ApplicationPanel;

import java.awt.*;

public class CollisionManager {

    ApplicationPanel panel;

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
                firstTile = panel.getTilePainter().getTile(leftCol, topRow).isSolid();
                secondTile = panel.getTilePainter().getTile(rightCol,topRow).isSolid();
            }
            case 1 -> {
                bottomRow = (int) (entity.getActualArea().y + entity.getActualArea().height + entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTile(leftCol, bottomRow).isSolid();
                secondTile = panel.getTilePainter().getTile(rightCol, bottomRow).isSolid();
            }
            case 2 -> {
                leftCol = (int) (entity.getActualArea().x - entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTile(leftCol, topRow).isSolid();
                secondTile = panel.getTilePainter().getTile(leftCol, bottomRow).isSolid();
            }
            case 3 -> {
                rightCol = (int) (entity.getActualArea().x + entity.getActualArea().width + entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTile(rightCol, topRow).isSolid();
                secondTile = panel.getTilePainter().getTile(rightCol, bottomRow).isSolid();
            }
        }
        return firstTile || secondTile;
    }

    public boolean checkEntityCollision(Entity invader, Entity defender) {
        Rectangle invaderArea = new Rectangle();
        int width = invader.getActualArea().width;
        int height = invader.getActualArea().height;

        switch (invader.getDirection()) {
            case 0 -> invaderArea = new Rectangle(invader.getActualArea().x, (int) (invader.getActualArea().y - invader.getSpeed()), width, height);
            case 1 -> invaderArea = new Rectangle(invader.getActualArea().x, (int) (invader.getActualArea().y + invader.getSpeed()), width, height);
            case 2 -> invaderArea = new Rectangle((int) (invader.getActualArea().x - invader.getSpeed()), invader.getActualArea().y, width, height);
            case 3 -> invaderArea = new Rectangle((int) (invader.getActualArea().x + invader.getSpeed()), invader.getActualArea().y, width, height);
        }
        return invaderArea.intersects(defender.getActualArea());
    }


    public boolean checkAdjutantTiles(Entity invader, Entity defender){
        Rectangle invaderArea = invader.getActualArea();
        Rectangle defenderArea = new Rectangle();
        switch (defender.getDirection()){
            case 0 -> defenderArea = new Rectangle(defender.getActualArea().x - 16, defender.getActualArea().y - 16, 64,16);
            case 1 -> defenderArea = new Rectangle(defender.getActualArea().x - 16, defender.getActualArea().y + 32, 64,16);
            case 2 -> defenderArea = new Rectangle(defender.getActualArea().x - 16, defender.getActualArea().y -16, 16,64);
            case 3 -> defenderArea = new Rectangle(defender.getActualArea().x + 32, defender.getActualArea().y - 16, 16,48);
        }
        return invaderArea.intersects(defenderArea);
    }
}