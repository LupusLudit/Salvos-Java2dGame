package effects;

import entities.Entity;
import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BitingEffect extends Effect{
    private Entity entity;
    BufferedImage image;
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
    public void draw(Graphics2D g) {
        try {
            pickImage();
        } catch (IOException ignored) {}
        g.drawImage(image, entity.getRelX(panel.getPlayer()) - panel.getSquareSide()/2, entity.getRelY(panel.getPlayer()) - panel.getSquareSide()*3,panel.getSquareSide()*2, panel.getSquareSide()*2, null);
    }

    private void pickImage() throws IOException {
        if (duration >= 20){
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mouth/mouth_open.png")));
        }
        else if (duration >= 10){
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mouth/mouth_closing.png")));
        }
        else{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mouth/mouth_closed.png")));
        }

    }

}
