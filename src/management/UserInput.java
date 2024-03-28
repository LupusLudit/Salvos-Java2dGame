package management;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener {

    private int direction = 0;
    private boolean pressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        switch (input){
            case KeyEvent.VK_W, KeyEvent.VK_UP->{
                direction = 0;
                pressed = true;
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN ->{
                direction = 1;
                pressed = true;
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT ->{
                direction = 2;
                pressed = true;
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT ->{
                direction = 3;
                pressed = true;
            }
            default -> pressed = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed = false;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isPressed() {
        return pressed;
    }
}
