package management;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;

    private final boolean solid;
    private final int imageIndex;

    public Tile(BufferedImage image, boolean solid, int imageIndex) {
        this.image = image;
        this.solid = solid;
        this.imageIndex = imageIndex;
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

    public int getImageIndex() {
        return imageIndex;
    }
}
