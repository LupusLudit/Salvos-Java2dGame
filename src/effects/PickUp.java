package effects;

import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PickUp extends Effect {

    private BufferedImage plusImage;
    public PickUp(ApplicationPanel applicationPanel, int duration) {
        super(applicationPanel, duration);
        try {
            setPlusImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(panel.getUi().getMedium());
        g.drawString("You´ve picked up an item.", 10, panel.getUi().textHeight(g, "You´ve picked up an item") + panel.getSquareSide());
        g.drawImage(plusImage, panel.getPlayer().getCenterX(), panel.getPlayer().getCenterY() - panel.getSquareSide(), null);
    }

    private void setPlusImage() throws IOException {
        plusImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/effects/plus.png")));
    }

}
