package management;
import items.Item;
import world.ApplicationPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
public class GameUI {
    ApplicationPanel applicationPanel;
    Font font = new Font("font", Font.BOLD, 80); //temporarily
    items.Item[][] invByPos = new items.Item[5][2];
    public GameUI(ApplicationPanel applicationPanel) {
        this.applicationPanel = applicationPanel;
    }

    public void draw(Graphics2D g) {
        g.setFont(font);
        g.setColor(Color.white);
        try {
            switch (applicationPanel.getStatus()) {
                case SETUP -> drawStartingScreen(g);
                case CUSTOMIZATION -> drawCustomizationScreen(g);
                case PLAYING -> {
                    drawBackground(g);
                    if (applicationPanel.getGame().isShooting()) {
                        drawFireBlast(g);
                    }
                }
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
            if (applicationPanel.getPlayer().getClock().isRunning()) drawTimer(g);
            if (applicationPanel.getEntities().isEmpty()) drawWaveMessage(g);
        } catch (IOException ignored) {}

    }

    public void drawBackground(Graphics2D g) throws IOException {
        applicationPanel.getTilePainter().draw(g);
        applicationPanel.getGame().drawEntities(g);
        applicationPanel.getPlayer().draw(g);
        applicationPanel.getCollectableManager().drawCollectables(g);

        drawAmmoIndicators(g);
        drawWeaponIndicators(g);
        drawScore(g);
    }


    public void drawDeathScreen(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, applicationPanel.getWidth(), applicationPanel.getHeight());
        g.setFont(new Font("font", Font.BOLD, 80));
        g.setColor(new Color(133, 25, 25));
        g.drawString("YOU DIED", centerX(g, "YOU DIED"), centerY(g, "YOU DIED"));
    }

    public void drawStartingScreen(Graphics2D g) throws IOException {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, applicationPanel.getWidth(), applicationPanel.getHeight());
        g.setColor(Color.white);
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/startingScreen.png")));
        g.drawImage(image, 0, 0, applicationPanel.getWidth(), applicationPanel.getHeight(), null);

        font = new Font("font", Font.BOLD, 36);
        g.setFont(font);

        drawSelectionLabel(g, "START", centerX(g, "START"), centerY(g, "START") - applicationPanel.getSquareSide(), 0, true);
        drawSelectionLabel(g, "QUIT", centerX(g, "QUIT"), centerY(g, "QUIT") + applicationPanel.getSquareSide(), 1, true);
    }

    private void drawSelectionLabel(Graphics2D g, String text, int x, int y, int chosen, boolean multiple){
        g.drawString(text, x, y);
        if (applicationPanel.getChosenOption() == chosen) drawArrows(g, text, x, y, multiple);
    }

    public void drawAmmoIndicators(Graphics2D g) throws IOException {
        g.setFont(new Font("font", Font.BOLD, 36));
        g.setColor(Color.white);
        int x = applicationPanel.getSquareSide() / 2;
        int y = applicationPanel.getHeight() - applicationPanel.getSquareSide() - 30;
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/ammo.png")));

        g.drawImage(image, x, y, applicationPanel.getSquareSide(), applicationPanel.getSquareSide(), null);
        String text = applicationPanel.getGame().getMagazine() + "/" + applicationPanel.getGame().getAmmo();
        g.drawString(text, x + applicationPanel.getSquareSide() * 2, applicationPanel.getHeight() - 36);
    }

    public void drawWeaponIndicators(Graphics2D g) throws IOException {
        int x = applicationPanel.getSquareSide() / 2;
        int y = applicationPanel.getHeight() - 4 * applicationPanel.getSquareSide() - 10;
        BufferedImage image = null;
        switch (applicationPanel.getGame().getSelectedAmmo()) {
            case REVOLVER -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/revolver.png")));
            case PISTOL -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/pistol.png")));
            case SEMIAUTO -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/semi-auto.png")));
            case ASSAULTRIFLE -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/assault-rifle.png")));
            case SUBMACHINE_GUN -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/tommyGun.png")));
            case FIST -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/fist.png")));
        }
        g.drawImage(image, x, y, applicationPanel.getSquareSide() * 3, applicationPanel.getSquareSide() * 3, null);

    }

    public void drawCustomizationScreen(Graphics2D g) {

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, applicationPanel.getWidth(), applicationPanel.getHeight());
        g.setColor(Color.white);

        g.setFont(font);

        int x = applicationPanel.getWidth() / 2 - applicationPanel.getSquareSide() * 2;
        int y = applicationPanel.getSquareSide();
        g.drawImage(applicationPanel.getPlayer().getDefaultImage(), x, y, applicationPanel.getSquareSide() * 4, applicationPanel.getSquareSide() * 4, null);

        font = new Font("font", Font.BOLD, 36);
        g.setFont(font);

        y = applicationPanel.getHeight() - 4 * applicationPanel.getSquareSide();
        drawSelectionLabel(g, "HEALTH", applicationPanel.getSquareSide(), y, 0, false);
        drawIndicators(g, applicationPanel.getSquareSide() * 5, y, applicationPanel.getGame().getHealthBonus());

        y += applicationPanel.getSquareSide();
        drawSelectionLabel(g, "STAMINA", applicationPanel.getSquareSide(), y, 1, false);
        drawIndicators(g, applicationPanel.getSquareSide() * 5, y, applicationPanel.getGame().getStaminaBonus());

        y += applicationPanel.getSquareSide();
        drawIndicators(g, applicationPanel.getSquareSide() * 5, y, applicationPanel.getGame().getSpeedBonus());
        drawSelectionLabel(g, "SPEED", applicationPanel.getSquareSide(), y, 2, false);

        y += applicationPanel.getSquareSide();
        drawSelectionLabel(g, "CONFIRM", applicationPanel.getWidth() - textLength(g, "CONFIRM") - applicationPanel.getSquareSide(), y, 3, false);
    }

    private void drawIndicators(Graphics2D g, int x, int y, int counter) {
        g.setColor(Color.white);
        for (int i = 0; i < 10; i++) {
            x += applicationPanel.getSquareSide();
            String symbol = (i < counter) ? "▮" : "▯";
            g.drawString(symbol, x, y);
        }
    }

    private void drawArrows(Graphics2D g, String text, int x, int y, boolean multiple) {
        g.drawString(">", x - applicationPanel.getSquareSide(), y);
        if (multiple) g.drawString("<", x + textLength(g, text) + applicationPanel.getSquareSide() - textLength(g, "<"), y);
    }

    public void drawInventoryWindow(Graphics2D g) {
        int width = applicationPanel.getSquareSide() * 5 + 12;
        int height = applicationPanel.getSquareSide() * 3;
        int x = applicationPanel.getWidth() / 2 - width / 2;
        int y = applicationPanel.getSquareSide() * 3;

        drawBackgroundRect(g, x, y, width, height);
        g.setColor(Color.white);
        drawInformationWindow(g, "INVENTORY", y);

        g.setFont(new Font("font", Font.BOLD, 10));
        AtomicInteger i = new AtomicInteger();
        AtomicInteger j = new AtomicInteger();
        AtomicReference<Item> previousItem = new AtomicReference<>();
        applicationPanel.getPlayer().getInventory().getItems().forEach((key, value) -> {
            if (value != 0) {
                int imageX;
                int imageY;
                if ((i.get() + 1) % 5 == 0) {
                    i.set(0);
                    j.set(j.get() + 1);
                }
                imageX = applicationPanel.getWidth() / 2 - width / 2 + i.get() * applicationPanel.getSquareSide() + 12;
                imageY = 3 * applicationPanel.getSquareSide() + (j.get()) * applicationPanel.getSquareSide() + 16;
                if (previousItem.get() != key) {
                    invByPos[i.get()][j.get()] = key;
                    i.set(i.get() + 1);
                }
                g.drawImage(key.getImage(), imageX, imageY, applicationPanel.getSquareSide(), applicationPanel.getSquareSide(), null);
                g.drawString(String.valueOf(value), imageX + applicationPanel.getSquareSide() - 10, imageY + applicationPanel.getSquareSide() + textHeight(g, String.valueOf(value)) - 5);
                previousItem.set(key);
            }
        });
        y = applicationPanel.getSquareSide() * 3 + (applicationPanel.getPlayer().getInventory().getSelectedRow() + 1) * (applicationPanel.getSquareSide() + 16);
        drawSelectionArrow(g,y, width, applicationPanel.getPlayer().getInventory().getSelectedCol());
    }

    public void drawShop(Graphics2D g) {
        int width = applicationPanel.getSquareSide() * 5 + 12;
        int height = applicationPanel.getSquareSide() * 3;
        int x = applicationPanel.getWidth() / 2 - width / 2;
        int y = applicationPanel.getHeight() / 2 - height / 2;

        drawBackgroundRect(g, x - 24, y, width + 48, height);
        g.setFont(new Font("font", Font.BOLD, 36));
        drawInformationWindow(g, "SHOP", y);
        String text;
        g.setFont(new Font("font", Font.BOLD, 10));
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                x = applicationPanel.getWidth() / 2 - width / 2 + j * applicationPanel.getSquareSide() + 12;
                y = applicationPanel.getHeight() / 2 - height / 2 + (i * applicationPanel.getSquareSide()) + 16;
                g.drawImage(applicationPanel.getShop().getItem(j, i).getImage(), x, y, applicationPanel.getSquareSide(), applicationPanel.getSquareSide(), null);
                text = String.valueOf(applicationPanel.getShop().getItem(j, i).getPrice());
                g.drawString(text, x + applicationPanel.getSquareSide() - 10, y + applicationPanel.getSquareSide() + textHeight(g, text) - 5);
            }
        }
        y = applicationPanel.getHeight() / 2 - height / 2 + (applicationPanel.getShop().getSelectedRow() + 1) * (applicationPanel.getSquareSide() + 16);
        drawSelectionArrow(g,y, width, applicationPanel.getShop().getSelectedCol());
    }

    public void drawTimer(Graphics2D g) {
        g.setFont(new Font("font", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        String text = "0:" + applicationPanel.getPlayer().getTime();
        g.drawString(text, applicationPanel.getWidth() - textLength(g, text) - 10, applicationPanel.getSquareSide());
    }

    public void drawWaveMessage(Graphics2D g) {
        g.setFont(new Font("font", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        String text = "NEXT WAVE IN: " + applicationPanel.getWaveTimer();
        g.drawString(text, applicationPanel.getWidth() / 2 - textLength(g, text) / 2, applicationPanel.getSquareSide() * 3);
    }

    public void drawScore(Graphics2D g) throws IOException {
        g.setColor(Color.WHITE);
        g.setFont(new Font("font", Font.BOLD, 36));
        String text = String.valueOf(applicationPanel.getGame().getScore());
        Image image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/star.png")));
        g.drawImage(image, 5, 5, applicationPanel.getSquareSide(), applicationPanel.getSquareSide(), null);
        g.drawString(text, applicationPanel.getSquareSide() + 15, textHeight(g, text));
    }

    public void drawBackgroundRect(Graphics2D g, int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 230);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 50, 50);

        color = new Color(255, 255, 255);
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x, y, width, height, 50, 50);
    }

    public void drawFireBlast(Graphics2D g) throws IOException {
        BufferedImage image = null;
        int x = 0;
        int y = 0;
        switch (applicationPanel.getPlayer().getDirection()) {
            case 0 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_1.png")));
                x = applicationPanel.getPlayer().getCenterX();
                y = applicationPanel.getPlayer().getCenterY() - applicationPanel.getSquareSide() / 2;
            }
            case 1 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_0.png")));
                x = applicationPanel.getPlayer().getCenterX();
                y = applicationPanel.getPlayer().getCenterY() + applicationPanel.getSquareSide() / 2;
            }
            case 2 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_2.png")));
                x = applicationPanel.getPlayer().getCenterX() - applicationPanel.getSquareSide() / 2;
                y = applicationPanel.getPlayer().getCenterY();
            }
            case 3 -> {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blasts/gunBlast_3.png")));
                x = applicationPanel.getPlayer().getCenterX() + applicationPanel.getSquareSide() / 2;
                y = applicationPanel.getPlayer().getCenterY();
            }
        }
        g.drawImage(image, x, y, applicationPanel.getSquareSide(), applicationPanel.getSquareSide(), null);
    }
    public void drawSelectionArrow(Graphics2D g, int y, int width, int col){
        g.setFont(new Font("font", Font.BOLD, 16));
        String text = "^";
        int x = applicationPanel.getWidth() / 2 - width / 2 + col * applicationPanel.getSquareSide() + 12 + applicationPanel.getSquareSide() / 2 - textLength(g, text);
        g.drawString(text, x, y);
    }
    public void drawInformationWindow(Graphics2D g, String text, int rectY){
        g.setFont(new Font("font", Font.BOLD, 36));
        drawBackgroundRect(g, applicationPanel.getWidth()/2 - textLength(g, text)/2 - 12, rectY - applicationPanel.getSquareSide() - textHeight(g, text), textLength(g, text) + 24, textHeight(g, text) + 12);
        g.drawString(text, applicationPanel.getWidth()/2 - textLength(g, text)/2, rectY - applicationPanel.getSquareSide() - 6);
    }

    public int textHeight(Graphics2D g, String text) {
        return (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
    }
    public int textLength(Graphics2D g, String text) {
        return (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
    }
    public int centerX(Graphics2D g, String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return applicationPanel.getWidth() / 2 - length / 2;
    }
    public int centerY(Graphics2D g, String text) {
        int height = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        return applicationPanel.getHeight() / 2 + height / 2;
    }
    public items.Item getSelectedItem() {
        return invByPos[applicationPanel.getPlayer().getInventory().getSelectedCol()][applicationPanel.getPlayer().getInventory().getSelectedRow()];
    }
}