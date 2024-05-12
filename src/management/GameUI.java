package management;

import items.Item;
import world.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GameUI {

    world.Panel panel;
    Font font = new Font("font", Font.BOLD, 80); //temporarily
    private int selectedRow = 0;
    private int selectedCol = 0;

    items.Item[][] itemsByPosition = new items.Item[3][2];

    public GameUI(Panel panel) {
        this.panel = panel;
    }


    public void draw(Graphics2D g) {
        g.setFont(font);
        g.setColor(Color.white);

        switch (panel.getStatus()) {
            case SETUP -> drawStartingScreen(g);
            case CUSTOMIZATION -> drawCustomizationScreen(g);
            case PLAYING -> drawBackground(g);
            case GAMEOVER -> drawDeathScreen(g);
            case SHOP -> { //will be edited later
                drawBackground(g);
                drawShop(g);
            }
            case INVENTORY -> { //will be edited later
                drawBackground(g);
                drawInventoryWindow(g);
            }
        }
        if (panel.getPlayer().getClock().isRunning()) {
            drawTimer(g);
        }
        if (panel.getEntities().isEmpty()) {
            drawWaveMessage(g);
        }
    }

    public void drawBackground(Graphics2D g) {
        panel.getTilePainter().draw(g);
        panel.getGame().drawEntities(g);
        panel.getPlayer().draw(g);
        panel.getCollectableManager().drawCollectables(g);
        try {
            drawAmmoIndicators(g);
            drawWeaponIndicators(g);
            drawScore(g);
        } catch (IOException e) {
            e.printStackTrace();
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
        if (panel.getChosenOption() == 0) drawArrows(g, text, x, y, true);

        text = "QUIT";
        x = centerX(g, text);
        y += panel.getSquareSide();
        g.drawString(text, x, y);
        if (panel.getChosenOption() == 1) drawArrows(g, text, x, y, true);
    }

    public void drawAmmoIndicators(Graphics2D g) throws IOException {
        g.setFont(new Font("font", Font.BOLD, 36));
        g.setColor(Color.white);
        int x = panel.getSquareSide() / 2;
        int y = panel.getHeight() - panel.getSquareSide() - 30;
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/ammo.png")));

        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
        String text = panel.getGame().getMagazine() + "/" + panel.getGame().getAmmo();
        g.drawString(text, x + panel.getSquareSide()*2, panel.getHeight() - 36);
    }

    public void drawWeaponIndicators(Graphics2D g) throws IOException {
        int x = panel.getSquareSide() / 2;
        int y = panel.getHeight() - 4 * panel.getSquareSide() - 10;
        BufferedImage image = null;
        switch (panel.getGame().getSelectedAmmo()) {
            case REVOLVER -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/revolver.png")));
            case PISTOL -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/pistol.png")));
            case SEMIAUTO -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/semi-auto.png")));
            case ASSAULTRIFLE -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/assault-rifle.png")));
            case SUBMACHINE_GUN -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/tommyGun.png")));
            case FIST -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/fist.png")));
        }
        g.drawImage(image, x, y, panel.getSquareSide() * 3, panel.getSquareSide() * 3, null);

    }

    public void drawCustomizationScreen(Graphics2D g) {

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setColor(Color.white);

        g.setFont(font);

        int x = panel.getWidth() / 2 - panel.getSquareSide() * 2;
        int y = panel.getSquareSide();
        g.drawImage(panel.getPlayer().getDefaultImage(), x, y, panel.getSquareSide() * 4, panel.getSquareSide() * 4, null);

        font = new Font("font", Font.BOLD, 36);
        g.setFont(font);

        String text = "HEALTH";
        x = panel.getSquareSide();
        y = panel.getHeight() - 4 * panel.getSquareSide();
        g.drawString(text, x, y);
        if (panel.getChosenOption() == 0) drawArrows(g, text, x, y, false);
        drawIndicators(g, panel.getSquareSide() * 5, y, panel.getGame().getHealthBonus());

        text = "STAMINA";
        y += panel.getSquareSide();
        g.drawString(text, x, y);
        if (panel.getChosenOption() == 1) drawArrows(g, text, x, y, false);
        drawIndicators(g, panel.getSquareSide() * 5, y, panel.getGame().getStaminaBonus());

        text = "SPEED";
        y += panel.getSquareSide();
        g.drawString(text, x, y);
        if (panel.getChosenOption() == 2) drawArrows(g, text, x, y, false);
        drawIndicators(g, panel.getSquareSide() * 5, y, panel.getGame().getSpeedBonus());

        text = "CONFIRM";
        x = panel.getWidth() - textLength(g, text) - panel.getSquareSide();
        y = panel.getHeight() - panel.getSquareSide();
        g.drawString(text, x, y);
        if (panel.getChosenOption() == 3) drawArrows(g, text, x, y, false);
    }

    private void drawIndicators(Graphics2D g, int x, int y, int counter) {
        g.setColor(Color.white);
        for (int i = 0; i < 10; i++) {
            x += panel.getSquareSide();
            if (i < counter) {
                g.drawString("▮", x, y);
            } else {
                g.drawString("▯", x, y);
            }

        }
    }

    public void drawArrows(Graphics2D g, String text, int x, int y, boolean multiple) {
        g.drawString(">", x - panel.getSquareSide(), y);
        if (multiple) {
            g.drawString("<", x + textLength(g, text) + panel.getSquareSide() - textLength(g, "<"), y);
        }
    }

    public void drawInventoryWindow(Graphics2D g) {
        int width = panel.getSquareSide() * 3 + 12;
        int height = panel.getSquareSide() * 3;
        int x = panel.getWidth() / 2 - width / 2;
        int y = panel.getSquareSide() *3;

        drawBackgroundRect(g, x, y, width, height);

        g.setFont(new Font("font", Font.BOLD, 36));

        String text = "INVENTORY";
        drawBackgroundRect(g,panel.getWidth()/2 - textLength(g,text)/2 - 12, y - panel.getSquareSide() - textHeight(g,text), textLength(g,text) +24, textHeight(g, text) +12);
        g.drawString(text, panel.getWidth()/2 - textLength(g,text)/2, y - panel.getSquareSide() - 6);


        AtomicInteger i = new AtomicInteger();
        AtomicInteger j = new AtomicInteger();
        AtomicReference<Item> previousItem = new AtomicReference<>();


        g.setFont(new Font("font", Font.BOLD, 10));
        panel.getPlayer().getInventory().forEach((key, value) -> {
            if (value != 0) {
                int imageX;
                int imageY;
                if (i.get() == 3) {
                    i.set(0);
                    j.set(j.get() + 1);
                }

                imageX = panel.getWidth() / 2 - width / 2 + i.get() * panel.getSquareSide() + 12;
                imageY = 3* panel.getSquareSide() + (j.get())*panel.getSquareSide() + 16;


                if (previousItem.get() != key) {
                    itemsByPosition[i.get()][j.get()] = key;
                    i.set(i.get() + 1);
                }


                g.drawImage(key.getImage(), imageX, imageY, panel.getSquareSide(), panel.getSquareSide(), null);
                g.drawString(String.valueOf(value), imageX + panel.getSquareSide() - 10, imageY + panel.getSquareSide() + textHeight(g, String.valueOf(value)) - 5);


                previousItem.set(key);
            }
        });

        g.setFont(new Font("font", Font.BOLD, 16));

        text = "^";
        x = panel.getWidth() / 2 - width / 2 + (selectedCol) * panel.getSquareSide() + 12 + panel.getSquareSide() / 2 - textLength(g, text);
        y = panel.getSquareSide()*3 + (selectedRow + 1) * (panel.getSquareSide() + 16);
        g.drawString(text, x, y);
    }

    public void drawShop(Graphics2D g) {
        int width = panel.getSquareSide() * 5 + 12;
        int height = panel.getSquareSide() * 3;
        int x = panel.getWidth() / 2 - width / 2;
        int y = panel.getHeight() / 2 - height / 2;

        drawBackgroundRect(g, x - 24, y, width + 48, height);

        g.setFont(new Font("font", Font.BOLD, 36));

        String text = "SHOP";
        drawBackgroundRect(g,panel.getWidth()/2 - textLength(g,text)/2 - 12, y - panel.getSquareSide() - textHeight(g,text), textLength(g,text) +24, textHeight(g, text) +12);
        g.drawString(text, panel.getWidth()/2 - textLength(g,text)/2, y - panel.getSquareSide() - 6);

        g.setFont(new Font("font", Font.BOLD, 10));
        for (int i = 0; i < 2; i++){
            for(int j = 0; j < 5; j++){
                x = panel.getWidth() / 2 - width / 2 + j*panel.getSquareSide() + 12;
                y = panel.getHeight() / 2 - height / 2 + (i*panel.getSquareSide()) + 16;
                g.drawImage(panel.getShop().getItem(j,i).getImage(), x, y, panel.getSquareSide(), panel.getSquareSide(), null);

                text = String.valueOf(panel.getShop().getItem(j,i).getPrice());
                g.drawString(text, x + panel.getSquareSide() - 10, y + panel.getSquareSide() + textHeight(g, text) - 5);
            }
        }

        g.setFont(new Font("font", Font.BOLD, 16));

        text = "^";
        x = panel.getWidth() / 2 - width / 2 + panel.getShop().getSelectedCol() * panel.getSquareSide() + 12 + panel.getSquareSide() / 2 - textLength(g, text);
        y = panel.getHeight() / 2 - height / 2 + (panel.getShop().getSelectedRow() + 1) * (panel.getSquareSide() + 16);
        g.drawString(text, x, y);
    }

    public void drawTimer(Graphics2D g) {
        g.setFont(new Font("font", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        String text = "0:" + panel.getPlayer().getTime();
        g.drawString(text, panel.getWidth() - textLength(g, text) - 10, panel.getSquareSide());
    }

    public void drawWaveMessage(Graphics2D g) {
        g.setFont(new Font("font", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        String text = "NEXT WAVE IN: " + panel.getWaveTimer();
        g.drawString(text, panel.getWidth() / 2 - textLength(g, text) / 2, panel.getSquareSide() * 3);
    }

    public void drawScore(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("font", Font.BOLD, 36));
        String text = String.valueOf(panel.getGame().getScore());
        try {
            Image image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/star.png")));
            g.drawImage(image, 5, 5, panel.getSquareSide(), panel.getSquareSide(), null);
            g.drawString(text, panel.getSquareSide() + 15, textHeight(g, text));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBackgroundRect(Graphics2D g, int x, int y, int width, int height){
        Color color = new Color(0, 0, 0, 230);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 50, 50);

        color = new Color(255, 255, 255);
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x, y, width, height, 50, 50);
    }


    public int textHeight(Graphics2D g, String text) {
        return (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
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

    public void addRow() {
        if (selectedRow < 1) {
            selectedRow++;
        } else {
            selectedRow = 0;
        }
    }

    public void subtractRow() {
        if (selectedRow > 0) {
            selectedRow--;
        } else {
            selectedRow = 1;
        }
    }

    public void addCol() {
        if (selectedCol < 2) {
            selectedCol++;
        } else {
            selectedCol = 0;
            addRow();
        }
    }

    public void subtractCol() {
        if (selectedCol > 0) {
            selectedCol--;
        } else {
            selectedCol = 2;
            subtractRow();
        }
    }

    public items.Item getSelectedItem() {
        return itemsByPosition[selectedCol][selectedRow];
    }
}