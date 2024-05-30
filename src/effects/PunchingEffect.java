package effects;

import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PunchingEffect extends Effect{

    BufferedImage image;
    BufferedImage fistImage;
    private int direction;
    public PunchingEffect(ApplicationPanel panel, int duration, int direction) {
        super(panel, duration);
        this.direction = direction;
        try {
            fistImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/fist.png")));
        }catch (IOException ignored){}
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) {
        int x = 0;
        int y = 0;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/punchingBlows/punchBlow_" + direction + ".png")));
        }catch (IOException ignored){}
        switch (direction) {
            case 0 -> {
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() - 20;
            }
            case 1 -> {
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() + 20;
            }
            case 2 -> {
                x = panel.getPlayer().getCenterX() - panel.getSquareSide()/2;
                y = panel.getPlayer().getCenterY();
            }
            case 3 -> {
                x = panel.getPlayer().getCenterX() + panel.getSquareSide()/2;
                y = panel.getPlayer().getCenterY();
            }
        }
        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);

        x = panel.getPlayer().getCenterX() - 15 + duration*2;
        y = panel.getPlayer().getCenterY() - panel.getSquareSide();
        g.drawImage(fistImage, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
    }
}
