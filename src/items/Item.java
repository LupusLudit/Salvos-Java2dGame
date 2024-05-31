package items;

import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * The type Item.
 */
public abstract class Item {

    /**
     * The Image.
     */
    protected BufferedImage image;

    /**
     * The Application panel.
     */
    protected ApplicationPanel applicationPanel;
    private int price;

    /**
     * Instantiates a new Item.
     *
     * @param applicationPanel the application panel
     */
    public Item(ApplicationPanel applicationPanel) {
        this.applicationPanel = applicationPanel;
    }

    /**
     * Use.
     */
    public abstract void use();

    /**
     * Collect.
     */
    public abstract void collect();

    /**
     * Set image.
     *
     * @param imagePath the image path
     */
    public void setImage(String imagePath){
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            System.out.println("image path problem.");
        }
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
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
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }
}
