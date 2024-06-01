package management;

import entities.Entity;
import logic.ApplicationPanel;

import java.awt.*;

public class CollisionManager {
    ApplicationPanel panel;
    public CollisionManager(ApplicationPanel panel) {
        this.panel = panel;
    }

    /**
     * Checks whether the @param entity is about to collide with a solid tile or not.
     * Explanation: With each move entity can run into 2 tiles.
     * This method checks those tiles the entity can encounter if it stays on its current path.
     *
     * @param entity the entity
     * @return true if entity is about to collide with a solid tile
     */
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

    /**
     * Check if entity invader is about to collide with entity defender
     *
     * @param invader  entity invader
     * @param defender entity defender
     * @return true if invader is about to collide with defender
     */
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


    /**
     * Check if entity invader is in defenders range.
     * Explanation: This method will create an imaginary rectangle based on the direction of the defender entity.
     * If invader intersects this imaginary rectangle, it means that invader is in range.
     * Therefore, defender can "punch" the invader.
     *
     * @param invader  entity invader
     * @param defender entity defender
     * @return true if invader is in range
     */
    public boolean entityInRange(Entity invader, Entity defender){
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