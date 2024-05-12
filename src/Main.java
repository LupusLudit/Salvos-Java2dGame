import world.Panel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Salvos");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(window);

        Panel panel = new Panel(window.getWidth(), window.getHeight());
        window.add(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        panel.requestFocus();

        panel.start();

        //cursor
        BufferedImage cursorImg = null;
        try {
            cursorImg = ImageIO.read(new File("images/cursor/circle_cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "Custom Cursor");
        panel.setCursor(customCursor);
    }
}
