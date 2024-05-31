package effects;

import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PunchingEffect extends Effect{

    BufferedImage image;
    BufferedImage fistImage;
    private final int direction;
    public PunchingEffect(ApplicationPanel panel, int duration, int direction) {
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
        switch (direction) {
            case 0 -> {
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() - panel.getSquareSide()/2;
            }
            case 1 -> {
                x = panel.getPlayer().getCenterX();
                y = panel.getPlayer().getCenterY() + panel.getSquareSide()/2;
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
        chooseImages();

        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
        x = panel.getPlayer().getCenterX() - panel.getSquareSide()/2;
        y = panel.getHeight() - panel.getSquareSide()*4; //The effect will be displayed above the players bars
        g.drawImage(fistImage, x, y, panel.getSquareSide()*2, panel.getSquareSide()*2, null);
    }

    private void chooseImages() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/punching/punchBlow_" + direction + ".png")));
        if (duration >= 20){
            fistImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/punching/punching_0.png")));
        }
        else if (direction >= 10){
            fistImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/punching/punching_1.png")));
        }else {
            fistImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/punching/punching_2.png")));
        }
    }
}
