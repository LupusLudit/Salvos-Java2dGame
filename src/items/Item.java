package items;

import world.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public abstract class Item {

    protected Image image;

    protected world.Panel panel;

    public Item(Panel panel) {
        this.panel = panel;
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
    public Image getImage() {
        return image;
    }

}