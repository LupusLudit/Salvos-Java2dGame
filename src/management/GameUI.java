package management;

import items.Item;
import logic.ApplicationPanel;

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
            assert inputStream != null;
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            large = baseFont.deriveFont(80f);
            medium = baseFont.deriveFont(36f);
            small = new Font("small", Font.BOLD, 16);
            verySmall = baseFont.deriveFont(10f);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws the appropriate screen based on the current status of the panel.
     * The method switches between different statuses to render the corresponding screen elements.
     *
     * @param g the Graphics2D context on which to draw the elements
     * @throws IOException if the program couldn't find the image on the specific address
     */
    public void draw(Graphics2D g) throws IOException {
            switch (panel.getStatus()) {
                case SETUP -> drawStartingScreen(g);
                case CUSTOMIZATION -> drawCustomizationScreen(g);
                case PLAYING -> drawBackground(g);
                case GAMEOVER -> drawDeathScreen(g);
                case SHOP -> {
                    drawBackground(g);
                    drawShopWindow(g);
                }
                case INVENTORY -> {
                    drawBackground(g);
                    drawInventoryWindow(g);
                }
            }
    }
    /**
     * Draws the background and various game elements on the screen.
     *
     * @param g the Graphics2D context on which to draw the elements
     * @throws IOException if the program couldn't find the image on the specific address
     */

    private void drawBackground(Graphics2D g) throws IOException {
        panel.getTilePainter().draw(g);
        panel.getGame().drawEntities(g);
        panel.getPlayer().draw(g);
        panel.getCollectableManager().drawCollectables(g);

        drawAmmoIndicators(g);
        drawWeaponIndicators(g);
        drawScore(g);
        if (panel.getPlayer().getClock().isRunning()) {
            drawEnergyDrinkTimer(g);
        }
        if (panel.getEntities().isEmpty()){
            drawWaveMessage(g);
        }
    }

    /**
     * Draws the death screen after the player has died.
     * Also features selection labels (so the player can quit or restart the game).
     *
     * @param g the Graphics2D context on which to draw the elements
     */
    private void drawDeathScreen(Graphics2D g) {
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

    /**
     * Draws the starting screen background image and options to start or quit the game.
     *
     * @param g the Graphics2D context on which to draw the elements
     * @throws IOException if the program couldn't find the image on the specific address
     */
    private void drawStartingScreen(Graphics2D g) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/backgroundScreens/startingScreen.png")));
        g.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);

        g.setColor(Color.white);
        g.setFont(medium);
        drawSelectionLabel(g, "START", centerX(g, "START"), centerY(g, "START") - panel.getSquareSide(), 0, true);
        drawSelectionLabel(g, "QUIT", centerX(g, "QUIT"), centerY(g, "QUIT") + panel.getSquareSide(), 1, true);
    }
    /**
     * Draws the customization screen where players can adjust various bonuses.
     * The screen includes a background image, player's character image and selection labels.
     *
     * @param g the Graphics2D context on which to draw the elements
     * @throws IOException if the program couldn't find the image on the specific address
     */
    private void drawCustomizationScreen(Graphics2D g) throws IOException {
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
    /**
     * Draws a selectable label at the specified location.
     * If the label is currently selected, arrows are drawn around it to indicate the selection.
     * Context: Java JButton or JLabel were not used because they will not be universal for this specific project.
     *
     * @param g the Graphics2D context on which to draw the label
     * @param text the text of the label to be drawn
     * @param x the x coordinate
     * @param y the y coordinate
     * @param chosen the index of the currently chosen option
     * @param multiple a boolean indicating if there are should be arrows on both sides of the label or not
     */
    private void drawSelectionLabel(Graphics2D g, String text, int x, int y, int chosen, boolean multiple) {
        g.drawString(text, x, y);
        if (panel.getChosenOption() == chosen) drawArrows(g, text, x, y, multiple);
    }
    /**
     * Draws the ammunition indicators on the screen.
     *
     * @param g the Graphics2D context on which to draw the elements
     * @throws IOException if the program couldn't find the image on the specific address
     */
    private void drawAmmoIndicators(Graphics2D g) throws IOException {
        g.setFont(medium);
        g.setColor(Color.white);
        int x = panel.getSquareSide() / 2;
        int y = panel.getHeight() - panel.getSquareSide() - 30;
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/ammo.png")));

        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
        String text = panel.getGame().getMagazine() + "/" + panel.getGame().getAmmo();
        g.drawString(text, x + panel.getSquareSide() * 2, panel.getHeight() - 36);
    }
    /**
     * Draws currently selected weapon on bottom left corner of the screen.
     *
     * @param g the Graphics2D context on which to draw the elements.
     * @throws IOException if the program couldn't find the image on the specific address
     */
    private void drawWeaponIndicators(Graphics2D g) throws IOException {
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

    /**
     * Draws indicators representing the value of specific bonus on the screen.
     * Context: Indicators are either filled (▮) or empty (▯).
     * Filled blocks represent added bonus while empty blocks represent not yet added bonus
     *
     * @param g the Graphics2D context on which to draw the indicators
     * @param x the x coordinate
     * @param y the y coordinate
     * @param counter the number of filled indicators to be drawn.
     */
    private void drawIndicators(Graphics2D g, int x, int y, int counter) {
        g.setColor(Color.white);
        g.setFont(new Font("font", Font.BOLD, 36));
        for (int i = 0; i < 10; i++) {
            x += panel.getSquareSide();
            String symbol = (i < counter) ? "▮" : "▯";
            g.drawString(symbol, x, y);
        }
    }
    /**
     * Draws arrow indicators around a text label to indicate selection.
     *
     * @param g the Graphics2D context on which to draw the arrows
     * @param text the text label around which the arrows are drawn.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param multiple if true -> arrows will be drawn on both sides, if false -> arrows will be drawn only on the left side of the text
     */
    private void drawArrows(Graphics2D g, String text, int x, int y, boolean multiple) {
        g.drawString(">", x - panel.getSquareSide(), y);
        if (multiple){
            g.drawString("<", x + textLength(g, text) + panel.getSquareSide() - textLength(g, "<"), y);
        }
    }
    /**
     * Displays the player's inventory items on the screen.
     * Explanation:
     *
     * @param g the Graphics2D context on which to draw the inventory window
     */
    private void drawInventoryWindow(Graphics2D g) {
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

    /**
     * Draws the shop window on the screen, showing items available for purchase.
     * Also displays the price of each item.
     *
     * @param g the Graphics2D context on which to draw the shop window
     */
    private void drawShopWindow(Graphics2D g) {
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
    /**
     * Draws a selection arrow indicating the current selected item in inventory/shop.
     *
     * @param g the Graphics2D context on which to draw the selection arrow
     * @param y the y coordinate (no need for x coordinate since we can calculate it in the method)
     * @param width the width of the window.
     * @param col the column index of the selected item.
     */
    private void drawSelectionArrow(Graphics2D g, int y, int width, int col) {
        g.setFont(small);
        String text = "^";
        int x = panel.getWidth() / 2 - width / 2 + col * panel.getSquareSide() + 12 + panel.getSquareSide() / 2 - textLength(g, text);
        g.drawString(text, x, y);
    }

    /**
     * Draws the energy drink timer on the screen.
     * It informs the player for how much longer will the energy drink effect last.
     *
     * @param g the Graphics2D context on which to draw the timer
     */
    private void drawEnergyDrinkTimer(Graphics2D g) {
        g.setFont(large);
        g.setColor(Color.WHITE);
        String text = "0:" + panel.getPlayer().getTime();
        g.drawString(text, panel.getWidth() - textLength(g, text) - 10, panel.getSquareSide());
    }
    /**
     * Draws the wave message timer on the screen.
     * It informs the player when the next wave of hostile entities will arrive.
     *
     * @param g the Graphics2D context on which to draw the timer
     */
    private void drawWaveMessage(Graphics2D g) {
        g.setFont(large);
        g.setColor(Color.WHITE);
        String text = "NEXT WAVE IN: " + panel.getWaveTimer();
        g.drawString(text, panel.getWidth() / 2 - textLength(g, text) / 2, panel.getSquareSide() * 3);
    }

    /**
     * Draws star icon and the player's score on the screen.
     *
     * @param g the Graphics2D context on which to draw the score
     * @throws IOException if the program couldn't find the image on the specific address
     */
    private void drawScore(Graphics2D g) throws IOException {
        g.setColor(Color.WHITE);
        g.setFont(medium);
        String text = String.valueOf(panel.getGame().getScore());
        Image image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/star.png")));
        g.drawImage(image, 5, 5, panel.getSquareSide(), panel.getSquareSide(), null);
        g.drawString(text, panel.getSquareSide() + 15, textHeight(g, text) + 6);
    }
    /**
     * Draws a rounded rectangle as the background of a window for inventory/shop.
     *
     * @param g the Graphics2D context on which to draw the background rectangle
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    private void drawBackgroundRect(Graphics2D g, int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 25, 25);

        color = new Color(255, 215, 0);
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x, y, width, height, 25, 25);
    }
    /**
     * Draws an information window with a specified text at the top of a rectangular area.
     *
     * @param g the Graphics2D context on which to draw the information window
     * @param text the text to be displayed
     * @param rectY the y coordinate (x coordinate is always the center of the screen)
     */
    private void drawInformationWindow(Graphics2D g, String text, int rectY) {
        g.setFont(medium);
        int x = panel.getWidth() / 2 - textLength(g, text) / 2 - 16;
        int y = rectY - panel.getSquareSide() - textHeight(g, text);

        drawBackgroundRect(g, x, y, textLength(g, text) + 32, textHeight(g, text) + 12);
        g.drawString(text, x+16, rectY - panel.getSquareSide() - 6);
    }
    /**
     * Calculates the height of the specified text.
     *
     * @param g the Graphics2D context used for rendering the text
     * @param text the text for which the height is to be calculated
     * @return the height of the rendered text
     */
    public int textHeight(Graphics2D g, String text) {
        return (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
    }
    /**
     * Calculates the length of the specified text.
     *
     * @param g the Graphics2D context used for rendering the text
     * @param text the text for which the length is to be calculated
     * @return the length of the rendered text
     */
    public int textLength(Graphics2D g, String text) {
        return (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
    }
    /**
     * Calculates the x coordinate for centering the specified text.
     *
     * @param g the Graphics2D context used for rendering the text
     * @param text the text to be centered
     * @return integer value for centered x
     */
    public int centerX(Graphics2D g, String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return panel.getWidth() / 2 - length / 2;
    }

    /**
     * Calculates the y coordinate for centering the specified text.
     *
     * @param g the Graphics2D context used for rendering the text
     * @param text the text to be centered
     * @return integer value for centered y
     */
    public int centerY(Graphics2D g, String text) {
        int height = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        return panel.getHeight() / 2 + height / 2;
    }

    public Item getSelectedItem() {
        return invByPos[panel.getPlayer().getInventory().getSelectedCol()][panel.getPlayer().getInventory().getSelectedRow()];
    }
    public Font getMedium() {
        return medium;
    }
}