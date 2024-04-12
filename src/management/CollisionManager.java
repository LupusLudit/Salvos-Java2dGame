package management;

import entities.Entity;
import world.Panel;

public class CollisionManager {

    public boolean checkTileCollision(Entity entity, Panel panel) {

        int leftCol = (entity.getActualArea().x) / panel.getSquareSide();
        int rightCol = (entity.getActualArea().x + entity.getActualArea().width) / panel.getSquareSide();
        int topRow = entity.getActualArea().y / panel.getSquareSide();
        int bottomRow = (entity.getActualArea().y + entity.getActualArea().height) / panel.getSquareSide();

        boolean firstTile = false;
        boolean secondTile = false;

        switch (entity.getDirection()) {
            case 0 -> {
                topRow = (entity.getActualArea().y - entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + topRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + topRow)).isCollision();
            }
            case 1 -> {
                bottomRow = (entity.getActualArea().y + entity.getActualArea().height + entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isCollision();
            }
            case 2 -> {
                leftCol = (entity.getActualArea().x - entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + topRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isCollision();
            }
            case 3 -> {
                rightCol = (entity.getActualArea().x + entity.getActualArea().width + entity.getSpeed()) / panel.getSquareSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + topRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isCollision();
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

