package world;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;

    private boolean solid;


    public Tile(BufferedImage image, boolean solid) {
        this.image = image;
        this.solid = solid;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


    public boolean isSolid() {
        return solid;
    }
}
