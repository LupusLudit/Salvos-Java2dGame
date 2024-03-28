package world;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;

    private boolean collision;


    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


    public boolean isCollision() {
        return collision;
    }
}
