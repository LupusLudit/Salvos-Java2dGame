import logic.ApplicationPanel;
import javax.swing.*;

/**
 * The main class.
 * Runs the whole code.
 */
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
