package management;

import entities.Entity;
import world.ApplicationPanel;

public class CollisionManager {

    public boolean checkTileCollision(Entity entity, ApplicationPanel applicationPanel) {

        int leftCol = (entity.getActualArea().x) / applicationPanel.getSquareSide();
        int rightCol = (entity.getActualArea().x + entity.getActualArea().width) / applicationPanel.getSquareSide();
        int topRow = entity.getActualArea().y / applicationPanel.getSquareSide();
        int bottomRow = (entity.getActualArea().y + entity.getActualArea().height) / applicationPanel.getSquareSide();

        boolean firstTile = false;
        boolean secondTile = false;

        switch (entity.getDirection()) {
            case 0 -> {
                topRow = (int) (entity.getActualArea().y - entity.getSpeed()) / applicationPanel.getSquareSide();
                firstTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(leftCol + "," + topRow)).isCollision();
                secondTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(rightCol + "," + topRow)).isCollision();
            }
            case 1 -> {
                bottomRow = (int) (entity.getActualArea().y + entity.getActualArea().height + entity.getSpeed()) / applicationPanel.getSquareSide();
                firstTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isCollision();
                secondTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isCollision();
            }
            case 2 -> {
                leftCol = (int) (entity.getActualArea().x - entity.getSpeed()) / applicationPanel.getSquareSide();
                firstTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(leftCol + "," + topRow)).isCollision();
                secondTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isCollision();
            }
            case 3 -> {
                rightCol = (int) (entity.getActualArea().x + entity.getActualArea().width + entity.getSpeed()) / applicationPanel.getSquareSide();
                firstTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(rightCol + "," + topRow)).isCollision();
                secondTile = applicationPanel.getTilePainter().getTiles().get(applicationPanel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isCollision();
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
}