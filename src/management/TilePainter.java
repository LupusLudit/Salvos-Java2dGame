package management;

import logic.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.awt.*;

/**
 * The type Tile painter.
 */
public class TilePainter {

    /**
     * The Map.
     */
    HashMap<String, Integer> map = new HashMap<>();
    /**
     * The Tiles.
     */
    HashMap<Integer, Tile> tiles = new HashMap<>();

    /**
     * The Panel.
     */
    ApplicationPanel panel;

    private int mapHeight;
    private int mapWidth;

    /**
     * Instantiates a new Tile painter.
     *
     * @param applicationPanel the application panel
     */
    public TilePainter(ApplicationPanel applicationPanel) {
        this.panel = applicationPanel;
        setMap();
        initializeTileImages();
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
        } catch (IOException ignored) {
        }
    }

    /**
     * Draw.
     *
     * @param g the g
     */
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

    /**
     * Is in range boolean.
     *
     * @param i the
     * @param j the j
     * @return the boolean
     */
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

    private void setTileImage(int index, boolean solid) {
        BufferedImage temp;
        try {
            temp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/background/tile_" + index + ".png"))));

            tiles.put(index, new Tile(temp, solid, index));
        } catch (IOException ignored) {
        }
    }

    private void initializeTileImages() {
        for (int i = 0; i < 24; i++) {
            setTileImage(i, i < 18);
        }
    }

    /**
     * Get tile tile.
     *
     * @param col the col
     * @param row the row
     * @return the tile
     */
    public Tile getTile(int col, int row) {
        int cell = map.get(col + "," + row);
        return tiles.get(cell);
    }

    /**
     * Gets map height.
     *
     * @return the map height
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Gets map width.
     *
     * @return the map width
     */
    public int getMapWidth() {
        return mapWidth;
    }
}
