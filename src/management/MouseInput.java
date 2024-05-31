package management;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The type Mouse input.
 */
public class MouseInput implements MouseListener {

    private boolean mouseClicked = false;

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        updateMouseClicked(e.getButton() == MouseEvent.BUTTON1);
    }

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

    /**
     * Is mouse clicked boolean.
     *
     * @return the boolean
     */
    public synchronized boolean isMouseClicked() {
        return mouseClicked;
    }
}
