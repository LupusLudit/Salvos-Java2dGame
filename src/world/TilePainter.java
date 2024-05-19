package world;

import entities.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.awt.*;

public class TilePainter {

    HashMap<String, Integer> map = new HashMap<>();
    HashMap<Integer, Tile> tiles = new HashMap<>();

    ApplicationPanel panel;

    private int mapHeight;
    private int mapWidth;

    public TilePainter(ApplicationPanel applicationPanel) {
        this.panel = applicationPanel;
        initializeTileImages();
        setMap();
    }

    private void setMap() {
        try (BufferedReader br = new BufferedReader(new FileReader("gameMap.csv"))) {
            String line;
            String[] arr;
            int i = 0;
            int j = 0;
            while ((line = br.readLine()) != null) {
                j = 0;
                arr = line.split(",");
                while (j < arr.length) {
                    map.put(j + "," + i, Integer.parseInt(arr[j]));
                    j++;
                }
                i++;
            }
            mapWidth = j;
            mapHeight = i;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int cell = map.get(j + "," + i);

                int x = (j * panel.getSquareSide()) - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
                int y = (i * panel.getSquareSide()) - panel.getPlayer().getY() + panel.getPlayer().getCenterY();

                if (isInRange(i, j)) {
                    g.drawImage(tiles.get(cell).getImage(), x, y, null);
                }
            }
        }
    }

    public boolean isInRange(int i, int j) {
        boolean drawX = false;
        boolean drawY = false;

        if (Math.abs(panel.getPlayer().getX() - j * panel.getSquareSide()) < panel.getPlayer().getCenterX() + panel.getSquareSide()) {
            drawX = true;
        }
        if (Math.abs(panel.getPlayer().getY() - i * panel.getSquareSide()) < panel.getPlayer().getCenterY() + panel.getSquareSide()) {
            drawY = true;
        }

        return drawX && drawY;

    }

    private static BufferedImage convertToBufferedImage(Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }

    private void setTileImage(int fieldIndex, String imageIndex, boolean solid) {
        BufferedImage temp;
        try {
            temp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/background/tiles" + imageIndex + ".png"))));
            temp = convertToBufferedImage(temp.getScaledInstance(48, 48, temp.getType()));
            tiles.put(fieldIndex, new Tile(temp, solid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTileImages() {
        setTileImage(0, "09", true);
        setTileImage(1, "04", true);
        setTileImage(2, "10", true);
        setTileImage(3, "11", true);
        setTileImage(4, "12", true);
        setTileImage(5, "26", false);

        setTileImage(6, "22", false);
        setTileImage(7, "25", false);
        setTileImage(8, "31", false);
        setTileImage(9, "32", false);
        setTileImage(10, "05", true);
        setTileImage(11, "17", false);

        setTileImage(12, "06", true);
        setTileImage(13, "14", true);
        setTileImage(14, "13", true);
        setTileImage(15, "24", false);
        setTileImage(16, "16", false);
        setTileImage(17, "23", false);

        setTileImage(18, "30", false);
        setTileImage(19, "29", false);
        setTileImage(20, "07", true);
        setTileImage(21, "03", true);
        setTileImage(22, "08", true);
        setTileImage(23, "01", true);

        setTileImage(24, "19", false);
        setTileImage(25, "28", false);
        setTileImage(26, "21", false);
        setTileImage(27, "27", false);
        setTileImage(28, "46", false);
        setTileImage(29, "45", false);

        setTileImage(30, "48", false);
        setTileImage(31, "54", false);
        setTileImage(32, "47", false);
        setTileImage(33, "60", false);
        setTileImage(34, "34", true);
        setTileImage(35, "18", false);

        setTileImage(36, "20", false);
        setTileImage(37, "15", false);
        setTileImage(38, "44", false);
        setTileImage(39, "43", false);
        setTileImage(40, "49", false);
        setTileImage(41, "51", false);

        setTileImage(42, "50", false);
        setTileImage(43, "53", false);
        setTileImage(44, "33", true);
        setTileImage(45, "39", true);
        setTileImage(46, "40", true);
        setTileImage(47, "41", true);

        setTileImage(48, "55", false);
        setTileImage(49, "02", true);
        setTileImage(50, "35", true);
        setTileImage(51, "36", true);
        setTileImage(52, "37", true);
        setTileImage(53, "38", true);

        setTileImage(54, "42", true);
        setTileImage(55, "57", false);
        setTileImage(56, "56", false);
        setTileImage(57, "58", false);
        setTileImage(58, "59", false);
        setTileImage(59, "52", false);
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public HashMap<Integer, Tile> getTiles() {
        return tiles;
    }

    public boolean isTileSolid(int col, int row){
        int cell = map.get(col + "," + row);
        return tiles.get(cell).isSolid();
    }

    public boolean entityOnTile(int col, int row){
        Rectangle rectangle = new Rectangle(col*48, row*48, 48, 48);
        for (Entity entity: panel.getEntities()){
            if (rectangle.intersects(entity.getActualArea())){
                return true;
            }
        }
        return false;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}
