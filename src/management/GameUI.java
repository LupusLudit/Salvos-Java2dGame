package management;

import items.Item;
import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GameUI {
    ApplicationPanel panel;
    private Font large;
    private Font medium;
    private Font small;
    private Font verySmall;
    Item[][] invByPos = new items.Item[5][2];

    public GameUI(ApplicationPanel applicationPanel) {
        this.panel = applicationPanel;
        initializeFonts();
    }

    private void initializeFonts() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/fonts/PIXELADE.TTF");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            large = baseFont.deriveFont(80f);
            medium = baseFont.deriveFont(36f);
            small = new Font("small", Font.BOLD, 16);
            verySmall = baseFont.deriveFont(10f);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        try {
            switch (panel.getStatus()) {
                case SETUP -> drawStartingScreen(g);
                case CUSTOMIZATION -> drawCustomizationScreen(g);
                case PLAYING -> drawBackground(g);
                case GAMEOVER -> drawDeathScreen(g);
                case SHOP -> { //will be edited later
                    drawBackground(g);
                    drawShopWindow(g);
                }
                case INVENTORY -> { //will be edited later
                    drawBackground(g);
                    drawInventoryWindow(g);
                }
            }
            if (panel.getPlayer().getClock().isRunning()) drawTimer(g);
            if (panel.getEntities().isEmpty()) drawWaveMessage(g);
        } catch (IOException ignored) {
        }
    }

    public void drawBackground(Graphics2D g) throws IOException {
        panel.getTilePainter().draw(g);
        panel.getGame().drawEntities(g);
        panel.getPlayer().draw(g);
        panel.getCollectableManager().drawCollectables(g);

        drawAmmoIndicators(g);
        drawWeaponIndicators(g);
        drawScore(g);
    }


    public void drawDeathScreen(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setFont(large);
        g.setColor(new Color(133, 25, 25));
        g.drawString("YOU DIED", centerX(g, "YOU DIED"), centerY(g, "YOU DIED") - panel.getSquareSide() * 2);
        g.setColor(Color.white);
        g.setFont(medium);
        if (panel.getChosenOption() > 1) {
            panel.setChosenOption(0);
        }
        drawSelectionLabel(g, "RESTART", centerX(g, "RESTART"), centerY(g, "RESTART") + panel.getSquareSide() * 2, 0, true);
        drawSelectionLabel(g, "QUIT", centerX(g, "QUIT"), centerY(g, "QUIT") + panel.getSquareSide() * 3, 1, true);
    }

    public void drawStartingScreen(Graphics2D g) throws IOException {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/backgroundScreens/startingScreen.png")));
        g.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);

        g.setColor(Color.white);
        g.setFont(medium);
        drawSelectionLabel(g, "START", centerX(g, "START"), centerY(g, "START") - panel.getSquareSide(), 0, true);
        drawSelectionLabel(g, "QUIT", centerX(g, "QUIT"), centerY(g, "QUIT") + panel.getSquareSide(), 1, true);
    }

    private void drawSelectionLabel(Graphics2D g, String text, int x, int y, int chosen, boolean multiple) {
        g.drawString(text, x, y);
        if (panel.getChosenOption() == chosen) drawArrows(g, text, x, y, multiple);
    }

    public void drawAmmoIndicators(Graphics2D g) throws IOException {
        g.setFont(medium);
        g.setColor(Color.white);
        int x = panel.getSquareSide() / 2;
        int y = panel.getHeight() - panel.getSquareSide() - 30;
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/ammo.png")));

        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
        String text = panel.getGame().getMagazine() + "/" + panel.getGame().getAmmo();
        g.drawString(text, x + panel.getSquareSide() * 2, panel.getHeight() - 36);
    }

    public void drawWeaponIndicators(Graphics2D g) throws IOException {
        int x = panel.getSquareSide() / 2;
        int y = panel.getHeight() - 4 * panel.getSquareSide() - 10;
        BufferedImage image = null;
        switch (panel.getGame().getSelectedWeapon()) {
            case REVOLVER ->
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/revolver.png")));
            case PISTOL ->
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/pistol.png")));
            case SEMIAUTO ->
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/semi-auto.png")));
            case ASSAULTRIFLE ->
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/assault-rifle.png")));
            case SUBMACHINE_GUN ->
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/tommyGun.png")));
            case FIST ->
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/fist.png")));
        }
        g.drawImage(image, x, y, panel.getSquareSide() * 3, panel.getSquareSide() * 3, null);

    }

    public void drawCustomizationScreen(Graphics2D g) throws IOException {

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setColor(Color.white);

        int x = panel.getWidth() / 2 - panel.getSquareSide() * 2;
        int y = panel.getSquareSide()*3;

        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/backgroundScreens/customizationScreen.png")));
        g.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);

        g.drawImage(panel.getPlayer().getDefaultImage(), x, y, panel.getSquareSide() * 4, panel.getSquareSide() * 4, null);

        g.setFont(medium);

        y = panel.getHeight() - 4 * panel.getSquareSide();
        drawSelectionLabel(g, "HEALTH", panel.getSquareSide(), y, 0, false);
        drawIndicators(g, panel.getSquareSide() * 5, y, panel.getGame().getHealthBonus());

        g.setFont(medium);
        y += panel.getSquareSide();
        drawSelectionLabel(g, "STAMINA", panel.getSquareSide(), y, 1, false);
        drawIndicators(g, panel.getSquareSide() * 5, y, panel.getGame().getStaminaBonus());

        g.setFont(medium);
        y += panel.getSquareSide();
        drawSelectionLabel(g, "SPEED", panel.getSquareSide(), y, 2, false);
        drawIndicators(g, panel.getSquareSide() * 5, y, panel.getGame().getSpeedBonus());

        g.setFont(medium);
        y += panel.getSquareSide();
        drawSelectionLabel(g, "CONFIRM", panel.getWidth() - textLength(g, "CONFIRM") - panel.getSquareSide(), y, 3, false);
    }

    private void drawIndicators(Graphics2D g, int x, int y, int counter) {
        g.setColor(Color.white);
        g.setFont(new Font("font", Font.BOLD, 36));
        for (int i = 0; i < 10; i++) {
            x += panel.getSquareSide();
            String symbol = (i < counter) ? "▮" : "▯";
            g.drawString(symbol, x, y);
        }
    }

    private void drawArrows(Graphics2D g, String text, int x, int y, boolean multiple) {
        g.drawString(">", x - panel.getSquareSide(), y);
        if (multiple) g.drawString("<", x + textLength(g, text) + panel.getSquareSide() - textLength(g, "<"), y);
    }

    public void drawInventoryWindow(Graphics2D g) {
        int width = panel.getSquareSide() * 5 + 24;
        int height = panel.getSquareSide() * 3;
        int x = panel.getWidth() / 2 - width / 2;
        int y = panel.getSquareSide() * 3;

        drawBackgroundRect(g, x, y, width, height);
        g.setColor(Color.white);
        drawInformationWindow(g, "INVENTORY", y);

        g.setFont(verySmall);
        AtomicInteger i = new AtomicInteger();
        AtomicInteger j = new AtomicInteger();
        AtomicReference<Item> previousItem = new AtomicReference<>();
        panel.getPlayer().getInventory().getItems().forEach((key, value) -> {
            if (value != 0) {
                int imageX;
                int imageY;
                if ((i.get() + 1) % 5 == 0) {
                    i.set(0);
                    j.set(j.get() + 1);
                }
                imageX = panel.getWidth() / 2 - width / 2 + i.get() * panel.getSquareSide() + 12;
                imageY = 3 * panel.getSquareSide() + (j.get()) * panel.getSquareSide() + 16;
                if (previousItem.get() != key) {
                    invByPos[i.get()][j.get()] = key;
                    i.set(i.get() + 1);
                }
                g.drawImage(key.getImage(), imageX, imageY, panel.getSquareSide(), panel.getSquareSide(), null);
                g.drawString(String.valueOf(value), imageX + panel.getSquareSide() - 10, imageY + panel.getSquareSide() + textHeight(g, String.valueOf(value)) - 5);
                previousItem.set(key);
            }
        });
        y = panel.getSquareSide() * 3 + (panel.getPlayer().getInventory().getSelectedRow() + 1) * (panel.getSquareSide() + 16);
        drawSelectionArrow(g, y, width, panel.getPlayer().getInventory().getSelectedCol());
    }

    public void drawShopWindow(Graphics2D g) {
        int width = panel.getSquareSide() * 5 + 24;
        int height = panel.getSquareSide() * 3;
        int x = panel.getWidth() / 2 - width / 2;
        int y = panel.getHeight() / 2 - height / 2;

        drawBackgroundRect(g, x - 24, y, width + 48, height);
        g.setFont(medium);
        drawInformationWindow(g, "SHOP", y);
        String text;
        g.setFont(verySmall);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                x = panel.getWidth() / 2 - width / 2 + j * panel.getSquareSide() + 12;
                y = panel.getHeight() / 2 - height / 2 + (i * panel.getSquareSide()) + 16;
                g.drawImage(panel.getShop().getItem(j, i).getImage(), x, y, panel.getSquareSide(), panel.getSquareSide(), null);
                text = String.valueOf(panel.getShop().getItem(j, i).getPrice());
                g.drawString(text, x + panel.getSquareSide() - 10, y + panel.getSquareSide() + textHeight(g, text) - 5);
            }
        }
        y = panel.getHeight() / 2 - height / 2 + (panel.getShop().getSelectedRow() + 1) * (panel.getSquareSide() + 16);
        drawSelectionArrow(g, y, width, panel.getShop().getSelectedCol());
    }

    public void drawTimer(Graphics2D g) {
        g.setFont(large);
        g.setColor(Color.WHITE);
        String text = "0:" + panel.getPlayer().getTime();
        g.drawString(text, panel.getWidth() - textLength(g, text) - 10, panel.getSquareSide());
    }

    public void drawWaveMessage(Graphics2D g) {
        g.setFont(large);
        g.setColor(Color.WHITE);
        String text = "NEXT WAVE IN: " + panel.getWaveTimer();
        g.drawString(text, panel.getWidth() / 2 - textLength(g, text) / 2, panel.getSquareSide() * 3);
    }

    public void drawScore(Graphics2D g) throws IOException {
        g.setColor(Color.WHITE);
        g.setFont(medium);
        String text = String.valueOf(panel.getGame().getScore());
        Image image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/star.png")));
        g.drawImage(image, 5, 5, panel.getSquareSide(), panel.getSquareSide(), null);
        g.drawString(text, panel.getSquareSide() + 15, textHeight(g, text) + 6);
    }

    public void drawBackgroundRect(Graphics2D g, int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 230);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 25, 25);

        color = new Color(255, 255, 255);
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x, y, width, height, 25, 25);
    }

    public void drawSelectionArrow(Graphics2D g, int y, int width, int col) {
        g.setFont(small);
        String text = "^";
        int x = panel.getWidth() / 2 - width / 2 + col * panel.getSquareSide() + 12 + panel.getSquareSide() / 2 - textLength(g, text);
        g.drawString(text, x, y);
    }

    public void drawInformationWindow(Graphics2D g, String text, int rectY) {
        g.setFont(medium);
        int x = panel.getWidth() / 2 - textLength(g, text) / 2 - 16;
        int y = rectY - panel.getSquareSide() - textHeight(g, text);

        drawBackgroundRect(g, x, y, textLength(g, text) + 32, textHeight(g, text) + 12);
        g.drawString(text, x+16, rectY - panel.getSquareSide() - 6);
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

    public items.Item getSelectedItem() {
        return invByPos[panel.getPlayer().getInventory().getSelectedCol()][panel.getPlayer().getInventory().getSelectedRow()];
    }

    public Font getMedium() {
        return medium;
    }
}