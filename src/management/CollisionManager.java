package management;

import entities.Entity;
import world.Panel;

public class CollisionManager {

    public boolean checkTileCollision(Entity entity, Panel panel) {

        int leftCol = (entity.getActualArea().x) / panel.getTileSide();
        int rightCol = (entity.getActualArea().x + entity.getActualArea().width) / panel.getTileSide();
        int topRow = entity.getActualArea().y / panel.getTileSide();
        int bottomRow = (entity.getActualArea().y + entity.getActualArea().height) / panel.getTileSide();

        boolean firstTile;
        boolean secondTile;

        switch (entity.getDirection()) {
            case 0 -> {
                topRow = (entity.getActualArea().y - entity.getSpeed()) / panel.getTileSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + topRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + topRow)).isCollision();

                if (firstTile || secondTile) {
                    return false;
                }
            }
            case 1 -> {
                bottomRow = (entity.getActualArea().y + entity.getActualArea().height + entity.getSpeed()) / panel.getTileSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isCollision();

                if (firstTile || secondTile) {
                   return false;
                }
            }
            case 2 -> {
                leftCol = (entity.getActualArea().x - entity.getSpeed()) / panel.getTileSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + topRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(leftCol + "," + bottomRow)).isCollision();

                if (firstTile || secondTile) {
                    return false;
                }
            }
            case 3 -> {
                rightCol = (entity.getActualArea().x + entity.getActualArea().width + entity.getSpeed()) / panel.getTileSide();
                firstTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + topRow)).isCollision();
                secondTile = panel.getTilePainter().getTiles().get(panel.getTilePainter().getMap().get(rightCol + "," + bottomRow)).isCollision();

                if (firstTile || secondTile) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkEntityCollision(Entity first, Entity second) {
        return first.getActualArea().intersects(second.getActualArea());
    }
}

