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

        Panel panel = new Panel();
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
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