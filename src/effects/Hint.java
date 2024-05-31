package effects;

import world.ApplicationPanel;

import java.awt.*;

public class Hint extends Effect {

    private String[] lines;

    public Hint(ApplicationPanel panel, int duration) {
        super(panel, duration);
        setText();
    }

    @Override
    public void update() {
        duration--;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(panel.getUi().getMedium());
        int x = panel.getWidth() - panel.getSquareSide()*7;
        int y = panel.getHeight() - panel.getSquareSide()*5;
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x, y + i * g.getFontMetrics().getHeight());
        }
    }

    private void setText() {
        lines = new String[]{
                "PRESS:",
                "WASD or arrows: to move",
                "SHIFT: to run",
                "I: to open inventory",
                "E: to use item",
                "B: to shop",
                "R: to reload your weapon"
        };
    }
}

