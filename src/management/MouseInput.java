package management;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    private boolean mouseClicked = false;

    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Override of default mousePressed method.
     * When the left mouse button is pressed it sets mouseClicked boolean true.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        updateMouseClicked(e.getButton() == MouseEvent.BUTTON1);
    }
    /**
     * Override of default mousePressed method.
     * When the left mouse button is released it sets mouseClicked boolean false.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        updateMouseClicked(false);
    }
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private synchronized void updateMouseClicked(boolean clicked) {
        mouseClicked = clicked;
    }

    public synchronized boolean isMouseClicked() {
        return mouseClicked;
    }
}
