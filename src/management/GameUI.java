package management;

import world.Panel;

import java.awt.*;

public class GameUI {

    world.Panel panel;
    Font font = new Font("font", Font.BOLD, 80); //temporarily

    public GameUI(Panel panel) {
        this.panel = panel;
    }

    public void draw(Graphics2D g) {
        g.setFont(font);
        g.setColor(Color.white);

        switch (panel.getStatus()) {
            case SETUP -> drawStartingScreen(g);
            case PLAYING -> {
                panel.getTilePainter().draw(g);
                panel.getGame().drawEntities(g);
                panel.getPlayer().draw(g);
            }
            case GAMEOVER -> drawDeathScreen(g);
        }
    }


    public void drawDeathScreen(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setFont(font);

        String message = "YOU DIED";
        int x = centerX(g, message);
        int y = centerY(g, message);

        g.setColor(new Color(133, 25, 25));
        g.drawString(message, x, y);
    }

    private void drawStartingScreen(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(Color.white);

        String text = "Salvos!";
        int x = centerX(g, text);
        int y = panel.getSquareSide() * 2;

        g.setFont(font);
        g.drawString(text, x, y);

        font = new Font("font", Font.BOLD, 36);
        g.setFont(font);

        text = "START";
        x = centerX(g, text);
        y += 5 * panel.getSquareSide();
        drawOptions(g, text, x, y);

        text = "QUIT";
        x = centerX(g, text);
        y += panel.getSquareSide();
        drawOptions(g, text, x, y);
    }


    public void drawOptions(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
        if ((panel.getChosenOption() == 0 && text.equals("START")) || (panel.getChosenOption() == 1 && text.equals("QUIT"))) {
            g.drawString(">", x - panel.getSquareSide(), y);
            g.drawString("<", x + textLength(g, text) + panel.getSquareSide() - textLength(g, "<"), y);
        }
    }

    public int textLength(Graphics2D g, String text) {
        return (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
    }

    public int centerX(Graphics2D g, String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return panel.getWidth() / 2 - length / 2;
    }

    public int centerY(Graphics2D g, String text) {
        int height = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        return panel.getHeight() / 2 + height / 2;
    }
}
