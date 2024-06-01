package effects;

import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Blasting extends Effect{

    private final int direction;
    public Blasting(ApplicationPanel panel, int duration, int direction) {
        super(panel, duration);
        this.direction = direction;
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) throws IOException {
        int x = 0;
        int y = 0;
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/blasts/gunBlast_" + direction + ".png")));
        switch (direction) {
            case 0 -> {
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() - panel.getSquareSide() + 7;
            }
            case 1 -> {
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() + panel.getSquareSide()/2 + 5;
            }
            case 2 -> {
                x = panel.getPlayer().getCenterX() - panel.getSquareSide();
                y = panel.getPlayer().getCenterY() - panel.getSquareSide()/4;
            }
            case 3 -> {
                x = panel.getPlayer().getCenterX() + panel.getSquareSide();
                y = panel.getPlayer().getCenterY() - panel.getSquareSide()/4;
            }
        }
        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
    }
}
