package items;

import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
public abstract class Item {
    protected BufferedImage image;

    protected ApplicationPanel panel;
    private int price;
    public Item(ApplicationPanel panel) {
        this.panel = panel;
    }

    /**
     * Uses this item.
     * Use means to experience the effects of the item.
     * (Also applies to all Overrides)
     */
    public abstract void use();

    /**
     * Collects this item.
     * Collect means to add to players inventory.
     * (Also applies to all Overrides)
     */
    public abstract void collect();

    public void setImage(String imagePath){
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            System.out.println("image path problem.");
        }
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public BufferedImage getImage() {
        return image;
    }
    public int getPrice() {
        return price;
    }
}
