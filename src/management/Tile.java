package management;

import java.awt.image.BufferedImage;

/**
 * The type Tile.
 */
public class Tile {

    private BufferedImage image;

    private final boolean solid;
    private final int imageIndex;

    /**
     * Instantiates a new Tile.
     *
     * @param image      the image
     * @param solid      the solid
     * @param imageIndex the image index
     */
    public Tile(BufferedImage image, boolean solid, int imageIndex) {
        this.image = image;
        this.solid = solid;
        this.imageIndex = imageIndex;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Is solid boolean.
     *
     * @return the boolean
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * Gets image index.
     *
     * @return the image index
     */
    public int getImageIndex() {
        return imageIndex;
    }
}
