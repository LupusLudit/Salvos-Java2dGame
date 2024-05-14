package items;

import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Item {

    protected BufferedImage image;

    protected ApplicationPanel applicationPanel;
    private int price;

    public Item(ApplicationPanel applicationPanel) {
        this.applicationPanel = applicationPanel;
    }
    public abstract void use();
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
