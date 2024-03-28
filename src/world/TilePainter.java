package world;

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

    world.Panel panel;

    public TilePainter(world.Panel panel) {
        this.panel = panel;
        initializeTileImages();
    }
    public void setMap() {
        try (BufferedReader br = new BufferedReader(new FileReader("gameMap.csv"))) {
            String line;
            String[] arr;
            int i = 0;
            int j;
            while ((line = br.readLine()) != null) {
                j = 0;
                arr = line.split(",");
                while (j < arr.length) {
                    map.put(j + "," + i, Integer.parseInt(arr[j]));
                    j++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < 90; i++) {
            for (int j = 0; j < 90; j++) {
                int cell = map.get(j + "," + i);

                int x = (j * panel.getTileSide()) - panel.getPlayer().getX() + panel.getPlayer().getCenterX();
                int y = (i * panel.getTileSide()) - panel.getPlayer().getY() + panel.getPlayer().getCenterY();

                if (isInRange(i, j)) {
                    g.drawImage(tiles.get(cell).getImage(), x, y, null);
                }
            }
        }
    }

    public boolean isInRange(int i, int j){
        boolean drawX = false;
        boolean drawY = false;

        if ((j * panel.getTileSide()) + panel.getTileSide() > panel.getPlayer().getX() - panel.getPlayer().getCenterX() &&
                (j * panel.getTileSide()) - panel.getTileSide() < panel.getPlayer().getX() + panel.getPlayer().getCenterX()) {
            drawX = true;
        }
        if ((i * panel.getTileSide()) + panel.getTileSide() > panel.getPlayer().getY() - panel.getPlayer().getCenterY() &&
                (i * panel.getTileSide()) - panel.getTileSide() < panel.getPlayer().getY() + panel.getPlayer().getCenterY()) {
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

    private void setTileImage(int fieldIndex, String imageIndex, boolean collision) {
        BufferedImage temp;
        try {
            temp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/background/"+ imageIndex +".png"))));
            temp = convertToBufferedImage(temp.getScaledInstance(48, 48, temp.getType()));
            tiles.put(fieldIndex, new Tile(temp, collision));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTileImages(){
        setTileImage(0, "tiles09", true);
        setTileImage(1, "tiles04", true);
        setTileImage(2, "tiles10", true);
        setTileImage(3, "tiles11", true);
        setTileImage(4, "tiles12", true);
        setTileImage(5, "tiles26", false);

        setTileImage(6, "tiles22", false);
        setTileImage(7, "tiles25", false);
        setTileImage(8, "tiles31", false);
        setTileImage(9, "tiles32", false);
        setTileImage(10, "tiles05", true);
        setTileImage(11, "tiles17", false);

        setTileImage(12, "tiles06", true);
        setTileImage(13, "tiles14", true);
        setTileImage(14, "tiles13", true);
        setTileImage(15, "tiles24", false);
        setTileImage(16, "tiles16", false);
        setTileImage(17, "tiles23", false);

        setTileImage(18, "tiles30", false);
        setTileImage(19, "tiles29", false);
        setTileImage(20, "tiles07", true);
        setTileImage(21, "tiles03", true);
        setTileImage(22, "tiles08", true);
        setTileImage(23, "tiles01", true);

        setTileImage(24, "tiles19", false);
        setTileImage(25, "tiles28", false);
        setTileImage(26, "tiles21", false);
        setTileImage(27, "tiles27", false);
        setTileImage(28, "tiles46", false);
        setTileImage(29, "tiles45", false);

        setTileImage(30, "tiles48", false);
        setTileImage(31, "tiles54", false);
        setTileImage(32, "tiles47", false);
        setTileImage(33, "tiles60", false);
        setTileImage(34, "tiles34", true);
        setTileImage(35, "tiles18", false);

        setTileImage(36, "tiles20", false);
        setTileImage(37, "tiles15", false);
        setTileImage(38, "tiles44", false);
        setTileImage(39, "tiles43", false);
        setTileImage(40, "tiles49", false);
        setTileImage(41, "tiles51", false);

        setTileImage(42, "tiles50", false);
        setTileImage(43, "tiles53", false);
        setTileImage(44, "tiles33", true);
        setTileImage(45, "tiles39", true);
        setTileImage(46, "tiles40", true);
        setTileImage(47, "tiles41", true);

        setTileImage(48, "tiles55", false);
        setTileImage(49, "tiles02", true);
        setTileImage(50, "tiles35", true);
        setTileImage(51, "tiles36", true);
        setTileImage(52, "tiles37", true);
        setTileImage(53, "tiles38", true);

        setTileImage(54, "tiles42", true);
        setTileImage(55, "tiles57", false);
        setTileImage(56, "tiles56", false);
        setTileImage(57, "tiles58", false);
        setTileImage(58, "tiles59", false);
        setTileImage(59, "tiles52", false);
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public HashMap<Integer, Tile> getTiles() {
        return tiles;
    }
}
