package effects;

import logic.ApplicationPanel;

import java.awt.*;

/**
 * The type Reloading effect.
 */
public class ReloadingEffect extends Effect{
    public ReloadingEffect(ApplicationPanel panel, int duration) {
        super(panel, duration);
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(panel.getUi().getMedium());
        String text;
        if (duration >= 67){
            text = "Reloading.";
        }
        else if (duration >= 33){
            text = "Reloading..";
        }
        else {
            text = "Reloading...";
        }
        g.drawString(text, panel.getUi().centerX(g, text), panel.getSquareSide()*5);
    }
}
