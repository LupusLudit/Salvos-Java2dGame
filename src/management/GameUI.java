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
            case CUSTOMIZATION -> drawCustomizationScreen(g);
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
        g.setFont(new Font("font", Font.BOLD, 80));

        String message = "YOU DIED";

        g.setColor(new Color(133, 25, 25));
        g.drawString(message, centerX(g, message), centerY(g, message));
    }

    public void drawStartingScreen(Graphics2D g) {
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
        g.drawString(text, x, y);
        if(panel.getChosenOption() == 0) drawArrows(g, text, x,y, true);

        text = "QUIT";
        x = centerX(g, text);
        y += panel.getSquareSide();
        g.drawString(text, x, y);
        if(panel.getChosenOption() == 1) drawArrows(g, text, x,y, true);
    }

    public void drawCustomizationScreen(Graphics2D g){

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setColor(Color.white);

        g.setFont(font);

        int x = panel.getWidth() / 2 - panel.getSquareSide()*2;
        int y = panel.getSquareSide();
        g.drawImage(panel.getPlayer().getDefaultImage(), x, y, panel.getSquareSide()*4, panel.getSquareSide()*4, null);

        font = new Font("font", Font.BOLD, 36);
        g.setFont(font);

        String text = "HEALTH";
        x = panel.getSquareSide();
        y = panel.getHeight() - 4*panel.getSquareSide();
        g.drawString(text, x, y);
        if(panel.getChosenOption() == 0) drawArrows(g, text, x,y, false);

        text = "STAMINA";
        y += panel.getSquareSide();
        g.drawString(text, x, y);
        if(panel.getChosenOption() == 1) drawArrows(g, text, x,y, false);

        text = "SPEED";
        y += panel.getSquareSide();
        g.drawString(text, x, y);
        if(panel.getChosenOption() == 2) drawArrows(g, text, x,y, false);

        text = "CONFIRM";
        x = panel.getWidth() - textLength(g, text) - panel.getSquareSide();
        y = panel.getHeight() - panel.getSquareSide();
        g.drawString(text, x, y);
        if(panel.getChosenOption() == 3) drawArrows(g, text, x,y, false);

    }


    public void drawArrows(Graphics2D g, String text, int x, int y, boolean multiple) {
        g.drawString(">", x - panel.getSquareSide(), y);
        if(multiple){
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
