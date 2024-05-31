package effects;

import entities.Entity;
import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
public class BitingEffect extends Effect{
    private final Entity entity;
    BufferedImage image;

    /**
     * Biting effect constructor.
     *
     * @param panel    the application panel
     * @param duration the duration of the effect
     * @param entity   the entity above which will the effect be drawn
     */
    public BitingEffect(ApplicationPanel panel, int duration, Entity entity) {
        super(panel, duration);
        this.entity = entity;
    }

    @Override
    public void update() {
        duration--;
        entity.setCanBite(duration == 0);
    }

    @Override
    public void draw(Graphics2D g) throws IOException {
        pickImage();
        int x = entity.getRelX(panel.getPlayer()) - panel.getSquareSide()/2;
        int y = entity.getRelY(panel.getPlayer()) - panel.getSquareSide()*3;
        g.drawImage(image,x ,y ,panel.getSquareSide()*2, panel.getSquareSide()*2, null);
    }

    /**
     * Picks which image to draw.
     *
     * @throws IOException if the program couldn't find the image on the specific address.
     */

    private void pickImage() throws IOException {
        if (duration >= 20){
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/mouth/mouth_open.png")));
        }
        else if (duration >= 10){
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/mouth/mouth_closing.png")));
        }
        else{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/mouth/mouth_closed.png")));
        }

    }

}
