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
public class TilePainter {
    HashMap<String, Integer> map = new HashMap<>();
    HashMap<Integer, Tile> tiles = new HashMap<>();
    ApplicationPanel panel;

    private int mapHeight;
    private int mapWidth;
    public TilePainter(ApplicationPanel applicationPanel) {
        this.panel = applicationPanel;
        setMap();
        initializeTileImages();
    }

    /**
     * Loads the map from pre-prepared file.
     * Sets the mapWidth and mapHeight integers.
     */
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
     * Draws the tiles.
     *
     * @param g the Graphics2D context on which to draw the tiles.
     */
    public void draw(Graphics2D g) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int cell = map.get(j + "," + i);

                int x = (j * panel.getSquareSide()) - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
                int y = (i * panel.getSquareSide()) - panel.getPlayer().getY() + panel.getPlayer().getCenterY();

                if (tileInRange(i, j)) {
                    g.drawImage(tiles.get(cell).getImage(), x, y, null);
                }
            }
        }
    }

    /**
     * Checks if the tile at selected coordinates is on the screen.
     * Ensures that computers processing power won't be wasted on tiles which cannot be even seen.
     *
     * @param i the selected row
     * @param j the selected column
     * @return true if the tile is indeed on the screen
     */
    public boolean tileInRange(int i, int j) {
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

    /**
     * Sets the tile image and puts it into tiles HashMap.
     *
     * @param index index of the tile
     * @param solid boolean (true if the tile is solid)
     */
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
     * Returns the tile on selected column and row.
     *
     * @param col the column
     * @param row the row
     * @return the tile to be returned.
     */
    public Tile getTile(int col, int row) {
        int cell = map.get(col + "," + row);
        return tiles.get(cell);
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getMapWidth() {
        return mapWidth;
    }
}
