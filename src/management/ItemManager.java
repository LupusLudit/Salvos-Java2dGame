package management;

import world.Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ItemManager {

    world.Panel panel;

    public ItemManager(world.Panel panel) {
        this.panel = panel;
    }

    public BufferedImage getImage(Item item) throws IOException{
        BufferedImage image = null;
        switch (item){
            case BANDAGE -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/bandage.png")));
            case ENERGYDRINK -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/energy_drink.png")));
        }
        return image;
    }

    public void useItem(Item item){
        switch (item){
            case BANDAGE -> panel.getPlayer().increaseLives();
            case ENERGYDRINK -> panel.getPlayer().addStamina(1, 30);
        }
    }
}
