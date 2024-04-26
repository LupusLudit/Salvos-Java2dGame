package management;

import world.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameUI {

    world.Panel panel;
    Font font = new Font("font", Font.BOLD, 80); //temporarily

    ItemManager itemManager;


    public GameUI(Panel panel) {
        this.panel = panel;
        itemManager = new ItemManager(panel);
    }

    public void draw(Graphics2D g) {
        g.setFont(font);
        g.setColor(Color.white);

        switch (panel.getStatus()) {
            case SETUP -> drawStartingScreen(g);
            case CUSTOMIZATION -> drawCustomizationScreen(g);
            case PLAYING-> {
                panel.getTilePainter().draw(g);
                panel.getGame().drawEntities(g);
                panel.getPlayer().draw(g);
                try {
                    drawAmmoIndicators(g);
                    drawWeaponIndicators(g);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            case GAMEOVER -> drawDeathScreen(g);
            case INVENTORY -> {
                panel.getTilePainter().draw(g);
                panel.getGame().drawEntities(g);
                panel.getPlayer().draw(g);
                try {
                    drawAmmoIndicators(g);
                    drawWeaponIndicators(g);
                }catch (IOException e){
                    e.printStackTrace();
                }
                drawInventoryWindow(g);
            }
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
        int x = panel.getSquareSide()/2;
        int y = panel.getHeight() - panel.getSquareSide() - 10;
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/ammo.png")));

        g.drawImage(image, x, y, panel.getSquareSide(), panel.getSquareSide(), null);
        String text = panel.getGame().getMagazine() +  "/" + panel.getGame().getAmmo();
        g.drawString(text, x + panel.getSquareSide(), panel.getHeight()-16);
    }

    public void drawWeaponIndicators(Graphics2D g)throws IOException{
        int x = panel.getSquareSide()/2;
        int y = panel.getHeight() - 4* panel.getSquareSide();
        BufferedImage image = null;
        switch (panel.getGame().getCurrentWeapon()){
            case PISTOL -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/pistol.png")));
            case SEMIAUTO -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/semi-auto.png")));
            case ASSAULTRIFLE -> image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/assault-rifle.png")));
        }
        g.drawImage(image, x, y, panel.getSquareSide()*3, panel.getSquareSide()*3,null);

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

    private int selectedRow = 0;
    private int selectedCol = 0;

    public void drawInventoryWindow(Graphics2D g) {
        int width = panel.getSquareSide()*3 +12;
        int height = panel.getSquareSide() * 3;
        int x = panel.getWidth()/2 - width/2;
        int y = panel.getSquareSide();

        Color color = new Color(0, 0, 0, 230);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 50, 50);

        color = new Color(255, 255, 255);
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x, y, width, height, 50, 50);

        int counter = 0;

       for(int i = 0; i < panel.getPlayer().getInventory().size(); i++){
            x = panel.getWidth()/2 - width/2 + counter*panel.getSquareSide() + 12;
            if(i == 3){
               x = panel.getWidth()/2 - width/2;
               y += (panel.getSquareSide()) + 16;
               counter = 0;
           }
            try {
                g.drawImage(itemManager.getImage(panel.getPlayer().getInventory().get(i)), x, y, panel.getSquareSide(), panel.getSquareSide(), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            counter++;

        }
        font = new Font("font", Font.BOLD, 16);
        g.setFont(font);

        String text = "^";
        x = panel.getWidth()/2 - width/2 + (selectedCol)*panel.getSquareSide() + 12 + panel.getSquareSide()/2 - textLength(g, text);
        y = panel.getSquareSide() + (selectedRow+1)*(panel.getSquareSide() + 16);
        g.drawString(text,x,y);
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

    public void addRow(){
        if(selectedRow < 1){
            selectedRow++;
        }
        System.out.println("row" +selectedRow);
    }
    public void subtractRow(){
        if(selectedRow > 0){
            selectedRow--;
        }
        System.out.println("row" + selectedRow);
    }

    public void addCol(){
        if(selectedCol < 2){
            selectedCol++;
        }
        System.out.println("col:" + selectedCol);
    }
    public void subtractCol(){
        if(selectedCol > 0){
            selectedCol--;
        }
        System.out.println("col:" + selectedCol);
    }
}