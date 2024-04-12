package management;

import world.Panel;

import java.awt.*;

public class GameUI {

    world.Panel panel;
    Font font = new Font("font", Font.BOLD, 80); //temporarily

    public GameUI(Panel panel) {
        this.panel = panel;
    }


    public void drawDeathScreen(Graphics2D g){
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setFont(font);

        String message = "YOU DIED";
        int x = centerX(g, message);
        int y = centerY(g, message);

        g.setColor(new Color(133,25,25));
        g.drawString(message, x, y);
    }

    public int centerX(Graphics2D g, String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return panel.getWidth()/2 - length/2;
    }

    public int centerY(Graphics2D g, String text) {
        int height = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        return panel.getHeight()/2 + height/2;
    }
}
