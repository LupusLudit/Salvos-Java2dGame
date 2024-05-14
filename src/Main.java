import world.ApplicationPanel;
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
        window.setUndecorated(true);

        ApplicationPanel applicationPanel = new ApplicationPanel();
        window.add(applicationPanel);
        applicationPanel.requestFocus();
        

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        applicationPanel.start();
    }
}
