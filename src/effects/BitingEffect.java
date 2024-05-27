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
    }

    @Override
    public void draw(Graphics2D g) {
        try {
            pickImage();
        } catch (IOException e) {
            System.out.println("problem");
            System.out.println("duration: " + duration);
            System.out.println("x :" + entity.getRelX(panel.getPlayer()));
            System.out.println("y :" + entity.getRelY(panel.getPlayer()));
            throw new RuntimeException(e);
        }
        g.drawImage(image, entity.getRelX(panel.getPlayer()), entity.getRelY(panel.getPlayer()) - panel.getSquareSide(), null);
    }

    private void pickImage() throws IOException {
        if (duration >= 40){
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mouth/mouth_open.png")));
        }
        else if (duration >= 20){
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mouth/mouth_closing.png")));
        }
        else{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/mouth/mouth_closed.png")));
        }

    }

}
