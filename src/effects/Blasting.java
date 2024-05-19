package effects;

import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Blasting extends Effect{

    private BufferedImage image;
    private int direction;

    public Blasting(ApplicationPanel panel, int duration, int direction) {
        super(panel, duration);
        this.direction = direction;
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
        switch (direction) {
            case 0 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_0.png")));
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() - panel.getSquareSide() / 2;
            }
            case 1 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_1.png")));
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() + panel.getSquareSide() / 2;
            }
            case 2 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_2.png")));
                x = panel.getPlayer().getCenterX() - panel.getSquareSide() / 2;
                y = panel.getPlayer().getCenterY();
            }
            case 3 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_3.png")));
                x = panel.getPlayer().getCenterX() + panel.getSquareSide() / 2;
                y = panel.getPlayer().getCenterY();
            }
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
    }
}
